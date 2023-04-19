package com.example.asodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.alledic.asodes.R

import android.view.View
import android.widget.Button
import android.widget.EditText
import org.json.JSONObject


class NewUserFormActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var aaddressEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var estCivilEditText: EditText
    private lateinit var dateUser: EditText
    private lateinit var idUser: EditText
    private lateinit var addBtn: Button
    private lateinit var salaryUser: EditText
    private lateinit var btnBack:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user_form)
        initElements()
        initListeners()
    }

    private fun initListeners() {
        addBtn.setOnClickListener(::onAddBtnClick)
        btnBack.setOnClickListener(::onBtnBackClick)
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
        salaryUser = findViewById(R.id.editTextNumberSalary)
        btnBack = findViewById(R.id.previewbtnTest)

    }

    private fun onAddBtnClick(view: View){

        val newUser = JSONObject()
        newUser.put("userId", idUser.text.toString().toLong())
        newUser.put("salary", salaryUser.text.toString().toDouble())
        newUser.put("phone", phoneEditText.text.toString())
        newUser.put("dateOfBirth", dateUser.text.toString())
        newUser.put("civilStatusId",estCivilEditText.text.toString())

    }

    private fun onBtnBackClick(view: View){
        val intent = Intent(this, com.example.asodes.AdmPrincipalActivity::class.java)
        startActivity(intent)
    }

}