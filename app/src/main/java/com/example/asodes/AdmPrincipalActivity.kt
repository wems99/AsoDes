package com.example.asodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.alledic.asodes.R

class AdmPrincipalActivity : AppCompatActivity() {

    private lateinit var agregarNuevoCLienteButton: Button
    private lateinit var asignarPrestamoButtton: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_principal)

        initElements()
        initListeners()

    }

    private fun initListeners() {
        agregarNuevoCLienteButton.setOnClickListener(::onAgregarNuevoCLienteButtonClick)
        asignarPrestamoButtton.setOnClickListener(::onAsignarPrestamoButttonClick)
        btnLogout.setOnClickListener(::onBtnLogoutClick)
    }

    private fun initElements() {
        agregarNuevoCLienteButton = findViewById(R.id.AdmNewClientButton)
        asignarPrestamoButtton = findViewById(R.id.AdmAsignarPrestamoButton)
        btnLogout = findViewById(R.id.buttonLogoutAdmPrin)
    }

    private fun onAgregarNuevoCLienteButtonClick(view: View){
        val intent = Intent(this, com.example.asodes.CreateClientActivity::class.java)
        startActivity(intent)
    }

    private fun onAsignarPrestamoButttonClick(view: View){
        val intent = Intent(this, com.example.asodes.AssignLoanActivity::class.java)
        startActivity(intent)
    }

    private fun onBtnLogoutClick(view: View){
        SessionManager.userId?.equals(null)
         val intent = Intent(this, com.example.asodes.MainActivity::class.java)
         startActivity(intent)
    }

}