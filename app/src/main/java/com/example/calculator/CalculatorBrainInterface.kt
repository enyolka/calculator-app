package com.example.calculator

interface CalculatorBrainInterface {
    public fun evaluateFormula(chars: String): Double
    public fun clear()
    public fun  percent(item: String): String
    public fun sqrRoot(item: String): String
}