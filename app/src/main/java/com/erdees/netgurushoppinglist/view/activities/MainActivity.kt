package com.erdees.netgurushoppinglist.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.Utils
import com.erdees.netgurushoppinglist.databinding.ActivityMainBinding
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.view.fragments.ArchivedShoppingListsFragment
import com.erdees.netgurushoppinglist.view.fragments.ActiveShoppingListsFragment
import com.erdees.netgurushoppinglist.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    private val activeShoppingListsFragment = ActiveShoppingListsFragment()
    private val archivedShoppingListFragment = ArchivedShoppingListsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        viewBinding.fab.setOnClickListener {
            val editText = EditText(this)
            val alertDialog = AlertDialog.Builder(this)
                .setView(editText)
                .setMessage("Add Grocery list")
                .setNegativeButton("Back", null)
                .setPositiveButton("Add", null)
                .show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val newShoppingList = ShoppingList(
                    0, editText.text.toString(), true,
                    java.util.Calendar.getInstance().time
                )
                viewModel.addShoppingList(newShoppingList)
            }
        }

        viewBinding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.activeShoppingList -> {
                    Log.i(TAG, "active shopping list clicked")
                    Utils.openFragment(
                        activeShoppingListsFragment,
                        ActiveShoppingListsFragment.TAG,
                        supportFragmentManager
                    )
                }
                R.id.archivedShoppingList -> {
                    Log.i(TAG, "archive shopping list clicked")

                    Utils.openFragment(
                        archivedShoppingListFragment,
                        ArchivedShoppingListsFragment.TAG,
                        supportFragmentManager
                    )
                }

            }
            true
        }


        Utils.openFragment(
            activeShoppingListsFragment,
            ActiveShoppingListsFragment.TAG,
            supportFragmentManager
        )

    }

    companion object {
        const val TAG = "MainActivity"
    }
}