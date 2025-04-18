package com.manuni.apiwithretrofitcrud.kotlin.oop

class MyCar() {

    val myData = "Hello"
    private fun printData(data: String){
        println("My name is Mahamud Islam and my favorite color is: $data")
    }

   inner class Engine{
        fun printDetails(){
            println("The data is: $myData")
        }
    }

    //nested class
    class Wheel{
        //as it is a nested class so it will not able to access the myData value
        fun printDetailsOfWheel(){
            println("The output is here for the Wheel")
        }
    }

}