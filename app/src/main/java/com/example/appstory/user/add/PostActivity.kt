package com.example.appstory.user.add

import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.viewModels
import com.example.appstory.data.reference.ResultState
import com.example.appstory.databinding.ActivityPostBinding
import com.example.appstory.utils.getImageUri
import com.example.appstory.utils.reduceFileImage
import com.example.appstory.utils.uriToFile
import com.example.appstory.user.ViewModelFactory
import com.example.appstory.user.main.MainActivity
import com.example.appstory.user.landing.LandingPage

class PostActivity : AppCompatActivity() {
    private val postViewModel by viewModels<PostViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var imageUri: Uri? = null
    private lateinit var binding: ActivityPostBinding

    private val openGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            show()
        } else {
            Log.d("Photo", "No photo selected")
        }
    }

    private val openKamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){
        if(it){
            show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivGallery.setOnClickListener {
            intentGallery()
        }

        binding.ivKamera.setOnClickListener {
            openKamera()
        }

        postViewModel.getSession().observe(this) { result ->
            if (!result.isLogin) {
                startActivity(Intent(this, LandingPage::class.java))
                finish()
            } else {
                binding.btnUpload.setOnClickListener {
                    Log.i("Check", "onCreate: ${result.token}")
                    post(result.token)
                }
            }
        }
    }

    private fun show() {
        imageUri?.let {
            binding.ivPhoto.setImageURI(it)
        }
    }

    private fun intentGallery(){
        openGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun openKamera() {
        imageUri = getImageUri(this)
        openKamera.launch(imageUri)
    }

    private fun post(token: String) {
        imageUri?.let { uri ->
            val file = uriToFile(uri, this).reduceFileImage()
            val desc = binding.edtStory.text.toString()

            postViewModel.upload(token, file, desc).observe(this) { result ->
                when (result) {
                    is ResultState.Success -> {
                        result.data.message?.let {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        }
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    is ResultState.Error -> {
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
            }
        } ?: Toast.makeText(this, "Photo Still Empty", Toast.LENGTH_SHORT).show()
    }
}
