package com.rehan.mviapp.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehan.mviapp.data.repository.PostRepository
import com.rehan.mviapp.ui.main.intent.MainIntent
import com.rehan.mviapp.ui.main.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    val mainIntent: Channel<MainIntent> = Channel(Channel.UNLIMITED)

    private val _mutableStateFlow = MutableStateFlow<MainViewState>(MainViewState.Idle)        // Private MutableStateFlow
    val stateFlow: StateFlow<MainViewState>                                             // Public level access StateFlow
        get() = _mutableStateFlow

    init {
        handleIntent()
    }

    fun handleIntent(){
        viewModelScope.launch(Dispatchers.IO) {
            mainIntent.consumeAsFlow().collect{
                when(it){
                    is MainIntent.GetPosts -> fetchPosts()
                }
            }
        }
    }

    fun fetchPosts(){
        viewModelScope.launch(Dispatchers.IO) {
            _mutableStateFlow.value = MainViewState.Loading        // We will show loading progress bar
            try{
                _mutableStateFlow.value = MainViewState.Success(data = postRepository.getPosts())      // Success data from API
            }catch (e: Exception){
                _mutableStateFlow.value = MainViewState.Error(message = e.message.toString())          // Error message when we don't get data from API
            }
        }
    }
}