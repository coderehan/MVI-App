package com.rehan.mviapp.ui.main.viewstate

import com.rehan.mviapp.data.model.Posts

sealed class MainViewState {

    object Idle : MainViewState()
    object Loading : MainViewState()
    class Error(val message: String) : MainViewState()
    class Success(val data: List<Posts>) : MainViewState()

}
