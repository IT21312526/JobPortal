package database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.entities.Admin
import database.entities.Company

@Dao
interface AdminDao {
    @Insert
    suspend fun insertAdmin(admin: Admin)
    @Delete
    suspend fun delete(admin: Admin)

    @Query("SELECT * From Admin")
    fun getAllAdmin(): List<Admin>

    @Query("SELECT * From Admin WHERE email LIKE :email")
    fun getAdminLogin(email : String) : Admin

    @Query("SELECT * From Admin WHERE id = :id")
    fun getAdminDetail(id : Int) : Admin

    @Query("UPDATE Admin SET email =:email ,password =:password , profilePic =:profilePic  WHERE id = :id")
    fun updateAdmin(id : Int , email : String , password :String , profilePic: ByteArray ) : Int



}