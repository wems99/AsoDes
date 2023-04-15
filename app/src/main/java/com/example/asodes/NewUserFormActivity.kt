package com.example.asodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.alledic.asodes.R
import java.util.Date

class NewUserFormActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var aaddressEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var estCivilEditText: EditText
    private lateinit var dateUser: EditText
    private lateinit var idUser: EditText
    private lateinit var addBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user_form)
        initElements()
        initListeners()
    }

    private fun initListeners() {
        addBtn.setOnClickListener(::onAddBtnClick)
    }

    private fun initElements() {
        nameEditText = findViewById(R.id.textNameField)
        emailEditText = findViewById(R.id.textEmailField)
        aaddressEditText = findViewById(R.id.textaddressField)
        phoneEditText = findViewById(R.id.textPhoneField)
        estCivilEditText = findViewById(R.id.editTextEstCivil)
        idUser = findViewById(R.id.editTextNumberId)
        dateUser = findViewById(R.id.editTextDate)
        addBtn = findViewById(R.id.newUserAddButton)
    }

    private fun onAddBtnClick(view: View){

    }
}