package com.example.asodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.ClientController
import com.example.asodes.infrastructure.controllers.CreditTimeController
import com.example.asodes.infrastructure.controllers.CreditTypeController
import com.example.asodes.infrastructure.utils.BackgroundRunner

class asignarPrestamoActivity : AppCompatActivity() {

    private lateinit var findId: EditText
    private lateinit var btnBack: Button
    private lateinit var userInfo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_prestamo)

        initElements()
        initListeners()



        //Periods and Loans chooses
        if(validateFields()){
            BackgroundRunner.run {
                var listPeriods = CreditTimeController.retrieveAll()
                var user = ClientController.retrieveClient(findId.text.toString().toLong())

                val stringBuilder = StringBuilder()
                stringBuilder.append("ID: ").append((user!!.id)).append("\n")
                stringBuilder.append("ADDRESS: ").append((user.address)).append("\n")
                stringBuilder.append("PHONE: ").append((user.phone)).append("\n")
                stringBuilder.append("CIVIL STATUS: ").append((user.civilStatusId)).append("\n")
                stringBuilder.append("SALARY: ").append((user.salary)).append("\n")
                var objectString = stringBuilder.toString()
                userInfo.setText(objectString)

                val radioBtnGroupPeriods = findViewById<RadioGroup>(R.id.RadioGroupPeriodo)
                for(periods in listPeriods){
                    val radioButton = RadioButton(this)
                    radioButton.text = "periods: ${periods.years} years"
                    radioButton.id = periods.id.toInt()
                    radioBtnGroupPeriods.addView(radioButton)
                }

                val listLoans = CreditTypeController.retrieveAll()
                val radioBtnGroupLoans = findViewById<RadioGroup>(R.id.RadioGoupTipoCredito)
                for(loans in listLoans){
                    val radioButton = RadioButton(this)
                    radioButton.text = "Type of Loan: ${loans.name}"
                    radioButton.id = loans.id.toInt()
                    radioBtnGroupLoans.addView(radioButton)
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        // Validate id field
        if (findId.text.isNullOrEmpty()) {
            findId.error = "Please enter the id to look for it"
            isValid = false
        }
        return isValid
    }

    private fun initListeners() {
        btnBack.setOnClickListener(::onBtnBackClick)
    }

    private fun initElements() {
        findId = findViewById(R.id.IdUserEditTextAsignarPrestamo)
        btnBack = findViewById(R.id.btnAsignarPrestamosBack)
        userInfo = findViewById(R.id.editTextMultiLineUserInf)
    }



    private fun onBtnBackClick(view: View){
        val intent = Intent(this, com.example.asodes.AdmPrincipalActivity::class.java)
        startActivity(intent)
    }

}


