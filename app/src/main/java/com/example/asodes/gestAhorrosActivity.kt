package com.example.asodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.SavingsTypeController
import com.example.asodes.infrastructure.utils.BackgroundRunner

class gestAhorrosActivity : AppCompatActivity() {

    private lateinit var btnBackGestAhorro: Button
    private lateinit var btnSaveGesAhorro:Button
    private lateinit var amountPerMonth: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gest_ahorros)

        amountPerMonth = findViewById(R.id.editTextNumberAmount)

        btnBackGestAhorro = findViewById(R.id.buttonBackGestAhorro)
        btnSaveGesAhorro = findViewById(R.id.buttonSaveGestAhorro)
        btnBackGestAhorro.setOnClickListener(){
            val intent = Intent(this, clientePantallaPrincipalActivity::class.java)
            startActivity(intent)
        }

        BackgroundRunner.run {
            val ListsavingPlans = SavingsTypeController.retrieveAll()
            val radioBtnGroupPeriods = findViewById<RadioGroup>(R.id.ButtonGroupGestAhorros)
            for(savingPLans in ListsavingPlans){
                val radioButton = RadioButton(this)
                radioButton.text = "Saving plan: ${savingPLans.name}"
                radioButton.id = savingPLans.id.toInt()
                radioBtnGroupPeriods.addView(radioButton)
            }

            btnSaveGesAhorro.setOnClickListener(){
                //save logic db
            }
        }


    }
}