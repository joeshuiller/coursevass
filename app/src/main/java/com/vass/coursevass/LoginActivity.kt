package com.vass.coursevass

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vass.coursevass.databinding.ActivityLoginBinding
import com.vass.coursevass.network.AlertBuild
import com.vass.coursevass.network.service.AuthServices
import com.vass.coursevass.storage.Storage
import com.vass.coursevass.viewmodel.serviceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val _binding get() = binding
    @Inject
    lateinit var storage: Storage
    @Inject
    lateinit var authServices: AuthServices
    private  val alertFinal =  AlertBuild(this)
    private val auth: serviceViewModel  by viewModels()
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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Viewauth()
        _binding.buttonFinal.setOnClickListener {
            sendMessage()
        }
    }
    private fun sendMessage() {
        val email = _binding.editTextTextEmailAddress
        val password = _binding.editTextTextPassword
        when {
            email.text.toString().isEmpty() -> {
                alertFinal.createAlert("The email is mandatory", "Error")
            }
            password.text.toString().isEmpty() -> {
                alertFinal.createAlert("Password is required", "Error")
            }
            else -> {
                alertFinal.displayLoadingWithText("Loading...", false)
                auth.authLogin(email.text.toString(), password.text.toString())
            }
        }
    }
    private  fun alert(){
        alertFinal.createAlert("Could not authenticate", "Error")
    }
    private fun authent (){
        val intent = Intent(this, HomeActivity::class.java).apply {
        }
        startActivity(intent)
        finish()
    }
    private fun Viewauth() {
        auth.auth.observe(this, { value ->
            if (value){
                alertFinal.hideLoading()
                authent()
            }else{
                alert()
                alertFinal.hideLoading()
            }
        })
    }
}