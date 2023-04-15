package com.example.asodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.alledic.asodes.R

class AdmPrincipalActivity : AppCompatActivity() {

    private lateinit var agregarNuevoCLienteButton: Button
    private lateinit var asignarPrestamoButtton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_principal)

        initElements()
        initListeners()

    }

    private fun initListeners() {
        agregarNuevoCLienteButton.setOnClickListener(::onAgregarNuevoCLienteButtonClick)
        asignarPrestamoButtton.setOnClickListener(::onAsignarPrestamoButttonClick)
    }

    private fun initElements() {
        agregarNuevoCLienteButton = findViewById(R.id.AdmNewClientButton)
        asignarPrestamoButtton = findViewById(R.id.AdmAsignarPrestamoButton)
    }

    private fun onAgregarNuevoCLienteButtonClick(view: View){

    }

    private fun onAsignarPrestamoButttonClick(view: View){

    }

}