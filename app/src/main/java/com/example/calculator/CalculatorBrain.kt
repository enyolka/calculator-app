package com.example.calculator

import kotlin.math.sqrt

class CalculatorBrain : CalculatorBrainInterface {
    var chars = ""


    public override fun evaluateFormula(chars: String) : Double {
        var result = 0.0

        var add = isEmpty(chars.split("+").toMutableList(), "0")

        for(i in 0..add.count()-1) {
            var sub = isEmpty(add[i].split("-").toMutableList(), "0")

            for(j in 0..sub.count()-1) {
                var mul = isEmpty(sub[j].split("*").toMutableList(), "1")

                for(k in 0..mul.count()-1) {
                    var div = isEmpty(mul[k].split("/").toMutableList(), "1")

                    for(s in 0..div.count()-1){
                        if(div[s].contains("%")){
                            div[s] = percent(div[s])
                        }
                        if(div[s].contains("√")){
                            div[s] = sqrRoot(div[s])
                        }
                        result = div[s].toDouble()
                    }
                    if(div.count() > 1) {
                        var div_res = div[0].toDouble()
                        div = div.drop(1).toMutableList()
                        while(div.isNotEmpty()){
                            div_res = div_res / div[0].toDouble()
                            div = div.drop(1).toMutableList()
                        }
                        mul[k] = div_res.toString()
                        result = div_res
                    }
                }
                for(s in 0..mul.count()-1){
                    if(mul[s].contains("%")){
                        mul[s] = percent(mul[s])
                    }
                    if(mul[s].contains("√")){
                        mul[s] = sqrRoot(mul[s])
                    }
                    result = mul[s].toDouble()
                    mul[s] = mul[s].replace("[","")
                    mul[s] = mul[s].replace("]","")
                }

                sub[j] = mul.toString()
                if(mul.count() > 1) {
                    var mul_res = mul[0].toDouble()
                    var items = mul.drop(1).toMutableList()
                    while(items.isNotEmpty()){
                        mul_res *= items[0].toDouble()
                        items = items.drop(1).toMutableList()
                    }
                    sub[j] = mul_res.toString()
                    result = mul_res
                }
            }

            for(s in 0..sub.count()-1){
                sub[s] = sub[s].replace("[","")
                sub[s] = sub[s].replace("]","")
            }
            add[i] = sub.toString()

            if(sub.count() > 1) {
                var sub_res = sub[0].toDouble()
                var items = sub.drop(1).toMutableList()
                while(items.isNotEmpty()){
                    sub_res -= items[0].toDouble()
                    items = items.drop(1).toMutableList()
                }
                add[i] = sub_res.toString()
                result = sub_res
            }

        }

        for(s in 0..add.count()-1){
            add[s] = add[s].replace("[","")
            add[s] = add[s].replace("]","")
        }

        if(add.count() > 1) {
            var add_res = add[0].toDouble()
            var items = add.drop(1).toMutableList()
            while(items.isNotEmpty()){
                add_res += items[0].toDouble()
                items = items.drop(1).toMutableList()
            }
            result = add_res
        }

        return result

    }

    public fun evaluate(list: MutableList<String>, item: String): Double {
        var list_res = list[0].toDouble()
        var new_list = list.drop(1).toMutableList()
        while(new_list.isNotEmpty()){
            list_res = list_res / new_list[0].toDouble()
            new_list = new_list.drop(1).toMutableList()
        }
        return list_res
    }

    public override fun percent(item: String): String {
        return (item.replace("%", "").toDouble() * 0.01).toString()
    }

    override fun sqrRoot(item: String): String {
        return sqrt(item.replace("√", "").toDouble()).toString()
    }

    public override fun clear() {
        chars = ""
    }

    public fun isEmpty(list:  MutableList<String>, substring: String): MutableList<String> {
        for(i in 0..list.count()-1){
            if(list[i].isEmpty()) {
                list[i] = substring
            }
        }
        return list
    }

}