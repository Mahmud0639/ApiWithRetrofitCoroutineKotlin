package com.manuni.apiwithretrofitcrud.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.FragmentMainBinding

class MainFragment:Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //all click listener and event here

        binding.goNextBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack("SecondFragment")?.add(R.id.fragmentContainer,SecondFragment(),"SecondFragment")?.commit()
        }
    }
}
