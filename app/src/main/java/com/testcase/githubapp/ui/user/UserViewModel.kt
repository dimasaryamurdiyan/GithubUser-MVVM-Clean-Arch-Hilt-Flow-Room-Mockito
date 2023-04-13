package com.testcase.githubapp.ui.user

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.domain.repository.UserRepository
import com.testcase.githubapp.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class  UserViewModel @ViewModelInject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _userList = MutableLiveData<Resource<GetUserResponse>>()
    val userList: LiveData<Resource<GetUserResponse>> get() = _userList

    fun getUsers() {
        viewModelScope.launch {
            userUseCase.getUsers(false).collect {
                _userList.postValue(it)
            }
        }
    }

    fun getSearchUsers(q: String) {
        viewModelScope.launch {
            userUseCase.getSearchUser(q, 3).collect {
                _userList.postValue(it)
            }
        }
    }
}