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
    private lateinit var btnLogoutAdmPrincipal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_principal)

        initElements()
        initListeners()

    }

    private fun initListeners() {
        agregarNuevoCLienteButton.setOnClickListener(::onAgregarNuevoCLienteButtonClick)
        asignarPrestamoButtton.setOnClickListener(::onAsignarPrestamoButttonClick)
        btnLogoutAdmPrincipal.setOnClickListener(::onBtnLogoutClick)
    }

    private fun initElements() {
        agregarNuevoCLienteButton = findViewById(R.id.AdmNewClientButton)
        asignarPrestamoButtton = findViewById(R.id.AdmAsignarPrestamoButton)
        btnLogoutAdmPrincipal = findViewById(R.id.buttonLogoutAdmPrin)
    }

    private fun onBtnLogoutClick(view: View){
        SessionManager.userId = null
        val intent = Intent(this, com.example.asodes.MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    private fun onAgregarNuevoCLienteButtonClick(view: View){
        val intent = Intent(this, com.example.asodes.CreateClientActivity::class.java)
        startActivity(intent)
    }

    private fun onAsignarPrestamoButttonClick(view: View){
        val intent = Intent(this, com.example.asodes.AssignLoanActivity::class.java)
        startActivity(intent)
    }

}