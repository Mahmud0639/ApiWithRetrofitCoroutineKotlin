package com.manuni.apiwithretrofitcrud

import com.manuni.apiwithretrofitcrud.kotlin.oop.MyCar

fun main(){
    val myCar = MyCar()
  val engine =  myCar.Engine()
    println(engine.printDetails())

    //accessing nested class
    val wheel = MyCar.Wheel()
    println(wheel.printDetailsOfWheel())
}

/*
class MyCar(val color:String, val brandName: String, val speed: Double){
    constructor(color: String,brandName: String):this(color,brandName,0.0)
    constructor(color: String):this(color,"",0.0)
}*/
