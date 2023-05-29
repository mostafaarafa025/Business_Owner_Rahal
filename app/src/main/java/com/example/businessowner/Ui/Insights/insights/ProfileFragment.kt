package com.example.businessowner.Ui.Insights.insights

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.businessowner.R
import com.example.businessowner.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeNavigationDrawer.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navigationView)

        }
    }
     fun onCreateOptionsMenu2(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.setting_business_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_too_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
               binding.drawerLayout.openDrawer(binding.navigationView)
                return true
            }
            R.id.editProfileFragment -> {
                view?.let { Navigation.findNavController(it).navigate(R.id.action_profileFragment2_to_editProfileFragment) }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}