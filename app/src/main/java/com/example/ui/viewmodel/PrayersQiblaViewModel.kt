package com.example.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.HafizIslamicHubApp
import com.example.data.catalog.QuizCatalog
import com.example.data.model.AsrMadhhab
import com.example.data.model.CityLocation
import com.example.data.model.PrayerCalcMethod
import com.example.data.repository.IslamicHubRepository
import com.example.domain.PrayerTimeCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

data class CompassUiState(
    val azimuthDegrees: Float = 0f,
    val hasCompassSensor: Boolean = true,
    val sensorAccuracyLabel: String = "Calibrated",
    val isAlignedWithQibla: Boolean = false
)

class PrayersQiblaViewModel(
    application: Application,
    private val repository: IslamicHubRepository
) : AndroidViewModel(application), SensorEventListener {

    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private val accelSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magnetSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private var gravityValues: FloatArray? = null
    private var magneticValues: FloatArray? = null
    private var smoothedAzimuth = 0f
    private var activeQiblaBearing = 260.0

    private val _compassState = MutableStateFlow(
        CompassUiState(
            hasCompassSensor = rotationSensor != null || (accelSensor != null && magnetSensor != null)
        )
    )
    val compassState: StateFlow<CompassUiState> = _compassState

    private val _citySearchQuery = MutableStateFlow("")
    val citySearchQuery: StateFlow<String> = _citySearchQuery

    private val _locationStatus = MutableStateFlow<String?>(null)
    val locationStatus: StateFlow<String?> = _locationStatus

    private val _isDetectingLocation = MutableStateFlow(false)
    val isDetectingLocation: StateFlow<Boolean> = _isDetectingLocation

    val allCities: List<CityLocation> = QuizCatalog.worldCities

    fun updateCitySearch(query: String) {
        _citySearchQuery.value = query
    }

    fun selectCity(city: CityLocation) {
        viewModelScope.launch {
            repository.setCityLocation(city)
            _locationStatus.value = "Prayer location updated to ${city.name}, ${city.country}"
        }
    }

    fun setPrayerCalcMethod(method: PrayerCalcMethod) {
        viewModelScope.launch { repository.setPrayerMethod(method) }
    }

    fun setAsrMadhhab(madhhab: AsrMadhhab) {
        viewModelScope.launch { repository.setAsrMadhhab(madhhab) }
    }

    fun clearLocationStatus() {
        _locationStatus.value = null
    }

    fun updateActiveQiblaBearing(bearingDeg: Double) {
        activeQiblaBearing = bearingDeg
    }

    @SuppressLint("MissingPermission")
    fun detectDeviceLocation() {
        viewModelScope.launch {
            _isDetectingLocation.value = true
            _locationStatus.value = null
            try {
                val lm = getApplication<Application>().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val providers = lm.getProviders(true)
                var bestLocation: Location? = null
                for (provider in providers) {
                    val loc = runCatching { lm.getLastKnownLocation(provider) }.getOrNull()
                    if (loc != null && (bestLocation == null || loc.accuracy < bestLocation.accuracy)) {
                        bestLocation = loc
                    }
                }

                if (bestLocation == null) {
                    _locationStatus.value = "Could not acquire GPS fix. Please select your city manually below."
                } else {
                    val city = resolveCityFromCoordinates(bestLocation.latitude, bestLocation.longitude)
                    repository.setCityLocation(city)
                    _locationStatus.value = "Location set to ${city.name}, ${city.country}"
                }
            } catch (_: SecurityException) {
                _locationStatus.value = "Location permission was denied. You can choose your city manually."
            } catch (e: Exception) {
                _locationStatus.value = "Location detection unavailable (${e.localizedMessage ?: "error"})."
            } finally {
                _isDetectingLocation.value = false
            }
        }
    }

    private suspend fun resolveCityFromCoordinates(lat: Double, lng: Double): CityLocation = withContext(Dispatchers.IO) {
        val tzId = TimeZone.getDefault().id
        var cityName = String.format(Locale.US, "%.2f°, %.2f°", lat, lng)
        var countryName = "Detected Location"
        try {
            val geocoder = Geocoder(getApplication(), Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                val first = addresses?.firstOrNull()
                if (first != null) {
                    cityName = first.locality ?: first.subAdminArea ?: first.adminArea ?: cityName
                    countryName = first.countryName ?: countryName
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                val first = addresses?.firstOrNull()
                if (first != null) {
                    cityName = first.locality ?: first.subAdminArea ?: first.adminArea ?: cityName
                    countryName = first.countryName ?: countryName
                }
            }
        } catch (_: Exception) {
            // Fallback to coordinate label if Geocoder offline
        }
        CityLocation(
            name = cityName,
            country = countryName,
            latitude = lat,
            longitude = lng,
            timeZoneId = tzId
        )
    }

    fun startCompass() {
        if (rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            if (accelSensor != null) {
                sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_UI)
            }
            if (magnetSensor != null) {
                sensorManager.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_UI)
            }
        }
    }

    fun stopCompass() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)
                val azimuthDeg = ((Math.toDegrees(orientation[0].toDouble()) + 360.0) % 360.0).toFloat()
                updateAzimuth(azimuthDeg)
            }
            Sensor.TYPE_ACCELEROMETER -> {
                gravityValues = lowPass(event.values.clone(), gravityValues)
                computeFromAccelMagnet()
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                magneticValues = lowPass(event.values.clone(), magneticValues)
                computeFromAccelMagnet()
            }
        }
    }

    private fun computeFromAccelMagnet() {
        val g = gravityValues ?: return
        val m = magneticValues ?: return
        val r = FloatArray(9)
        val i = FloatArray(9)
        if (SensorManager.getRotationMatrix(r, i, g, m)) {
            val orientation = FloatArray(3)
            SensorManager.getOrientation(r, orientation)
            val azimuthDeg = ((Math.toDegrees(orientation[0].toDouble()) + 360.0) % 360.0).toFloat()
            updateAzimuth(azimuthDeg)
        }
    }

    private fun updateAzimuth(newAzimuth: Float) {
        // Shortest-angle low-pass smoothing
        var delta = newAzimuth - smoothedAzimuth
        if (delta > 180f) delta -= 360f
        if (delta < -180f) delta += 360f
        smoothedAzimuth = (smoothedAzimuth + 0.18f * delta + 360f) % 360f

        val diffToQibla = abs(((activeQiblaBearing - smoothedAzimuth + 540.0) % 360.0) - 180.0)
        _compassState.value = _compassState.value.copy(
            azimuthDegrees = smoothedAzimuth,
            isAlignedWithQibla = diffToQibla <= 4.0
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        val label = when (accuracy) {
            SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> "High Accuracy"
            SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> "Medium Accuracy"
            SensorManager.SENSOR_STATUS_ACCURACY_LOW -> "Low Accuracy — Wave phone in figure-8"
            else -> "Uncalibrated — Wave phone in figure-8"
        }
        _compassState.value = _compassState.value.copy(sensorAccuracyLabel = label)
    }

    private fun lowPass(input: FloatArray, output: FloatArray?): FloatArray {
        if (output == null) return input
        val alpha = 0.2f
        for (i in input.indices) {
            output[i] = output[i] + alpha * (input[i] - output[i])
        }
        return output
    }

    override fun onCleared() {
        stopCompass()
        super.onCleared()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HafizIslamicHubApp)
                PrayersQiblaViewModel(app, app.repository)
            }
        }
    }
}
