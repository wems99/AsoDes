package com.example.asodes

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.CivilStatusController
import com.example.asodes.infrastructure.controllers.ClientController
import com.example.asodes.infrastructure.controllers.UserController
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.utils.BackgroundRunner
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class infPersonalActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var salaryEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var dateOfBirthEditText: EditText
    private lateinit var civilStatusSpinner: Spinner
    private lateinit var passwordEditText: EditText
    private lateinit var btnBackForm: Button
    private lateinit var submitButton: Button
    private var selectedCivilStatusId: Long? = null
    private var client: Client? = null
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inf_personal)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        salaryEditText = findViewById(R.id.salaryEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText)
        civilStatusSpinner = findViewById(R.id.civilStatusSpinner)
        passwordEditText = findViewById(R.id.passwordEditText)
        submitButton = findViewById(R.id.submitButton)
        btnBackForm = findViewById(R.id.buttonBackForm)


        dateOfBirthEditText.inputType = InputType.TYPE_NULL
        dateOfBirthEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this@infPersonalActivity,
                { _, year, month, dayOfMonth ->
                    dateOfBirthEditText.setText("${twoDigitsFormat(dayOfMonth)}-${twoDigitsFormat(month + 1)}-${year}")
                }, year, month, dayOfMonth)

            datePickerDialog.show()
        }

        BackgroundRunner.run {
            client = SessionManager.userId?.let { ClientController.retrieveClient(it) }

            user = UserController.retrieveUser(client!!.userId)
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
            val defaultOptionIndex = civilStatusOptions.indexOfFirst { it.id == client!!.civilStatusId }

            runOnUiThread{
                fullNameEditText.setText(user!!.name)
                salaryEditText.setText(client!!.salary.toString())
                phoneEditText.setText(client!!.phone)
                addressEditText.setText(client!!.address)
                val dob = Calendar.getInstance()
                dob.time = client!!.dateOfBirth
                val year = dob.get(Calendar.YEAR)
                val month = dob.get(Calendar.MONTH)
                val dayOfMonth = dob.get(Calendar.DAY_OF_MONTH)
                dateOfBirthEditText.setText("${twoDigitsFormat(dayOfMonth)}-${twoDigitsFormat(month + 1)}-${year}")
                civilStatusSpinner.setSelection(defaultOptionIndex)
                passwordEditText.setText(user!!.password)
            }

        }

        submitButton.setOnClickListener {
            if (validateFields()) {
                BackgroundRunner.run {

                    try {
                        val updated = sendForm()
                        showToast("Client ${if (updated) "successfully" else "not"} updated")
                        val intent =
                            Intent(this, com.example.asodes.clientePantallaPrincipalActivity::class.java)
                        startActivity(intent)

                    } catch (e: Throwable) {
                        showToast(e.message)
                    }
                }

            }
        }

        btnBackForm.setOnClickListener{
            val intent = Intent(this, com.example.asodes.clientePantallaPrincipalActivity::class.java)
            startActivity(intent)
        }

    }

    private fun twoDigitsFormat(num: Int): String {
        return if (num < 10) "0$num" else "$num"
    }


    private fun validateFields(): Boolean {
        var isValid = true

        // Validate full name field
        if (fullNameEditText.text.isNullOrEmpty()) {
            fullNameEditText.error = "Please enter your full name"
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
        } else if (parseDate(dateOfBirthEditText.text.toString()) == null) {
            dateOfBirthEditText.error = "Invalid date format: should be dd-MM-yyyy"
            isValid = false
        }

        // Validate password field
        if (passwordEditText.text.isNullOrEmpty()) {
            passwordEditText.error = "Please enter a password"
            isValid = false
        }

        return isValid
    }

    private fun showToast(message: String?) {
        runOnUiThread {
            Toast.makeText(
                this,
                message ?: "An error occurred",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun parseDate(dateString: String): LocalDate? {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return try {
            LocalDate.parse(dateString, formatter)
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun sendForm(): Boolean {
        var clientUpdated: Client? = null
        var userUpdated: User?  = null

        if (client !== null && user !== null) {
            var passwordField = passwordEditText.text.toString()
            var salaryField = salaryEditText.text.toString()
            var dateOfBirthField = dateOfBirthEditText.text.toString()
            var addressField = addressEditText.text.toString()
            var phoneField = phoneEditText.text.toString()
            var civilEstField = selectedCivilStatusId
            var fullNameField = fullNameEditText.text.toString()
            user!!.name = fullNameField
            user!!.password = passwordField
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val dateOfBirth = dateFormat.parse(dateOfBirthField)
            client!!.dateOfBirth = dateOfBirth
            if (civilEstField != null) {
                client!!.civilStatusId = civilEstField
            }
            client!!.phone = phoneField
            client!!.salary = salaryField.toDouble()
            client!!.address = addressField

            clientUpdated = ClientController.updateClient(client!!)
            userUpdated = UserController.updateUser(user!!)
        }
        return clientUpdated != null && userUpdated != null
    }

}