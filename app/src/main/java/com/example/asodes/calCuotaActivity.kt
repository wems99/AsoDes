package com.example.asodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.CreditTypeController
import com.example.asodes.infrastructure.utils.BackgroundRunner

class calCuotaActivity : AppCompatActivity() {

    private lateinit var btnBackCalCuotas: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cal_cuota)

        BackgroundRunner.run {
            val listLoans = CreditTypeController.retrieveAll()
            val radioBtnGroupLoans = findViewById<RadioGroup>(R.id.RadioGroupCalCuota)
            for(loans in listLoans){
                val radioButton = RadioButton(this)
                radioButton.text = "Type of Loan: ${loans.name}"
                radioButton.id = loans.id.toInt()
                radioBtnGroupLoans.addView(radioButton)
            }

        }

        btnBackCalCuotas = findViewById(R.id.buttonBackCalCuota)
        btnBackCalCuotas.setOnClickListener(){
            val intent = Intent(this, com.example.asodes.clientePantallaPrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}