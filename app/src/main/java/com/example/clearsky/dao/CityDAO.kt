import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDao {
    @Insert
    suspend fun insert(city: City)

    @Query("SELECT * FROM city")
    suspend fun getAll(): List<City>

    @Query("DELETE FROM city")
    suspend fun deleteAll()
}