package com.erdees.netgurushoppinglist.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdees.netgurushoppinglist.databinding.ShoppingListsFragmentBinding
import com.erdees.netgurushoppinglist.view.recyclerAdapters.ShoppingListsRecyclerAdapter
import com.erdees.netgurushoppinglist.viewModel.ActiveShoppingListsFragmentViewModel


class ActiveShoppingListsFragment : Fragment() {

    private var _binding : ShoppingListsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : ActiveShoppingListsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShoppingListsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity())[ActiveShoppingListsFragmentViewModel::class.java]
        binding.shoppingListsRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        viewModel.getActiveShoppingLists.observe(viewLifecycleOwner,{

            binding.shoppingListsRV.adapter = ShoppingListsRecyclerAdapter(it,requireActivity())

        })

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