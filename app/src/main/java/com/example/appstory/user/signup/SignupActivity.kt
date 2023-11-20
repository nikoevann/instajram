package com.example.appstory.user.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import com.example.appstory.data.reference.ResultState
import com.example.appstory.databinding.ActivitySignupBinding
import com.example.appstory.user.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var progressBar: ProgressBar

    private val signUpViewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progress
        progressBar.visibility = View.INVISIBLE

        setupView()
        setupAction()
        playAnimation()
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

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()

            progressBar.visibility = View.VISIBLE

            signUpViewModel.registerUser(name, email, password).observe(this) { result ->
                progressBar.visibility = View.GONE
                if (result != null) {
                    when (result) {
                        is ResultState.Success -> {
                            result.data.message?.let {
                                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        }
                        is ResultState.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Handle other cases if needed
                        }
                    }
                }
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageview, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val nameTv =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(300)
        val nameEtLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val emailTv =
            ObjectAnimator.ofFloat(binding.emailTv, View.ALPHA, 1f).setDuration(300)
        val emailEtLayout =
            ObjectAnimator.ofFloat(binding.emailEtLayout, View.ALPHA, 1f).setDuration(300)
        val passwordTv =
            ObjectAnimator.ofFloat(binding.passwordTv, View.ALPHA, 1f).setDuration(300)
        val passwordEtLayout =
            ObjectAnimator.ofFloat(binding.passwordEtLayout, View.ALPHA, 1f).setDuration(300)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(
                nameTv,
                nameEtLayout,
                emailTv,
                emailEtLayout,
                passwordTv,
                passwordEtLayout,
                signup
            )
            startDelay = 100
        }.start()
    }
}
