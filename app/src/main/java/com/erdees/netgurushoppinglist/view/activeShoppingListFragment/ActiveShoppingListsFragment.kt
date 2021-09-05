package com.erdees.netgurushoppinglist.view.activeShoppingListFragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.Utils.makeGone
import com.erdees.netgurushoppinglist.Utils.makeSnackBar
import com.erdees.netgurushoppinglist.databinding.AddItemAlertDialogBinding
import com.erdees.netgurushoppinglist.databinding.ShoppingListsFragmentBinding
import com.erdees.netgurushoppinglist.view.recyclerAdapters.ShoppingListsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActiveShoppingListsFragment : Fragment() {

    private var _binding: ShoppingListsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ActiveShoppingListsFragmentViewModel
    private lateinit var rvAdapter :ShoppingListsRecyclerAdapter

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
        rvAdapter = ShoppingListsRecyclerAdapter(requireActivity(), parentFragmentManager, viewModel)
        with(binding.shoppingListsRV) {
            adapter = rvAdapter
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
            rvAdapter.updateShoppingList(it)
        })

        return view
    }

    private fun buildAlertDialogToAddShoppingList() {
        val thisBinding: AddItemAlertDialogBinding =
            AddItemAlertDialogBinding.inflate(LayoutInflater.from(requireContext()))
        thisBinding.itemQuantityLayout.makeGone()
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(thisBinding.root)
            .setMessage(getString(R.string.add_shopping_list))
            .setNegativeButton(R.string.back, null)
            .setPositiveButton(R.string.add, null)
            .show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val itemName = thisBinding.itemName.text.toString()
            viewModel.addShoppingList(itemName)
            alertDialog.dismiss()
            binding.root.makeSnackBar("$itemName created!")
        }
    }

    companion object {
        const val TAG = "ActiveShopListFrag"
    }


    override fun onDestroy() {
        Log.i(TAG,"on destroy")
        super.onDestroy()
        _binding = null
    }
}