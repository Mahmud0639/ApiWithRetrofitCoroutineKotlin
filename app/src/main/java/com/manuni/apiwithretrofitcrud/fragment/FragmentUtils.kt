package com.manuni.apiwithretrofitcrud.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.manuni.apiwithretrofitcrud.R

fun addFragment(activity: FragmentActivity?, bundle: Bundle?, fragment: Fragment) {
    bundle?.let {
        fragment.arguments = it
    }

    //getting the class name
    val className = fragment.javaClass.simpleName

    //as we need to access the supportFragmentManager so we need to pass FragmentActivity not context
    //because with the context we won't access the supportFragmentManager
    //because the supportFragmentManager is available in the FragmentActivity not in the Context
    activity?.supportFragmentManager?.beginTransaction()
    ?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)?.
        //addToBackStack("MainFragment")?.
    addToBackStack(className)?.add(R.id.fragmentContainer, fragment, className)?.commit()
}

fun removeFragment(activity: FragmentActivity?, fragment: Fragment) {
    activity?.supportFragmentManager?.apply {
        beginTransaction().remove(fragment).commit()
        popBackStack()
    }
}