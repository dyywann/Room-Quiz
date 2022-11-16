package unj.cs.app.data

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch

class StudentViewModel(private val dao: StudentDao, private val savedStateHandle: SavedStateHandle):ViewModel() {
    private val STATE_KEY:String = "studentList"
    var _studentList: MutableLiveData<List<Student>> = savedStateHandle.getLiveData<List<Student>>(STATE_KEY)
    val studentList: LiveData<List<Student>>  get() = _studentList


    fun setStudent(student: Student) {
        viewModelScope.launch {
            dao.update(student)
        }
    }

    fun loadStudent(){
        viewModelScope.launch {
            dao.getAll().collect(){
                _studentList.value = it
            }
        }
        savedStateHandle[STATE_KEY] = studentList.value
    }

    fun getStudent(position: Int): Student {
        return studentList.value!![position]
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            dao.insert(student)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val studentDao = StudentDatabase.getInstance(application).studentDao()
                val savedStateHandle = extras.createSavedStateHandle()

                return StudentViewModel(
                    studentDao,
                    savedStateHandle
                ) as T
            }
        }
    }
}