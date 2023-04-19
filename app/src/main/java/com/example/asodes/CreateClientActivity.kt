package com.example.asodes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.CivilStatusController
import com.example.asodes.infrastructure.controllers.ClientController
import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.utils.BackgroundRunner
import org.json.JSONObject

class CreateClientActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var idNumberEditText: EditText
    private lateinit var salaryEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var dateOfBirthEditText: EditText
    private lateinit var civilStatusSpinner: Spinner
    private lateinit var passwordEditText: EditText
    private lateinit var btnBackForm: Button
    private lateinit var submitButton: Button
    private var selectedCivilStatusId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_client)

        fullNameEditText = findViewById(R.id.fullNameEditText)
        idNumberEditText = findViewById(R.id.idNumberEditText)
        salaryEditText = findViewById(R.id.salaryEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText)
        civilStatusSpinner = findViewById(R.id.civilStatusSpinner)
        passwordEditText = findViewById(R.id.passwordEditText)
        submitButton = findViewById(R.id.submitButton)
        btnBackForm = findViewById(R.id.buttonBackForm)

        BackgroundRunner.run {
            val civilStatusOptions = CivilStatusController.retrieveAll()
            civilStatusSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, civilStatusOptions.map { it.name })
            civilStatusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedCivilStatusId = civilStatusOptions[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Nothing
                }
            }
        }

        submitButton.setOnClickListener {
            if (validateFields()) {
                // Perform submit action here
                BackgroundRunner.run {
                    val client = sendForm()
                    Log.d("client inf", client.toString())
                    runOnUiThread{
                        Toast.makeText(this, "Client ${if (client != null) "succefully" else "not"} created", Toast.LENGTH_LONG).show()
                    }
                    val intent = Intent(this, com.example.asodes.AdmPrincipalActivity::class.java)
                    startActivity(intent)
                }

            }
        }

        btnBackForm.setOnClickListener{
            val intent = Intent(this, com.example.asodes.AdmPrincipalActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateFields(): Boolean {
        var isValid = true

        // Validate full name field
        if (fullNameEditText.text.isNullOrEmpty()) {
            fullNameEditText.error = "Please enter your full name"
            isValid = false
        }

        // Validate ID number field
        if (idNumberEditText.text.isNullOrEmpty()) {
            idNumberEditText.error = "Please enter your ID number"
            isValid = false
        }

        // Validate salary field
        if (salaryEditText.text.isNullOrEmpty()) {
            salaryEditText.error = "Please enter your salary"
            isValid = false
        }

        // Validate phone number field
        if (phoneEditText.text.isNullOrEmpty()) {
            phoneEditText.error = "Please enter your phone number"
            isValid = false
        }

        // Validate address field
        if (addressEditText.text.isNullOrEmpty()) {
            addressEditText.error = "Please enter your address"
            isValid = false
        }

        // Validate date of birth field
        if (dateOfBirthEditText.text.isNullOrEmpty()) {
            dateOfBirthEditText.error = "Please enter your date of birth"
            isValid = false
        }

        // Validate password field
        if (passwordEditText.text.isNullOrEmpty()) {
            passwordEditText.error = "Please enter a password"
            isValid = false
        }

        return isValid
    }

    suspend private fun sendForm(): Client? {

        var passwordField = passwordEditText.text.toString()
        var salaryField = salaryEditText.text.toString()
        var DateOfBithField = dateOfBirthEditText.text.toString()
        var addressField = addressEditText.text.toString()
        var phoneField = phoneEditText.text.toString()
        var idField = idNumberEditText.text.toString()
        var civilEstField = selectedCivilStatusId
        var fullNameField = fullNameEditText.text.toString()
        var userName = idField
        var user = JSONObject()
        user.put("name", fullNameField)
        user.put("username", userName)
        user.put("password", passwordField)

        var client = JSONObject()
        client.put("user", user)
        client.put("salary", salaryField)
        client.put("phone", phoneField)
        client.put("address", addressField)
        client.put("dateOfBirth", DateOfBithField)
        client.put("civilStatusId", civilEstField)
        client.put("id", idField)

        return ClientController.createClient(client)

    }
}