package com.example.remindertestapp.ui.Schedule.MeTime

import com.example.remindertestapp.databinding.BottomSheetBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.second.generic.GeneralBottomSheetDialog

class MeNameFragment : BaseFragment(){














    fun bottomSheet(){
        object : GeneralBottomSheetDialog<BottomSheetBinding>(mainActivity){
            override fun getViewBinding() = BottomSheetBinding.inflate(layoutInflater)

            override fun onLayoutCreated(view: GeneralBottomSheetDialog<BottomSheetBinding>) {
                binding.btnAccept.setOnClickListener{
                }
                binding.btnReject.setOnClickListener{
                }
                binding.btnReSchedule.setOnClickListener{
                }
            }
        }.dismissible().show()
    }
}