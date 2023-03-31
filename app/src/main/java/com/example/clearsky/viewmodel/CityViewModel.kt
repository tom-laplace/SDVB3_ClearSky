import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CityViewModel(private val app: MyApplication) : ViewModel() {

    fun insertCity(city: City) {
        viewModelScope.launch {
            app.database.cityDao().insert(city)
        }
    }

    fun getAllCities() {
        viewModelScope.launch {
            val cities = app.database.cityDao().getAll()
        }
    }

    fun deleteAllCities() {
        viewModelScope.launch {
            app.database.cityDao().deleteAll()
        }
    }
}