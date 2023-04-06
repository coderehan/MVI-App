package com.rehan.mviapp.ui.main.intent

// Intent in MVI patterns basically returns actions. Here we are getting posts info from API which is basically intent action here
sealed class MainIntent{
    object GetPosts : MainIntent()
}
