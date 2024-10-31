package com.blockmaker.fdland.presentation.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyPageConfirmViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _birth = MutableLiveData<String>()
    val birth: LiveData<String> get() = _birth

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> get() = _phoneNumber

    private val _school = MutableLiveData<String>()
    val school: LiveData<String> get() = _school

    private val _schoolName = MutableLiveData<String>()
    val schoolName: LiveData<String> get() = _schoolName

    private val _userGrade = MutableLiveData<String>()
    val userGrade: LiveData<String> get() = _userGrade

    // 사용자 데이터 설정 메서드
    fun setUserData(userName: String, birth: String, phoneNumber: String, school: String, schoolName: String, userGrade: String) {
        _userName.value = userName
        _birth.value = birth
        _phoneNumber.value = phoneNumber
        _school.value = school
        _schoolName.value = schoolName
        _userGrade.value = userGrade
    }
}