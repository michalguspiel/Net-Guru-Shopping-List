package com.erdees.netgurushoppinglist.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdees.netgurushoppinglist.databinding.SingleShoppingListFragmentBinding
import com.erdees.netgurushoppinglist.view.recyclerAdapters.SingleShoppingListRecyclerAdapter
import com.erdees.netgurushoppinglist.viewModel.SingleShoppingListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleShoppingListFragment : Fragment() {

    private var _binding : SingleShoppingListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : SingleShoppingListFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SingleShoppingListFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SingleShoppingListFragmentViewModel::class.java]

        with(binding.shoppingListsRV) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        viewModel.shoppingListToPresent.observe(viewLifecycleOwner,{ shoppingList ->
            if(shoppingList != null) {
                viewModel.groceriesToPresent(shoppingList.id).observe(viewLifecycleOwner, { listOfGroceryItems ->
                    binding.shoppingListsRV.adapter = SingleShoppingListRecyclerAdapter(listOfGroceryItems,shoppingList,requireContext())

                })
            }
        })


        return view
    }


    companion object {
        const val TAG = "SingleShopListFrag"
    }

    override fun onDestroy() {
        Log.i(TAG, "on Destroy")
        viewModel.clearShoppingListToPresent()
        super.onDestroy()
        _binding = null
    }
}