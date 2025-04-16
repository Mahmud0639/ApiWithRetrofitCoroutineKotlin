package com.manuni.apiwithretrofitcrud.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityFragmentBinding

class FragmentActivity:AppCompatActivity() {
    private lateinit var binding: ActivityFragmentBinding
    private val mainFragment:MainFragment = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addFragmentBtn.setOnClickListener {

            //data passing using bundle
            val bundle = Bundle().apply {
                putString("MY_NAME","Mahamudul Islam")
            }
            mainFragment.arguments = bundle


            //here binding.frameLayoutContainer will not work,,,because we inflate only constraint layout
            /**
             * using frameLayout as fragment container
             */
            //supportFragmentManager.beginTransaction().addToBackStack("MainFragment").add(R.id.frameLayoutContainer,MainFragment(),"MainFragment").commit()
            /**
             * using fragmentContainerView as fragment container
             */
            supportFragmentManager.beginTransaction().
            setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit).
            addToBackStack("MainFragment").
            add(R.id.fragmentContainer,mainFragment,"MainFragment").
            commit()


        }

        binding.removeFragmentBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().remove(mainFragment).commit()
            //since we need to remove the backstack so we can write the above code like this way:
            supportFragmentManager.apply {
                beginTransaction().remove(mainFragment).commit()
                popBackStack()
            }
        }
    }
}