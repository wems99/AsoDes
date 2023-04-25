package com.example.asodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.AuthController
import com.example.asodes.infrastructure.exceptions.AuthenticationException
import com.example.asodes.infrastructure.seeders.SeedCivilStatus
import com.example.asodes.infrastructure.seeders.SeedDatabase
import com.example.asodes.infrastructure.seeders.seedDatabase
import com.example.asodes.infrastructure.services.AuthenticateUserService
import com.example.asodes.infrastructure.services.CreateCivilStatusService
import com.example.asodes.infrastructure.utils.BackgroundRunner
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject
import kotlin.math.log


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
        if(validateFields()){
            BackgroundRunner.run {
                val credenciales = JSONObject()
                credenciales.put("username", userTextField.text.toString())
                credenciales.put("password", userPassword.text.toString())
                try {
                    var user = AuthController.authenticate(credenciales)
                    SessionManager.userId =  user!!.id
                    if(user!!.isAdmin){
                        val intent = Intent(this, com.example.asodes.AdmPrincipalActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this, com.example.asodes.clientePantallaPrincipalActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }catch (e: AuthenticationException){
                    runOnUiThread{
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        // Validate full name field
        if (userTextField.text.isNullOrEmpty()) {
            userTextField.error = "Please enter your full name"
            isValid = false
        }

        // Validate password field
        if (userPassword.text.isNullOrEmpty()) {
            userPassword.error = "Please enter a password"
            isValid = false
        }

        return isValid
    }

    private fun onUserExitAppButton(view: View) {

        this.finishAffinity()

    }
}