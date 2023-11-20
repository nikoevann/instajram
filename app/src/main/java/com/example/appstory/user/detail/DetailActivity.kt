package com.example.appstory.user.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.appstory.data.response.DetailStoryResponse
import com.example.appstory.databinding.ActivityDetailBinding
import com.example.appstory.user.ViewModelFactory
import com.example.appstory.user.landing.LandingPage

class DetailActivity : AppCompatActivity() {
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        detailViewModel.getSession().observe(this){user ->
            if(!user.isLogin){
                startActivity(Intent(this, LandingPage::class.java))
                finish()
            }
            val detailStory = intent.getStringExtra(EXTRA_DATA)
            if(detailStory != null){
                detailViewModel.getDetail(user.token,detailStory).observe(this){result ->
                    Log.e("result", "setDetailStory: $result")
                    setDataDetail(result)
                    showLoading(false)
                }
            }
        }
        detailViewModel.Loading.observe(this){
            showLoading(it)
        }
    }


    private fun showLoading(isLoading: Boolean){
        binding.progress.visibility =if(isLoading) View.VISIBLE else View.GONE
    }
    private fun setDataDetail(detail: DetailStoryResponse){
        binding.tvTitle.text = detail.story?.name
        binding.tvDescription.text = detail.story?.description
        Glide.with(this)
            .load(detail.story?.photoUrl)
            .into(binding.ivStory)
    }

    companion object{
        const val EXTRA_DATA = "extra"
    }
}