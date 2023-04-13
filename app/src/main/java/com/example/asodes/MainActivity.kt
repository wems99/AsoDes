package com.example.asodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var logInButton: Button
    private lateinit var userExitAppButton: Button
    private lateinit var userTextField: EditText
    private lateinit var userPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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