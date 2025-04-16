package com.manuni.apiwithretrofitcrud.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manuni.apiwithretrofitcrud.databinding.FragmentMainBinding
import com.manuni.apiwithretrofitcrud.databinding.FragmentSecondBinding

class SecondFragment:Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private var myData:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myData = arguments?.getString("MY_NAME","")?:""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSecondBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //all click listener and event here

        binding.fragmentTxt.text = myData


    }
}
