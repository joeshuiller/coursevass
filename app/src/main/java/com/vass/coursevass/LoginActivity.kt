package com.vass.coursevass

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.vass.coursevass.databinding.ActivityLoginBinding
import com.vass.coursevass.network.AlertBuild
import com.vass.coursevass.network.service.AuthServices
import com.vass.coursevass.network.service.db.LoginDto
import com.vass.coursevass.storage.Storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.concurrent.timerTask
import android.R





@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val _binding get() = binding
    @Inject
    lateinit var storage: Storage
    @Inject
    lateinit var authServices: AuthServices
    private  val alertFinal =  AlertBuild(this)
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
        val buttonFinal: Button = _binding.buttonFinal
        buttonFinal.setOnClickListener {
            sendMessage()
        }
    }
    private fun sendMessage() {
        val email: EditText = _binding.editTextTextEmailAddress
        val password: EditText = _binding.editTextTextPassword
        email.requestFocus()
        password.requestFocus()
        showSoftKeyboard(email)
        showSoftKeyboard(password)
        if (email.text.toString().isEmpty()){
            alertFinal.createAlert("The email is mandatory", "Error")
        } else if (password.text.toString().isEmpty()){
            alertFinal.createAlert("Password is required", "Error")
        } else{
            alertFinal.displayLoadingWithText("Loading...", false)
            GlobalScope.launch {
                val loginResponse = authServices.login(LoginDto(email.text.toString(),password.text.toString()))
                if (loginResponse.isSuccessful){
                    alertFinal.hideLoading()
                    storage.saveToken(loginResponse.body()!!.accessToken)
                    auth(email.text.toString())
                    Log.d("Listo", "Storage token= ${storage.getToken()}")
                }
                if (loginResponse.code() == 500) {
                    alertFinal.hideLoading()
                    Timer().schedule(timerTask {
                        Log.d("uno", "Storage token")
                        alert()
                    }, 2000)
                }
            }
        }

    }
    private  fun alert(){
        alertFinal.createAlert("Could not authenticate", "Error")
    }
    private fun auth (Email: String){
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, Email)
        }
        startActivity(intent)
    }
    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}