package com.vass.coursevass

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.vass.coursevass.databinding.ActivityHomeBinding
import com.vass.coursevass.network.RetrofitGenere
import com.vass.coursevass.network.service.AuthServices
import com.vass.coursevass.network.service.UserService
import com.vass.coursevass.network.service.db.LoginDto
import com.vass.coursevass.network.service.db.RegistrationDto
import com.vass.coursevass.storage.LocalStorage
import com.vass.coursevass.storage.Storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var storage: Storage
    @Inject
    lateinit var userService: UserService
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
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        autentication()
        // Get the Intent that started this activity and extract the string
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }
    fun autentication(){
        GlobalScope.launch {
            val userServiceGetUsersResponse = userService.getUsersList()
            if (userServiceGetUsersResponse.isSuccessful){
                Log.d("Listo", "Storage token= ${userServiceGetUsersResponse.body()}")
            }else{
                alert("Unauthenticated user", "Error")
            }
            val saveUserResponse = userService.saveUser(
                RegistrationDto(
                    "Janes Saenz Puerta",
                    "janes.saenz@vasslatam.com",
                    "Jasapu21*"
                )
            )
            if (saveUserResponse.isSuccessful){
                Log.d("Developer", "Created user: ${saveUserResponse.body()}")
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }
    private fun alert(messages: String, title: String){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this)
        //set message for alert dialog
        val view = layoutInflater.inflate(R.layout.alert, null)
        val tvTitle: TextView = view.findViewById(R.id.title_alert)
        val tvDetail: TextView = view.findViewById(R.id.text_alert)
        val btDone: Button = view.findViewById(R.id.button_alert)
        tvDetail.text = messages
        tvTitle.text = title
        btDone.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })
        builder.setView(view)
        // create and show the alert dialog
        dialog = builder.create()
        dialog.show()
    }
}