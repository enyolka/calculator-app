// + 1. TableLayout, przyciski na całąprzestrzeń ekranu, symbole w strings.xml, wymienione przyciski
// + 2. Interfejs, własna implementacja procesu,
// + 3. Obsługa kropki (dotButtonPressed()), wielokrotne użycie przycisku z symbolem operacji, pozostałe
// + 4. Aktualizacja wyświetlacza za pomocą settera
// + 5. Kalkulator działa po zmianie symbolu
// + 6. Zbyt długie liczby -> cyfry zastąpione kropkami
// - 7. Pominięte na rzecz zadania 9.
// + 8. Pierwiastki i procenty
// + 9. Poprawna obsługa kolejności działań


package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    internal lateinit var clearButton: Button
    internal lateinit var resultButton: Button
    internal lateinit var dotButton: Button
    internal lateinit var digits: Array<Button>
    internal lateinit var operations: Array<Button>
    internal lateinit var display: TextView

    private var lastNumeric = true
    private var decimalUsed = false
    private var lastOperations = false

    var chars = ""
        set(value){
            if (value == "") {
                setDisplay("0")
            } else {
                setDisplay(value.toString())
            }
            field = value
        }

    internal val calculatorEngine: CalculatorBrainInterface = CalculatorBrain();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clearButton = findViewById(R.id.buttonClear)
        dotButton = findViewById(R.id.buttonDot)
        resultButton = findViewById(R.id.buttonResult)
        display = findViewById(R.id.display)
        val digitsLabels = arrayOf(R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9)
        val operationsLabels = arrayOf(R.id.buttonAdd, R.id.buttonSub, R.id.buttonMul, R.id.buttonDiv, R.id.buttonPercent, R.id.buttonSqrt)
        digits = (digitsLabels.map{id -> findViewById(id) as Button}).toTypedArray()
        operations = (operationsLabels.map{id -> findViewById(id) as Button}).toTypedArray()

        clearButton.setOnClickListener { clear() }
        resultButton.setOnClickListener { evaluateFormula() }
        dotButton.setOnClickListener { dotButtonPressed() }
        digits.forEach { btn -> btn.setOnClickListener { i -> digitPressed(i as Button)} }
        operations.forEach { btn -> btn.setOnClickListener { i -> operationPressed(i as Button)} }
    }

    private fun setDisplay(value: String) {
        display.text = value
    }

    private fun clear() {
        calculatorEngine.clear()
        chars = ""
        setDisplay("0")
    }

    private fun evaluateFormula() {
        var result = calculatorEngine.evaluateFormula(chars)
        chars = result.toString()
    }

    private fun dotButtonPressed() {
        if(chars.isNotEmpty()){
            println(chars.last())
        }
        if (!decimalUsed){
            decimalUsed = true
            lastNumeric = true
            if (chars == "" || chars.last() == '+' || chars.last() == '-' || chars.last() == '*' || chars.last() == '/') {
                chars += "0."
            } else {
                chars += "."
            }
        }
    }

    private fun digitPressed(btn: Button) {
        lastOperations = false
        lastNumeric = true
        chars += btn.text.last().toString()
    }

    private fun operationPressed(operationBtn: Button) {
        if ( display.text.isEmpty()){
            throw Error()
        }

        decimalUsed = false
        var operation = ""

        when (operationBtn.id) {
            R.id.buttonAdd -> {
                operation = "+"
            }
            R.id.buttonSub -> {
                operation = "-"
            }
            R.id.buttonMul -> {
                operation = "*"
            }
            R.id.buttonDiv -> {
                operation = "/"
            }
            R.id.buttonPercent -> {
                operation = "%"
                decimalUsed = true
            }
            R.id.buttonSqrt -> {
                operation = "√"
            }
        }
        if(chars == "" && operation != "√"){
            chars = ""
        } else if (!lastOperations || (chars.last() == '%' && operation != "%" && operation != "√" ) ||
                (operation == "√" && chars.last() != '√' && chars.last() != '%') || (chars == "" && operation!="√")) {
            chars += operation
        } else {
            chars = chars.dropLast(1) + operation
        }
        lastOperations = true
        lastNumeric = false
    }
}