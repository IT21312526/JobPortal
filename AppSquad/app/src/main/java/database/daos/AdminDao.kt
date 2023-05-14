package database.daos



@Dao
interface AdminDao {
    //insert admin
    @Insert
    suspend fun insertAdmin(admin: Admin)
    
    //delete admin
    @Delete
    suspend fun delete(admin: Admin)
    
    //getting all admins
    @Query("SELECT * From Admin")
    fun getAllAdmin(): List<Admin>

    //admin login
    @Query("SELECT * From Admin WHERE email LIKE :email")
    fun getAdminLogin(email : String) : Admin

    //admin profile
    @Query("SELECT * From Admin WHERE id = :id")
    fun getAdminDetail(id : Int) : Admin
    
    //update admin
    @Query("UPDATE Admin SET email =:email ,password =:password , profilePic =:profilePic  WHERE id = :id")
    fun updateAdmin(id : Int , email : String , password :String , profilePic: ByteArray ) : Int



}
