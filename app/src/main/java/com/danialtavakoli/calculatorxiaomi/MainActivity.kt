package com.danialtavakoli.calculatorxiaomi

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.danialtavakoli.calculatorxiaomi.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onNumberClicked()
        onOperatorClicked()
    }

    private fun onOperatorClicked() {
        with(binding) {
            btnPlus.setOnClickListener {
                if (binding.txtExpression.text.isNotEmpty()) {
                    val myChar = txtExpression.text.last()
                    if (myChar != '+' && myChar != '-' && myChar != '*' && myChar != '/') appendText(
                        "+"
                    )
                }
            }
            btnMultiply.setOnClickListener {
                if (binding.txtExpression.text.isNotEmpty()) {
                    val myChar = txtExpression.text.last()
                    if (myChar != '+' && myChar != '-' && myChar != '*' && myChar != '/') appendText(
                        "*"
                    )
                }
            }
            btnMinus.setOnClickListener {
                if (txtExpression.text.isNotEmpty()) {
                    val myChar = txtExpression.text.last()
                    if (myChar != '+' && myChar != '-' && myChar != '*' && myChar != '/') appendText(
                        "-"
                    )
                } else appendText("-")
            }
            btnDivide.setOnClickListener {
                if (binding.txtExpression.text.isNotEmpty()) {
                    val myChar = txtExpression.text.last()
                    if (myChar != '+' && myChar != '-' && myChar != '*' && myChar != '/') appendText(
                        "/"
                    )
                }
            }
            btnRemove.setOnClickListener {
                val oldText = txtExpression.text.toString()
                if (oldText.isNotEmpty()) txtExpression.text =
                    oldText.substring(0, oldText.length - 1)
            }
            btnAC.setOnClickListener {
                txtExpression.text = ""
                txtAnswer.text = ""
            }
            btnOpenParenthesis.setOnClickListener { appendText("(") }
            btnCloseParenthesis.setOnClickListener { appendText(")") }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onNumberClicked() {
        with(binding) {
            btn1.setOnClickListener { appendText("1") }
            btn2.setOnClickListener { appendText("2") }
            btn3.setOnClickListener { appendText("3") }
            btn4.setOnClickListener { appendText("4") }
            btn5.setOnClickListener { appendText("5") }
            btn6.setOnClickListener { appendText("6") }
            btn7.setOnClickListener { appendText("7") }
            btn8.setOnClickListener { appendText("8") }
            btn9.setOnClickListener { appendText("9") }
            btn0.setOnClickListener { if (txtExpression.text.isNotEmpty()) appendText("0") }
            btnDot.setOnClickListener {
                if (txtExpression.text.isEmpty() || txtAnswer.text.isNotEmpty()) appendText("0.")
                else if (txtExpression.text.last() != '.') {
                    var countOperators = 0
                    var countDots = 0
                    txtExpression.text.forEach {
                        if (it == '+' || it == '-' || it == '*' || it == '/') countOperators++
                        if (it == '.') countDots++
                    }
                    if (countDots <= countOperators) appendText(".")
                }
            }
            btnEqual.setOnClickListener {
                try {
                    val expression = ExpressionBuilder(txtExpression.text.toString()).build()
                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    if (result == longResult.toDouble()) txtAnswer.text = longResult.toString()
                    else txtAnswer.text = result.toString()
                } catch (e: Exception) {
                    txtExpression.text = ""
                    txtAnswer.text = "Error"
                }
            }
        }
    }

    private fun appendText(text: String) {
        with(binding) {
            if (txtAnswer.text.isNotEmpty()) txtExpression.text = ""
            txtAnswer.text = ""
            txtExpression.append(text)
            val viewTree: ViewTreeObserver = horizontalScrollViewTxtExpression.viewTreeObserver
            viewTree.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    horizontalScrollViewTxtExpression.viewTreeObserver.removeOnGlobalLayoutListener(
                        this
                    )
                    horizontalScrollViewTxtExpression.scrollTo(txtExpression.width, 0)
                }
            })
        }
    }
}