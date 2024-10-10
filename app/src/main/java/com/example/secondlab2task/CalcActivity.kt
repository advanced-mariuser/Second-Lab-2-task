package com.example.secondlab2task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import net.objecthunter.exp4j.ExpressionBuilder

class CalcActivity : AppCompatActivity()
{

    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView
    private var expression = ""
    private var expressionText = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)

        expressionTextView = findViewById(R.id.expressionTextView)
        resultTextView = findViewById(R.id.resultTextView)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnDecimal
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                appendToExpression((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            removeLastCharacter()
        }
    }

    private fun appendToExpression(value: String)
    {
        val correctedValue = when (value)
        {
            "×" -> "*"
            "÷" -> "/"
            else -> value
        }
        expression += correctedValue
        expressionText += value
        updateExpression()
    }

    private fun removeLastCharacter()
    {
        if (expression.isNotEmpty())
        {
            expression = expression.substring(0, expression.length - 1)
            expressionText = expressionText.substring(0, expressionText.length - 1)
        }
        updateExpression()
    }

    private fun updateExpression()
    {
        expressionTextView.text = expressionText
        calculateResult()
    }

    private fun calculateResult()
    {
        try
        {
            val expr = ExpressionBuilder(expression).build()
            val result = expr.evaluate()

            expressionTextView.setTextColor(getColor(R.color.black))
            resultTextView.setTextColor(getColor(R.color.black))
            resultTextView.text = result.toString()
        } catch (e: Exception)
        {
            expressionTextView.setTextColor(getColor(R.color.red))
            resultTextView.setTextColor(getColor(R.color.red))
            resultTextView.text = "Ошибка"
        }
    }
}
