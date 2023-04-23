package com.example.asodes

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.CivilStatusController
import com.example.asodes.infrastructure.controllers.ClientController
import com.example.asodes.infrastructure.controllers.SavingsPlanController
import com.example.asodes.infrastructure.controllers.SavingsTypeController
import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan
import com.example.asodes.infrastructure.exceptions.NotEnoughSalary
import com.example.asodes.infrastructure.exceptions.SavingsPlanMinAmountException
import com.example.asodes.infrastructure.utils.BackgroundRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class ClientSavingsActivity : AppCompatActivity() {

    private lateinit var savingsContainer: LinearLayout
    private lateinit var backFormButton: Button
    private var notTriggeredByUser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_savings)

        initElements()
        initEvents()
        renderSavings()

    }

    private fun initElements() {
        this.savingsContainer = findViewById(R.id.savings_container)
        this.backFormButton = findViewById(R.id.buttonBackForm)
    }

    private fun initEvents() {
        onBackFormButtonClicked()
    }

    private fun onBackFormButtonClicked() {
        this.backFormButton.setOnClickListener {
            val intent = Intent(this, com.example.asodes.clientePantallaPrincipalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun renderSavings() {
        val clientId = SessionManager.userId ?: return

        BackgroundRunner.run {
            val savings = SavingsPlanController.retrieveClientPlans(clientId)

            for (saving in savings) {
                val savingsType = SavingsTypeController.retrieveSavingsPlan(saving.savingsTypeId)

                runOnUiThread {
                    val label = TextView(this)
                    savingsContainer.addView(label)
                    val numericField = createEditNumericText()
                    val container = createLinearLayout()
                    val switch = createSwitch()


                    label.text = savingsType!!.name
                    switch.isChecked = saving.active
                    numericField.setText(saving.amount.toString())


                    onSwitchClicked(switch, saving, savingsType.name)
                    onEditTextChange(numericField, saving, savingsType.name)
                    container.addView(numericField)
                    container.addView(switch)
                }
            }
        }

    }

    private fun onSwitchClicked(switch: Switch, saving: SavingsPlan, savingTypeName: String) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            saving.active = isChecked
            BackgroundRunner.run {
                try {
                    val newSaving = SavingsPlanController.updateClientPlan(saving)
                    showToast("$savingTypeName is now ${if (newSaving!!.active) "active" else "inactive"}")
                } catch (e: SavingsPlanMinAmountException) {
                    showToast("$savingTypeName cannot be ${if (isChecked) "activated" else "deactivated"} if amount is less than 5000")
                    runOnUiThread {
                        saving.active = !isChecked
                        switch.setOnCheckedChangeListener(null)
                        switch.isChecked = !isChecked
                        onSwitchClicked(switch, saving, savingTypeName)
                    }
                } catch (e: NotEnoughSalary) {
                    showToast(e.message)
                    runOnUiThread {
                        saving.active = !isChecked
                        switch.setOnCheckedChangeListener(null)
                        switch.isChecked = !isChecked
                        onSwitchClicked(switch, saving, savingTypeName)
                    }
                }
            }
        }
    }

    private fun onEditTextChange(editText: EditText, saving: SavingsPlan, savingTypeName: String) {
        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val amount = s?.toString()?.toDoubleOrNull()
                if (amount == null) {
                    editText.error = "Amount must be at least 5000"
                } else if (amount < 5000) {
                    editText.error = "Amount must be at least 5000"
                } else {
                    editText.error = null
                    BackgroundRunner.run {
                        if (amount != null) {
                            saving.amount = amount
                            BackgroundRunner.run {
                                try {
                                    val newSaving = SavingsPlanController.updateClientPlan(saving)
                                    showToast("$savingTypeName is now ${newSaving!!.amount}")
                                } catch (e: NotEnoughSalary) {
                                    showToast(e.message)
                                }
                            }
                        }
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun createEditNumericText(): EditText {
        val numericField = EditText(this)
        numericField.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        numericField.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        return numericField
    }

    private fun createLinearLayout(): LinearLayout {
        val container = LinearLayout(this)
        container.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        container.orientation = LinearLayout.HORIZONTAL
        savingsContainer.addView(container)

        return container
    }

    private fun createSwitch(): Switch {
        val switch = Switch(this)
        switch.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        return switch
    }

    private fun showToast(message: String?) {
        runOnUiThread {
            Toast.makeText(
                this,
                message ?: "An error occurred",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}