package com.example.remindertestapp.ui.homeContact

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ContactViewPagesAdapter (fragmentActivity: FragmentActivity, private vararg val fragments: Fragment) :
    FragmentStateAdapter(fragmentActivity) {


    //return how many buttons in vew pages based on how many add in dashboard fragment
    override fun getItemCount(): Int {
        return fragments.size
    }

    // return specific fragment based on position
    override fun createFragment(position: Int): Fragment {
        return fragments[position]

    }
}