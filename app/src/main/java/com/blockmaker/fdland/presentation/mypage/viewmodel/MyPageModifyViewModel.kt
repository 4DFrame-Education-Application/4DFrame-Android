package com.blockmaker.fdland.presentation.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.data.model.MyPageResponse

class MyPageModifyViewModel : ViewModel() {

    // 사용자 정보 LiveData
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val birthDate = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val schoolName = MutableLiveData<String>()

    // 저장 성공 여부를 나타내는 LiveData
    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> get() = _saveSuccess

    // 초기 사용자 데이터 설정
    fun setUserData(myPageResponse: MyPageResponse) {
        userName.value = myPageResponse.userName
        schoolName.value = myPageResponse.schoolName
    }

    // 수정 사항을 저장하는 함수
    fun saveModifications() {
        // 여기서 서버나 로컬 데이터베이스에 저장하는 로직을 추가합니다.
        // 성공적으로 저장되었다고 가정하고 _saveSuccess를 true로 설정합니다.
        _saveSuccess.value = true
    }
}