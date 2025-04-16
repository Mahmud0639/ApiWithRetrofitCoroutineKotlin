package com.manuni.apiwithretrofitcrud

fun main(){
    for (i in 0..5 step 1){
        println("$i hello")
    }

    println("---------")

    for (i in 10 downTo 0 step 2){
        println("$i Mahamud")
    }

    println("----------")

    val nameList:Array<String> = arrayOf("Mahmud","Mamun","Sourav")
    for (n in nameList){
        println(n)
    }

    println("---------------")

    for (n in nameList.indices){
        println("$n Index no of: ${nameList[n]}")
    }
}