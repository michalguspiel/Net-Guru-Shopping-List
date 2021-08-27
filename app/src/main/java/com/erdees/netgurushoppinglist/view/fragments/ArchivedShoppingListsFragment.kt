package com.erdees.netgurushoppinglist.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erdees.netgurushoppinglist.databinding.ArchivedShoppingListsFragmentBinding

class ArchivedShoppingListsFragment : Fragment() {

    private var _binding : ArchivedShoppingListsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArchivedShoppingListsFragmentBinding.inflate(inflater,container,false)
        val view = binding.root


        return view
    }


    companion object {
        const val TAG = "ArchivedShoppingListFragment"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}