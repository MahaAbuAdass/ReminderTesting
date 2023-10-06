package com.example.remindertestapp.ui.homeContact.contacts

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.remindertestapp.databinding.New1Binding
import com.example.remindertestapp.databinding.ScheduleFragmentBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment

class New  : BaseFragment() {

    private var binding: New1Binding? = null
    private var spinner: Spinner? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = New1Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the spinner after binding is set
        spinner = binding?.spinner

        val options = listOf("2", "4", "6", "8", "10")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = options[position]

                // Handle item selection here

                spinner?.visibility = View.GONE // Hide the Spinner after selection
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }
}
