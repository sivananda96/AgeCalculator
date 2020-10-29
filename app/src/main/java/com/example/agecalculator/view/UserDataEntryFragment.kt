package com.example.agecalculator.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.agecalculator.R
import com.example.agecalculator.model.PersonInfo
import com.example.agecalculator.viewmodel.MainViewModel
import java.util.*

class UserDataEntryFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etDateOfBirth: EditText

    var dayOfBirth = 0
    var monthOfBirth = 0
    var yearOfBirth = 0

    lateinit var personAge : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_data_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etFirstName = view.findViewById<EditText>(R.id.etFirstName)
        etLastName = view.findViewById<EditText>(R.id.etLastName)
        etDateOfBirth = view.findViewById<EditText>(R.id.etDateOfBirth)

        val mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        val btnRegister = view.findViewById<Button>(R.id.btnRegister);
        btnRegister.setOnClickListener {
            if (etFirstName.text.isNotBlank() && etLastName.text.isNotBlank() && etDateOfBirth.text.isNotBlank()) {
                val personInfo = PersonInfo(
                    etFirstName.text.toString(),
                    etLastName.text.toString(),
                    personAge
                )
                mainViewModel.init(personInfo)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, UserProfileDisplayFragment())
                    .addToBackStack(null).commit()
            } else {
                Toast.makeText(activity, "All Fields are mandatory", Toast.LENGTH_LONG).show()
            }
        }

        val btnClear = view.findViewById<Button>(R.id.btnClear)
        btnClear.setOnClickListener {
            etFirstName.setText("")
            etLastName.setText("")
            etDateOfBirth.setText("")
        }

        val btnSetDateOfBirth = view.findViewById<Button>(R.id.btnSetDateOfBirth)
        btnSetDateOfBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            var day = calendar.get(Calendar.DAY_OF_MONTH)
            var month = calendar.get(Calendar.MONTH)
            var year = calendar.get(Calendar.YEAR)

            val datePickerDialog = DatePickerDialog(requireActivity(), R.style.MySpinnerDatePickerStyle, this, year, month, day)
            datePickerDialog.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dayOfBirth = dayOfMonth
        monthOfBirth = month + 1
        yearOfBirth = year

        etDateOfBirth.setText("$dayOfBirth/$monthOfBirth/$yearOfBirth")

        val now = Calendar.getInstance()

        val birthday = Calendar.getInstance()
        birthday.set(Calendar.YEAR, year)
        birthday.set(Calendar.MONTH, month)
        birthday.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val diff = now.timeInMillis - birthday.timeInMillis

        if (diff < 0) {
            Toast.makeText(activity, "Selected date is in future", Toast.LENGTH_LONG).show()
            etDateOfBirth.setText("")
        } else {
            var years = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)

            val currMonth = now.get(Calendar.MONTH) + 1
            val birthMonth = birthday.get(Calendar.MONTH) + 1

            var months = currMonth - birthMonth
            if (months < 0) {
                years--
                months = 12 - birthMonth + currMonth
                if (now.get(Calendar.DATE) < birthday.get((Calendar.DATE))) {
                    months--
                }
            } else if (months == 0 && now.get(Calendar.DATE) < birthday.get(Calendar.DATE)) {
                years--
                months = 11
            }

            var days = 0
            if (now.get(Calendar.DATE) > birthday.get(Calendar.DATE)) {
                days = now.get(Calendar.DATE) - birthday.get(Calendar.DATE)
            } else if(now.get(Calendar.DATE) < birthday.get(Calendar.DATE)) {
                months--
                val today = now.get(Calendar.DAY_OF_MONTH)
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH) + today
            } else {
                days = 0
                if(months == 12) {
                    years++
                    months = 0
                }
            }

            personAge = "$years years, $months months, $days days"
        }

    }

}