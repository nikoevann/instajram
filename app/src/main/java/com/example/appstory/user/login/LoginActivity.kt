package com.example.appstory.user.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import com.example.appstory.data.reference.ResultState
import com.example.appstory.data.reference.UserModel
import com.example.appstory.databinding.ActivityLoginBinding
import com.example.appstory.user.ViewModelFactory
import com.example.appstory.user.main.MainActivity
import com.example.appstory.user.signup.SignupActivity


class LoginActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progress
        progressBar.visibility = View.INVISIBLE

        setupAction()
        setupView()
        playAnimation()
    }

    private fun setupView() {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()


            progressBar.visibility = View.VISIBLE


            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                loginViewModel.loginUser(email, password).observe(this) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                        }
                        is ResultState.Success -> {

                            result.data.message.let {
                                Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                                loginViewModel.saveSession(
                                    UserModel(
                                        email,
                                        "${result.data.loginResult?.token}"
                                    )
                                )
                            }
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        is ResultState.Error -> {

                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                        }
                    }


                    progressBar.visibility = View.INVISIBLE
                }
            }, 100)
        }
    }



    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.loginimage, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val emailTv =
            ObjectAnimator.ofFloat(binding.emailTv, View.ALPHA, 1f).setDuration(300)
        val emailEtLayout =
            ObjectAnimator.ofFloat(binding.emailEtLayout, View.ALPHA, 1f).setDuration(300)
        val passwordTv =
            ObjectAnimator.ofFloat(binding.passwordTv, View.ALPHA, 1f).setDuration(300)
        val passwordEtLayout =
            ObjectAnimator.ofFloat(binding.passwordEtLayout, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(
                emailTv,
                emailEtLayout,
                passwordTv,
                passwordEtLayout,
                login
            )
            startDelay = 100
        }.start()
    }



}