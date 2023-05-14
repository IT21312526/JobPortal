package database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Admin(
    var email:String,
    var password:String ,
    val profilePic: ByteArray
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
