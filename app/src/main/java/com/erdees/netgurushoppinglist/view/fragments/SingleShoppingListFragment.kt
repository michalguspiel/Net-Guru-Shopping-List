package com.erdees.netgurushoppinglist.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.erdees.netgurushoppinglist.databinding.SingleShoppingListFragmentBinding
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


        viewModel.shoppingListToPresent.observe(viewLifecycleOwner,{
            if(it != null) {

            }
        })


        return view
    }


    companion object {
        const val TAG = "SingleShopListFrag"
    }

    override fun onDestroy() {
        Log.i(TAG, "on Destroy")
        super.onDestroy()
        _binding = null
    }
}