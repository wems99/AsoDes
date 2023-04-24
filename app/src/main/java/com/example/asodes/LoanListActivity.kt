package com.example.asodes

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alledic.asodes.R
import com.example.asodes.infrastructure.controllers.CivilStatusController
import com.example.asodes.infrastructure.controllers.ClientController
import com.example.asodes.infrastructure.controllers.CreditTimeController
import com.example.asodes.infrastructure.controllers.CreditTypeController
import com.example.asodes.infrastructure.controllers.LoanController
import com.example.asodes.infrastructure.controllers.SavingsPlanController
import com.example.asodes.infrastructure.controllers.SavingsTypeController
import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.data.local.entity.Loan
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

class LoanListActivity : AppCompatActivity() {

    private lateinit var loanList: RecyclerView
    private lateinit var btnBackForm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_list)
        btnBackForm = findViewById(R.id.buttonBackForm)

        onBackFormButtonClicked()

        this.loanList = findViewById(R.id.loan_list)
        val adapter = LoanAdapter(this)
        loanList.adapter = adapter
        loanList.layoutManager = LinearLayoutManager(this)

       BackgroundRunner.run {
           val loans = SessionManager!!.userId?.let { LoanController.retrieveLoansByClient(it) }
           if (loans != null) {
               adapter.setLoans(loans)
           }
       }
    }

    private fun onBackFormButtonClicked() {
        this.btnBackForm.setOnClickListener {
            val intent = Intent(this, com.example.asodes.clientePantallaPrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}

class LoanAdapter(private val activity: Activity) : RecyclerView.Adapter<LoanAdapter.ViewHolder>() {

    private var loans = emptyList<Loan>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = loans[position]
        BackgroundRunner.run {
            val creditTime = CreditTimeController.retrieveCreditTime(current.creditTimeId)
            val creditType = CreditTypeController.retrieveCreditType(current.creditTypeId)
            val totalAmount = (current.clientSalary * (current.percentage) / 100.0)
            val monthly = (totalAmount / (creditTime!!.years * 12.0))
            val fee = monthly * (creditType!!.percentage / 100.0)
            val total = (fee + monthly) * (creditTime.years * 12.0)
            val moneyLeft = total - current.contributions
            holder.loanTypeView.text = "${creditType!!.name} (${creditType.percentage} %)"
            holder.loanPeriodView.text = "${creditTime!!.name} (${creditTime.years} Years)"
            holder.loanTotalAmountView.text = "Total: $totalAmount"
            holder.moneyLeftView.text = "Total left: $moneyLeft"
            holder.loanAmountView.text = "Total monthly: ${monthly + fee}"
            holder.payButton.setOnClickListener {
                BackgroundRunner.run {
                    current.addContribution(fee + monthly)
                    LoanController.updateLoan(current)
                    if (total - current.contributions <= 0.0) {
                        activity.runOnUiThread {
                            setLoans(loans.filter { it.id != current.id })
                            showToast("You fully paid the loan!")
                        }
                    } else {
                        activity.runOnUiThread {
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String?) {
        activity.runOnUiThread {
            Toast.makeText(
                activity,
                message ?: "An error occurred",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    internal fun setLoans(loans: List<Loan>) {
        this.loans = loans.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = loans.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loanTypeView: TextView = itemView.findViewById(R.id.loan_type)
        val loanPeriodView: TextView = itemView.findViewById(R.id.loan_period)
        val loanTotalAmountView: TextView = itemView.findViewById(R.id.loan_total_amount)
        val moneyLeftView: TextView = itemView.findViewById(R.id.loan_money_left)
        val loanAmountView: TextView = itemView.findViewById(R.id.loan_monthly_amount)
        val payButton: Button = itemView.findViewById(R.id.pay_button)
    }
}