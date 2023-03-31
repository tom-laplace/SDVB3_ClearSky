import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val latitude: Float,
    val longitude: Float
)