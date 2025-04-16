package com.manuni.apiwithretrofitcrud.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manuni.apiwithretrofitcrud.MyConstants
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.FragmentMainBinding

class MainFragment:Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var myNameFromActivity:String = ""

    private val secondFragment:SecondFragment = SecondFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myNameFromActivity = arguments?.getString(MyConstants.MY_NAME,"")?:""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //all click listener and event here

        binding.fragmentTxt.text = myNameFromActivity

        binding.goNextBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putString("MY_NAME","$myNameFromActivity ${binding.etData.text}")
            }

            //here this will not work, because this is a fragment and this fragment is placed on an activity
            //so we need to pass the activity not this
            addFragment(activity,bundle,secondFragment)
            //secondFragment.arguments = bundle

            //activity?.supportFragmentManager?.beginTransaction()?.addToBackStack("SecondFragment")?.add(R.id.fragmentContainer,secondFragment,"SecondFragment")?.commit()
        }
    }
}
