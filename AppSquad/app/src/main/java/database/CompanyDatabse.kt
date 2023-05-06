package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import database.daos.*
import database.entities.*

@Database(entities = [Company::class , Admin::class , Job::class, User::class , Gig::class , Application::class] , version = 10 )



abstract class CompanyDatabase: RoomDatabase() {

    abstract fun getCompanyDao(): CompanyDao
    abstract fun getAdminDao():AdminDao
    abstract fun getJobDao():JobDao
    abstract fun getUserDao():UserDao
    abstract fun getGigDao():GigDao
    abstract fun getApplicationDao():ApplcationDao

    companion object {
        @Volatile
        private var INSTANCE: CompanyDatabase? = null

        fun getInstance(context: Context):CompanyDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CompanyDatabase::class.java,
                    "company_db"
                ).addMigrations(MIGRATION_9_10).build().also {
                    INSTANCE = it
                }
            }
        }


        val MIGRATION_9_10 = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the "Employee" table to the database
//                database.execSQL("CREATE TABLE IF NOT EXISTS `Company` (`name` TEXT NOT NULL , `password` TEXT NOT NULL , `approved` BOOLEAN NOT NULL ,`address` TEXT NOT NULL ,  `password` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT,  `email` TEXT NOT NULL)")
//                database.execSQL("DROP TABLE IF EXISTS `Company`")
//                database.execSQL("DROP TABLE if EXISTS `Company`" )
               database.execSQL("DROP TABLE if EXISTS `Application`")
                database.execSQL("CREATE TABLE IF NOT EXISTS `Application` (`notes` TEXT NOT NULL , `jobId` INT NOT NULL , `user` INT NOT NULL , `contact` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)")
            }
        }
    }
}