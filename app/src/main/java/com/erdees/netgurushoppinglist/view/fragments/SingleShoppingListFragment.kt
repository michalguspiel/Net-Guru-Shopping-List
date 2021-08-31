package com.erdees.netgurushoppinglist.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdees.netgurushoppinglist.Utils.makeGone
import com.erdees.netgurushoppinglist.Utils.makeSnackbar
import com.erdees.netgurushoppinglist.Utils.makeVisible
import com.erdees.netgurushoppinglist.databinding.AddItemAlertDialogBinding
import com.erdees.netgurushoppinglist.databinding.SingleShoppingListFragmentBinding
import com.erdees.netgurushoppinglist.model.models.GroceryItem
import com.erdees.netgurushoppinglist.view.recyclerAdapters.SingleShoppingListRecyclerAdapter
import com.erdees.netgurushoppinglist.viewModel.SingleShoppingListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleShoppingListFragment : Fragment() {

    private var _binding : SingleShoppingListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter : SingleShoppingListRecyclerAdapter

    private lateinit var viewModel : SingleShoppingListFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SingleShoppingListFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SingleShoppingListFragmentViewModel::class.java]

        binding.fab.setOnClickListener { buildAlertDialogToAddGroceryItemToShoppingList() }

        rvAdapter = SingleShoppingListRecyclerAdapter(requireContext(),viewModel)
        with(binding.shoppingListsRV) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rvAdapter
            this.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        viewModel.shoppingListToPresent.observe(viewLifecycleOwner,{ shoppingList ->
            if (shoppingList != null) {
                if(!shoppingList.isActive) binding.fab.makeGone() else binding.fab.makeVisible()
                    rvAdapter.updateShoppingList(shoppingList)
                    viewModel.getGroceriesToPresent(shoppingList.id).observe(viewLifecycleOwner, { listOfGroceryItems ->
                        rvAdapter.updateGroceryList(listOfGroceryItems.toMutableList())
                    })
            }
        })

        return view
    }


    private fun buildAlertDialogToAddGroceryItemToShoppingList() {
        val thisBinding : AddItemAlertDialogBinding = AddItemAlertDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage("Add grocery item to the list")
            .setView(thisBinding.root)
            .setNegativeButton("Back", null)
            .setPositiveButton("Add", null)
            .show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if(thisBinding.itemName.text.isNullOrBlank()) Toast.makeText(requireContext(),"Can't add nameless item",
                Toast.LENGTH_SHORT).show()
            else {
                val groceryItem = GroceryItem(
                    0,
                    thisBinding.itemName.text.toString(),
                    thisBinding.itemQuantity.text.toString(),
                    viewModel.shoppingListToPresent.value!!.id,
                    false
                )
                viewModel.addGroceryItem(groceryItem)
                thisBinding.itemName.text?.clear()
                thisBinding.itemQuantity.text?.clear()
                binding.root.makeSnackbar("${groceryItem.name} added!")
            }
        }
    }

    companion object {
        const val TAG = "SingleShopListFrag"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}