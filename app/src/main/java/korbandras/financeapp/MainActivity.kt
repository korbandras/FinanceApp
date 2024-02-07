import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import korbandras.financeapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var editTextIncome: EditText
    private lateinit var editTextExpenses: EditText
    private lateinit var editTextDueDate: EditText
    private lateinit var editTextTargetSum: EditText
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newdata)

        editTextIncome = findViewById(R.id.editTextIncome)
        editTextExpenses = findViewById(R.id.editTextExpenses)
        editTextDueDate = findViewById(R.id.editTextDueDate)
        editTextTargetSum = findViewById(R.id.editTextTargetSum)
        textViewResult = findViewById(R.id.textViewResult)
    }

    fun calculateMonthlySavings(view: View) {
        val income = editTextIncome.text.toString().toDoubleOrNull() ?: 0.0
        val expenses = editTextExpenses.text.toString().toDoubleOrNull() ?: 0.0
        val dueDate = editTextDueDate.text.toString().toIntOrNull() ?: 0
        val targetSum = editTextTargetSum.text.toString().toDoubleOrNull() ?: 0.0

        if (income <= expenses || dueDate <= 0 || targetSum <= 0) {
            textViewResult.text = "It's not possible"
        } else {
            val monthlySavings = (targetSum / dueDate) - (expenses - income)
            if (monthlySavings <= 0) {
                textViewResult.text = "It's not possible"
            } else {
                textViewResult.text = "Save $${monthlySavings.format(2)} monthly to reach your target by the due date."
            }
        }
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
