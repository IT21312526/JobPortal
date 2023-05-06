package database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.entities.Admin
import database.entities.Application


@Dao
interface ApplcationDao {
    @Insert
    suspend fun insert(applcation: Application)
    @Delete
    suspend fun delete(applcation: Application)
    @Query("SELECT * From Application")
    fun getAllApplications(): List<Application>
}