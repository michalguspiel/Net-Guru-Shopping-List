package com.erdees.netgurushoppinglist.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdees.netgurushoppinglist.Utils.makeGone
import com.erdees.netgurushoppinglist.Utils.makeSnackbar
import com.erdees.netgurushoppinglist.databinding.AddItemAlertDialogBinding
import com.erdees.netgurushoppinglist.databinding.ShoppingListsFragmentBinding
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.view.recyclerAdapters.ShoppingListsRecyclerAdapter
import com.erdees.netgurushoppinglist.viewModel.ActiveShoppingListsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ActiveShoppingListsFragment : Fragment() {

    private var _binding: ShoppingListsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ActiveShoppingListsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShoppingListsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel =
            ViewModelProvider(requireActivity())[ActiveShoppingListsFragmentViewModel::class.java]


        binding.fab.setOnClickListener { buildAlertDialogToAddShoppingList() }

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

        viewModel.getActiveShoppingLists.observe(viewLifecycleOwner, {
            binding.shoppingListsRV.adapter = ShoppingListsRecyclerAdapter(it, requireContext(),parentFragmentManager,viewModel)
        })

        return view
    }

    private fun buildAlertDialogToAddShoppingList() {
        val thisBinding : AddItemAlertDialogBinding = AddItemAlertDialogBinding.inflate(LayoutInflater.from(requireContext()))
        thisBinding.itemQuantityLayout.makeGone()
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(thisBinding.root)
            .setMessage("Add shopping list")
            .setNegativeButton("Back", null)
            .setPositiveButton("Add", null)
            .show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val shoppingListName : String = if(thisBinding.itemName.text.isNullOrBlank()) "Basic shopping list"
            else thisBinding.itemName.text.toString()
            val newShoppingList = ShoppingList(
                0, shoppingListName, true,
                Calendar.getInstance().time
            )
            viewModel.addShoppingList(newShoppingList)
            alertDialog.dismiss()
            binding.root.makeSnackbar("${newShoppingList.name} created!")
        }
    }

    companion object {
        const val TAG = "ActiveShoppingListFragment"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}