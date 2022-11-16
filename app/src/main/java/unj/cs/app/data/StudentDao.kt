package unj.cs.app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Query("Select * from Student")
    fun getAll(): Flow<List<Student>>

    @Query("Select * from Student where uid = :uid")
    fun getStudentByUid(uid: Int): Flow<List<Student>>

    @Query("Select * from Student where _id = :id")
    fun getStudentById(id: Int): Flow<List<Student>>

    @Query("Select * from Student where name = :name")
    fun getStudentByName(name: String): Flow<List<Student>>

    @Query("Select * from Student where uid = :uid or name = :name")
    fun getStudentOr(uid: Int, name: String) : Flow<List<Student>>

    @Insert
    suspend fun insert(vararg student: Student)
    @Update
    suspend fun update(student: Student)
    @Delete
    suspend fun delete(student: Student)
}