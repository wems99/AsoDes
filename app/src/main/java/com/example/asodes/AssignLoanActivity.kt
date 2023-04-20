package com.example.asodes

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.ClientController
import com.example.asodes.infrastructure.controllers.CreditTimeController
import com.example.asodes.infrastructure.controllers.CreditTypeController
import com.example.asodes.infrastructure.controllers.LoanController
import com.example.asodes.infrastructure.controllers.UserController
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.exceptions.NoRecordFoundException
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.BackgroundRunner
import org.json.JSONObject


class AssignLoanActivity : AppCompatActivity() {

    private lateinit var clientIdEditText: EditText
    private lateinit var searchClientButton: Button
    private lateinit var clientNameEditText: EditText
    private lateinit var clientSalaryEditText: EditText
    private lateinit var loanPercentageEditText: EditText
    private lateinit var creditTypeSpinner: Spinner
    private lateinit var loanYearsSpinner: Spinner
    private lateinit var loanAmountEditText: EditText
    private lateinit var monthlyFeeEditText: EditText
    private lateinit var assignButton: Button
    private lateinit var btnBackForm: Button
    private var selectedCreditType: Long? = null
    private var selectedLoanYears: Long? = null
    private var clientSalary: Double? = null
    private var creditTypePercentage: Double? = null
    private var loanYears: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assign_loan_activity)

        clientIdEditText = findViewById(R.id.clientIdEditText)
        searchClientButton = findViewById(R.id.searchClientButton)
        clientNameEditText = findViewById(R.id.clientNameEditText)
        clientSalaryEditText = findViewById(R.id.clientSalaryEditText)
        loanPercentageEditText = findViewById(R.id.loanPercentageEditText)
        creditTypeSpinner = findViewById(R.id.creditTypeSpinner)
        loanYearsSpinner = findViewById(R.id.loanYearsSpinner)
        loanAmountEditText = findViewById(R.id.loanAmountEditText)
        monthlyFeeEditText = findViewById(R.id.monthlyFeeEditText)
        assignButton = findViewById(R.id.assignButton)
        btnBackForm = findViewById(R.id.buttonBackForm)

        BackgroundRunner.run {
            val creditTypeOptions = CreditTypeController.retrieveAll()
            creditTypePercentage = creditTypeOptions[0].percentage
            creditTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, creditTypeOptions.map { "${it.name} (${it.percentage} %)" })
            creditTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedCreditType = creditTypeOptions[position].id
                    creditTypePercentage =  creditTypeOptions[position].percentage
                    calculateLoanFields()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Nothing
                }
            }

            val loanYearsOptions = CreditTimeController.retrieveAll()
            loanYearsSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, loanYearsOptions.map { "${it.name} (${it.years} Years)" })
            loanYears = loanYearsOptions[0].years
            loanYearsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedLoanYears = loanYearsOptions[position].id
                    loanYears = loanYearsOptions[position].years
                    calculateLoanFields()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Nothing
                }
            }
        }

        searchClientButton.setOnClickListener {
            if (clientIdEditText.text.isNullOrEmpty()) {
                 clientIdEditText.error = "Please enter a client id"
            } else {
                BackgroundRunner.run {
                    try {
                        val client = findClient(clientIdEditText.text.toString().toLong())
                        val user = findUser(client!!.userId)
                        clientSalary = client.salary
                        runOnUiThread {
                            clientNameEditText.setText(user!!.name)
                            clientSalaryEditText.setText(client.salary.toString())
                        }
                    } catch (e: NoRecordFoundException) {
                        showToast(e.message)
                        clientSalary  = null
                        creditTypePercentage  = null
                        loanYears = null
                    }
                }
            }
        }

        assignButton.setOnClickListener {
            if (validateFields()) {
                BackgroundRunner.run {
                    try {
                        val loan = sendForm()
                        showToast("Loan ${if (loan != null) "successfully" else "not"} created")
                        val intent =
                            Intent(this, com.example.asodes.AdmPrincipalActivity::class.java)
                        startActivity(intent)
                    } catch (e: UnableToCreateRecord) {
                        showToast(e.message)
                    }
                }
            }
        }

        loanPercentageEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Nothing
            }

            override fun afterTextChanged(s: Editable?) {
                calculateLoanFields()
            }

        })

        btnBackForm.setOnClickListener{
            val intent = Intent(this, com.example.asodes.AdmPrincipalActivity::class.java)
            startActivity(intent)
        }
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

    private fun calculateLoanFields() {
        if (clientSalary != null && !loanPercentageEditText.text.isNullOrEmpty()) {
            val loanPercentage = loanPercentageEditText.text.toString().toDouble()
            val loan = clientSalary!! * (loanPercentage / 100)
            loanAmountEditText.setText("$loan")
        } else {
            loanAmountEditText.setText("")
        }

        if (!loanAmountEditText.text.isNullOrEmpty() && loanYears != null && creditTypePercentage != null) {
            val months = loanYears!! * 12
            val loanAmount = loanAmountEditText.text.toString().toDouble()
            val fee = (loanAmount / months) * creditTypePercentage!!
            monthlyFeeEditText.setText("$fee")
        } else {
            monthlyFeeEditText.setText("")
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (clientIdEditText.text.isNullOrEmpty()) {
            clientIdEditText.error = "Please enter a client id"
            isValid = false
        }

        if (clientNameEditText.text.isNullOrEmpty()) {
            clientNameEditText.error = "Please search a client"
            isValid = false
        }

        if (clientSalaryEditText.text.isNullOrEmpty()) {
            clientSalaryEditText.error = "Please search a client"
            isValid = false
        }

        if (loanPercentageEditText.text.isNullOrEmpty()) {
            loanPercentageEditText.error = "Please provide a loan percentage"
            isValid = false
        } else {
            val percentage = loanPercentageEditText.text.toString().toInt()
            if (percentage > 45 || percentage < 1) {
                loanPercentageEditText.error = "Please provide a valid loan percentage"
                isValid = false
            }
        }

        return isValid
    }

    private suspend fun sendForm(): Loan? {
        val creditTypeId = selectedCreditType
        val creditTimeId = selectedLoanYears
        val clientId = clientIdEditText.text.toString()
        val percentage = loanPercentageEditText.text.toString()
        val clientSalary = clientSalaryEditText.text.toString()

        val payload = JSONObject()
        payload.put("creditTypeId", creditTypeId)
        payload.put("creditTimeId", creditTimeId)
        payload.put("clientId", clientId)
        payload.put("percentage", percentage)
        payload.put("clientSalary", clientSalary)

        return LoanController.assignLoan(payload)
    }

    private suspend fun findClient(clientId: Long): Client? {
        return ClientController.retrieveClient(clientId)
    }

    private suspend fun findUser(clientId: Long): User? {
        return UserController.retrieveUser(clientId)
    }
}