package com.example.asodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.alledic.asodes.R

class clientePantallaPrincipalActivity : AppCompatActivity() {

    private lateinit var buttonLogout: Button
    private lateinit var buttonVerPrestamos: Button
    private lateinit var buttonGestAhorro: Button
    private lateinit var buttonCalCuota: Button
    private lateinit var buttonInfPersonal: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_pantalla_principal)
        initElements()
        initListeners()
    }

    private fun initListeners() {
        buttonLogout.setOnClickListener(::onButtonLogoutClick)
        buttonCalCuota.setOnClickListener(::onButtonCalCuotaClick)
        buttonGestAhorro.setOnClickListener(::onButtonGestAhorroClick)
        buttonVerPrestamos.setOnClickListener(::onButtonVerPrestamosClick)
        buttonInfPersonal.setOnClickListener(::onButtonInfPersonalClick)
    }

    private fun initElements() {
        buttonLogout = findViewById(R.id.buttonLogOut)
        buttonCalCuota = findViewById(R.id.buttonCalculaCuota)
        buttonGestAhorro = findViewById(R.id.buttonGestionarAhorros)
        buttonInfPersonal = findViewById(R.id.buttonInfPersonal)
        buttonVerPrestamos = findViewById(R.id.buttonVerPrestamos)
    }

    private fun onButtonLogoutClick(view: View) {
       // val intent = Intent(this, com.example.asodes.::class.java)
       // startActivity(intent)
    }

    private fun onButtonCalCuotaClick(view: View) {
        val intent = Intent(this, com.example.asodes.CalculateLoanActivity::class.java)
        startActivity(intent)
    }

    private fun onButtonGestAhorroClick(view: View) {
        val intent = Intent(this, com.example.asodes.ClientSavingsActivity::class.java)
        startActivity(intent)
    }

    private fun onButtonVerPrestamosClick(view: View) {
        val intent = Intent(this, com.example.asodes.LoanListActivity::class.java)
        startActivity(intent)
    }

    private fun onButtonInfPersonalClick(view: View) {
        val intent = Intent(this, com.example.asodes.infPersonalActivity::class.java)
        startActivity(intent)
    }


}