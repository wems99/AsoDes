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
import com.example.asodes.infrastructure.exceptions.NotEnoughSalary
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.BackgroundRunner
import org.json.JSONObject


class CalculateLoanActivity : AppCompatActivity() {

    private lateinit var loanPercentageEditText: EditText
    private lateinit var creditTypeSpinner: Spinner
    private lateinit var loanYearsSpinner: Spinner
    private lateinit var loanAmountEditText: EditText
    private lateinit var monthlyFeeEditText: EditText
    private lateinit var btnBackForm: Button
    private var selectedCreditType: Long? = null
    private var selectedLoanYears: Long? = null
    private var clientSalary: Double? = null
    private var creditTypePercentage: Double? = null
    private var loanYears: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_loan)

        loanPercentageEditText = findViewById(R.id.loanPercentageEditText)
        creditTypeSpinner = findViewById(R.id.creditTypeSpinner)
        loanYearsSpinner = findViewById(R.id.loanYearsSpinner)
        loanAmountEditText = findViewById(R.id.loanAmountEditText)
        monthlyFeeEditText = findViewById(R.id.monthlyFeeEditText)
        btnBackForm = findViewById(R.id.buttonBackForm)

        BackgroundRunner.run {
            this.clientSalary = SessionManager.userId?.let { findClient(it)?.salary }
        }

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
            val intent = Intent(this, com.example.asodes.clientePantallaPrincipalActivity::class.java)
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
            val monthly = loanAmount / months
            val fee = monthly * (creditTypePercentage!! / 100)
            val total = monthly + fee
            monthlyFeeEditText.setText("$total")
        } else {
            monthlyFeeEditText.setText("")
        }
    }

    private suspend fun findClient(clientId: Long): Client? {
        return ClientController.retrieveClient(clientId)
    }
}