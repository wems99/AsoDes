package com.example.asodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.CreditTimeController
import com.example.asodes.infrastructure.controllers.CreditTypeController
import com.example.asodes.infrastructure.utils.BackgroundRunner

class asignarPrestamoActivity : AppCompatActivity() {

    private lateinit var findId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_prestamo)

        initElements()

        BackgroundRunner.run {
            var listPeriods = CreditTimeController.retrieveAll()
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

    private fun initElements() {
        findId = findViewById(R.id.IdUserEditTextAsignarPrestamo)

    }

}


