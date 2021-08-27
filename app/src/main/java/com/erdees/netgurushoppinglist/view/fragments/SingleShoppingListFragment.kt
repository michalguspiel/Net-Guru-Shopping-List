package com.erdees.netgurushoppinglist.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erdees.netgurushoppinglist.databinding.SingleShoppingListFragmentBinding

class SingleShoppingListFragment: Fragment() {

    private var _binding : SingleShoppingListFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SingleShoppingListFragmentBinding.inflate(inflater,container,false)
        val view = binding.root


        return view
    }


    companion object {
        const val TAG = "SingleShoppingListFragment"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}