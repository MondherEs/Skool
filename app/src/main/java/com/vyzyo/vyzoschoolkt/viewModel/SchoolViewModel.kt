package com.vyzyo.vyzoschoolkt.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyzyo.vyzoschoolkt.config.Config.Companion.ERROR_TAG
import com.vyzyo.vyzoschoolkt.model.*
import com.vyzyo.vyzoschoolkt.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    private val getShcoolUrl = MutableLiveData<getSchoolResponse>()
     var getLoginDetail = MutableLiveData<LoginResponse>()
     val getChildren = MutableLiveData<ChildrenResponse>()
    private val getStudentImage = MutableLiveData<StudentImageResponse>()
    private val getUser = MutableLiveData<UserResponse>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()


    fun getSchoolUrl(otp: String) = viewModelScope.launch {

        repo.getSchoolUrl(otp).let {
            if (it.isSuccessful)
                getShcoolUrl.value = it.body()
            else
                Log.d(ERROR_TAG + "GETSkool", it.errorBody().toString());
        }

    }

    fun getLogin(email: String, password: String): LiveData<LoginResponse> {

        viewModelScope.launch {

            repo.getLogin(email, password).let {
                    if (it.isSuccessful)
                        getLoginDetail.value = it.body()
                    else
                        Log.d(ERROR_TAG + "GETSkool", it.errorBody()!!.string());
                }


        }
        return getLoginDetail
    }

    fun getLogin2(email: String, password: String){
        job = CoroutineScope(Dispatchers.IO ).launch {
            val response = repo.getLogin(email,password)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getLoginDetail.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }
        private fun onError(message: String) {
            errorMessage.value = message
            loading.value = false
        }
    fun getChildren2(token: String, parentID: String){


        job = CoroutineScope(Dispatchers.IO ).launch {
            val response = repo.getChildren(token,parentID)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getChildren.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
    fun getChildren(token: String, parentID: String): LiveData<ChildrenResponse> {


        viewModelScope.launch {

            repo.getChildren(token, parentID).let {

                withContext(Dispatchers.IO)
                {

                    if (it.isSuccessful)
                        getChildren.value = it.body()
                    else
                        Log.d(ERROR_TAG + "GetChildren", it.errorBody()!!.string());
                }
            }

        }
        return getChildren
    }

    fun getStudentImage(token: String, studentID: String, type:String, gender: String): LiveData<StudentImageResponse> {

        viewModelScope.launch {

            repo.getStudentImage(token, studentID,type,gender).let {
                if (it!!.isSuccessful)
                    getStudentImage.value = it.body()
                else
                    Log.d(ERROR_TAG +"GetSKImage", it.errorBody()!!.string());
            }

        }
        return getStudentImage
    }

    fun getUser(token: String, user_id: String, user_type:String ): LiveData<UserResponse> {

        viewModelScope.launch {

            repo.getUser(token, user_id,user_type).let {
                if (it!!.isSuccessful)
                    getUser.value = it.body()
                else
                    Log.d(ERROR_TAG +"GetUser", it.errorBody()!!.string());
            }

        }
        return getUser
    }

    fun observeSchoolUrl(): LiveData<getSchoolResponse> {
        return getShcoolUrl
    }


}