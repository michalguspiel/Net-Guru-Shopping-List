package com.erdees.netgurushoppinglist.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdees.netgurushoppinglist.databinding.ArchivedShoppingListsFragmentBinding
import com.erdees.netgurushoppinglist.view.recyclerAdapters.ShoppingListsRecyclerAdapter
import com.erdees.netgurushoppinglist.viewModel.ArchivedShoppingListsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchivedShoppingListsFragment : Fragment() {

    private var _binding: ArchivedShoppingListsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ArchivedShoppingListsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArchivedShoppingListsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[ArchivedShoppingListsFragmentViewModel::class.java]
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
        viewModel.getArchivedShoppingLists.observe(viewLifecycleOwner, {
            binding.shoppingListsRV.adapter = ShoppingListsRecyclerAdapter(it, requireContext(),parentFragmentManager,viewModel)
        })
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