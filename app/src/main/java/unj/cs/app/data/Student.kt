package unj.cs.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    var _id: Int = -1,
    val uid:String,
    val name:String
){
    constructor(uid: String, name: String): this(0, uid, name)
}