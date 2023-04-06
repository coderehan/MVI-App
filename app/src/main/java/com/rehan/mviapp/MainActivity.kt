package com.rehan.mviapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.rehan.mviapp.databinding.ActivityMainBinding
import com.rehan.mviapp.ui.main.adapters.PostsAdapter
import com.rehan.mviapp.ui.main.intent.MainIntent
import com.rehan.mviapp.ui.main.viewmodel.MainViewModel
import com.rehan.mviapp.ui.main.viewstate.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val postsAdapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initRecyclerView()
        observeViewModels()

        // Initially we have to set the channel data
        lifecycleScope.launch{
            viewModel.mainIntent.send(MainIntent.GetPosts)
        }
    }

    private fun observeViewModels() {
        lifecycleScope.launch {
            viewModel.stateFlow.collect() {
                when (it) {
                    is MainViewState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainViewState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        postsAdapter.setPostsItems(it.data)
                    }
                    is MainViewState.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvPosts.apply {
            adapter = postsAdapter
        }
    }
}