package com.erdees.netgurushoppinglist.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erdees.netgurushoppinglist.databinding.ShoppingListsFragmentBinding



class ShoppingListsFragment : Fragment() {

    private var _binding : ShoppingListsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShoppingListsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    companion object {
        const val TAG = "ShoppingListFragment"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}