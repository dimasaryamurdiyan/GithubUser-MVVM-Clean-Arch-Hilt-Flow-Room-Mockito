package com.testcase.githubapp.ui.detailuser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailUserViewModel @ViewModelInject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _userRepos = MutableLiveData<Resource<GetUserReposResponse>>()
    val userRepos: LiveData<Resource<GetUserReposResponse>> get() = _userRepos

    private val _user = MutableLiveData<Resource<GetUserResponse.User>>()
    val user: LiveData<Resource<GetUserResponse.User>> get() = _user

    fun getUserRepos(username: String){
        viewModelScope.launch {
            userUseCase.getUserRepos(
                username,
                3
            ).collect {
                _userRepos.postValue(it)
            }
        }
    }

    fun getUser(username: String){
        viewModelScope.launch {
            userUseCase.getUser(username).collect {
                _user.postValue(it)
            }
        }
    }
}