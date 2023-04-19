package com.example.asodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.LoanController
import com.example.asodes.infrastructure.controllers.SavingsTypeController
import com.example.asodes.infrastructure.utils.BackgroundRunner

class verPrestamosActivity : AppCompatActivity() {

    private lateinit var btnBackverPrestamos: Button
    private lateinit var btnPayPrestamos: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_prestamos)



        btnBackverPrestamos = findViewById(R.id.buttonBackVerPrestamos)
        btnPayPrestamos = findViewById(R.id.buttonPayPrestamos)
        btnBackverPrestamos.setOnClickListener(){
            val intent = Intent(this, clientePantallaPrincipalActivity::class.java)
            startActivity(intent)
        }

        BackgroundRunner.run {

            val listLoan = LoanController.retrieveLoansByClient(1)
            val radioBtnGroupPeriods = findViewById<RadioGroup>(R.id.ButtonGroupVerPrestamos)
            for(loans in listLoan){
                val radioButton = RadioButton(this)
                radioButton.text = "periods: ${loans.contributions} $"
                radioButton.id = loans.id.toInt()
                radioBtnGroupPeriods.addView(radioButton)
            }

            btnPayPrestamos.setOnClickListener(){
                //logic pay loans
            }
        }



    }
}