package com.example.appstory.user.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstory.databinding.ActivityMainBinding
import com.example.appstory.user.add.PostActivity
import com.example.appstory.user.ViewModelFactory
import com.example.appstory.user.adapter.StoryAdapter
import com.example.appstory.user.landing.LandingPage
import com.example.appstory.user.map.MapsActivity

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvItem
        adapter = StoryAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        showLoading(true)
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LandingPage::class.java))
                finish()
            } else {
                showLoading(false)
                val adapter = StoryAdapter()
                binding.rvItem.adapter = adapter
                mainViewModel.getStory(user.token).observe(this) {
                    if (it != null) {
                        adapter.submitData(lifecycle, it)
                    }
                    Log.e("token", "onCreate: ${user.token}",)
                }
            }
            binding.cvLogout.setOnClickListener {
                mainViewModel.logout()
                finish()
            }

            binding.cvPost.setOnClickListener {
                val intent = Intent(this, PostActivity::class.java)
                startActivity(intent)
            }

            binding.fabMaps.setOnClickListener {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }

            setupView()

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}