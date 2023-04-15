package com.example.asodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alledic.asodes.R

class asignarPrestamoActivity : AppCompatActivity() {

    private lateinit var findId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_prestamo)

        initElements()

        val radioGroupPeriodo = findViewById<RadioGroup>(R.id.RadioGroupPeriodo)
        radioGroupPeriodo.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedOption = radioButton.text.toString()
            // Do something with the selected option
        }

        val radioGroupCredito = findViewById<RadioGroup>(R.id.RadioGoupTipoCredito)
        radioGroupCredito.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedOption = radioButton.text.toString()
            // Do something with the selected option
        }

    }

    private fun initElements() {
        findId = findViewById(R.id.IdUserEditTextAsignarPrestamo)

    }

}


