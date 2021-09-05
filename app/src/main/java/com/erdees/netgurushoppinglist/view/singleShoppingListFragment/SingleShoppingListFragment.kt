package com.erdees.netgurushoppinglist.view.singleShoppingListFragment

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
import com.erdees.netgurushoppinglist.Constants
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.Utils.makeGone
import com.erdees.netgurushoppinglist.Utils.makeSnackBar
import com.erdees.netgurushoppinglist.Utils.makeVisible
import com.erdees.netgurushoppinglist.databinding.AddItemAlertDialogBinding
import com.erdees.netgurushoppinglist.databinding.SingleShoppingListFragmentBinding
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.view.mainActivity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleShoppingListFragment : Fragment() {

    private var _binding: SingleShoppingListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: SingleShoppingListRecyclerAdapter
    private lateinit var shoppingList : ShoppingList

    private lateinit var viewModel: SingleShoppingListFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SingleShoppingListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SingleShoppingListFragmentViewModel::class.java]

        (activity as MainActivity).supportActionBar?.title = getString(R.string.shopping_lists_details)
        binding.fab.setOnClickListener { buildAlertDialogToAddGroceryItemToShoppingList() }

        rvAdapter = SingleShoppingListRecyclerAdapter(requireActivity(), viewModel)
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

        shoppingList = arguments?.getParcelable(Constants.SHOPPING_LIST_KEY)!!
        rvAdapter.updateShoppingList(shoppingList)
        if (!shoppingList.isActive) binding.fab.makeGone() else binding.fab.makeVisible()
        viewModel.getGroceriesToPresent(shoppingList.id)
            .observe(viewLifecycleOwner, { listOfGroceryItems ->
                rvAdapter.updateGroceryList(listOfGroceryItems.toMutableList())
            })

        return view
    }

    private fun buildAlertDialogToAddGroceryItemToShoppingList() {
        val thisBinding: AddItemAlertDialogBinding =
            AddItemAlertDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.add_grocery_item_to_list))
            .setView(thisBinding.root)
            .setNegativeButton(getString(R.string.back), null)
            .setPositiveButton(getString(R.string.add), null)
            .show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (thisBinding.itemName.text.isNullOrBlank()) Toast.makeText(
                requireContext(), getString(R.string.cant_make_nameless_item),
                Toast.LENGTH_SHORT
            ).show()
            else {
                val itemName = thisBinding.itemName.text.toString()
                viewModel.addGroceryItem(
                    itemName,
                    thisBinding.itemQuantity.text.toString(),
                    shoppingList.id
                )
                thisBinding.itemName.text?.clear()
                thisBinding.itemQuantity.text?.clear()
                binding.root.makeSnackBar("$itemName added!")
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