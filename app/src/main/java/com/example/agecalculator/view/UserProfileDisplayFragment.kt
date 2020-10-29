package com.example.agecalculator.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.agecalculator.R
import com.example.agecalculator.viewmodel.MainViewModel

class UserProfileDisplayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile_display, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val tvFirstName = view.findViewById<TextView>(R.id.tvFirstName)
        val tvLastName = view.findViewById<TextView>(R.id.tvLastName)
        val tvPersonAge = view.findViewById<TextView>(R.id.tvPersonAge)

        val mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.personInfoLiveData.observe(requireActivity(), Observer {

            Log.v("Profile", "${it.firstName} - ${it.lastName} - ${it.personAge}")
            tvFirstName.text = "First Name : ${it.firstName}"
            tvLastName.text = "Last Name : ${it.lastName}"
            tvPersonAge.text = "Age : ${it.personAge}"
        })

        val btnBack = view.findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}