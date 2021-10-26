package com.vass.coursevass

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.AlarmClock
import android.view.WindowInsets
import android.view.WindowManager
import com.vass.coursevass.databinding.ActivitySplashScreenBinding
import com.vass.coursevass.storage.Storage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val _binding get() = binding
    @Inject
    lateinit var storage: Storage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setTheme(R.style.Theme_Prueba)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.progressBar.progressTintList = ColorStateList.valueOf(Color.WHITE)
        }
        val timer: CountDownTimer
        timer = object : CountDownTimer(6000, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                valid()
            }
        }
        timer.start()
    }
    fun valid(){
        if (storage.authUsers()){
            homeActivity()
        }else{
            loginActivity()
        }
        finish()
    }
    fun loginActivity(){
        val intent = Intent(this, LoginActivity::class.java).apply {
        }
        startActivity(intent)
    }
    fun homeActivity(){
        val intent = Intent(this, HomeActivity::class.java).apply {
        }
        startActivity(intent)
    }
}