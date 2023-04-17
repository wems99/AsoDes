package com.example.asodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.AuthController
import com.example.asodes.infrastructure.seeders.SeedCivilStatus
import com.example.asodes.infrastructure.seeders.SeedDatabase
import com.example.asodes.infrastructure.seeders.seedDatabase
import com.example.asodes.infrastructure.services.CreateCivilStatusService
import com.example.asodes.infrastructure.utils.BackgroundRunner
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var logInButton: Button
    private lateinit var userExitAppButton: Button
    private lateinit var userTextField: EditText
    private lateinit var userPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Database path", getDatabaseInstance().openHelper.readableDatabase.path)

        initElements()
        initListeners()
    }

    private fun initElements() {
        logInButton = findViewById(R.id.loginUserButton)
        userExitAppButton = findViewById(R.id.userExitAppButton)
        userTextField = findViewById(R.id.userLoginEditext)
        userPassword = findViewById(R.id.loginUserPassword)
    }

    private fun initListeners() {
        userExitAppButton.setOnClickListener(::onUserExitAppButton)
        logInButton.setOnClickListener(::onLogInButtonClick)
    }

    private fun onLogInButtonClick(view: View){
        //Log in logic
    }

    private fun onUserExitAppButton(view: View) {

        this.finishAffinity()

    }
}