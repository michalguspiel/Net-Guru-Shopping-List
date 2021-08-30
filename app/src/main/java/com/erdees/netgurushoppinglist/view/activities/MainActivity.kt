package com.erdees.netgurushoppinglist.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.Utils
import com.erdees.netgurushoppinglist.Utils.makeGone
import com.erdees.netgurushoppinglist.Utils.makeSnackbar
import com.erdees.netgurushoppinglist.Utils.makeVisible
import com.erdees.netgurushoppinglist.databinding.ActivityMainBinding
import com.erdees.netgurushoppinglist.databinding.AddItemAlertDialogBinding
import com.erdees.netgurushoppinglist.model.models.GroceryItem
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.view.fragments.ArchivedShoppingListsFragment
import com.erdees.netgurushoppinglist.view.fragments.ActiveShoppingListsFragment
import com.erdees.netgurushoppinglist.viewModel.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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

        viewModel.presentedShoppingList.observe(this, {
            if (it != null) {
                setUiForShoppingListDetails()
            }
        })

        viewBinding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.activeShoppingList -> {
                    openActiveShoppingListsFragment()
                }
                R.id.archivedShoppingList -> {
                   openArchivedShoppingListsFragment()
                }
            }
            true
        }
        openActiveShoppingListsFragment()
    }

    private fun openActiveShoppingListsFragment(){
        supportActionBar?.title = "Shopping lists"
        Utils.openFragment(
            activeShoppingListsFragment,
            ActiveShoppingListsFragment.TAG,
            supportFragmentManager
        )
    }

    private fun openArchivedShoppingListsFragment(){
        Utils.openFragment(
            archivedShoppingListFragment,
            ArchivedShoppingListsFragment.TAG,
            supportFragmentManager
        )
        supportActionBar?.title = "Archived shopping lists"
    }

    private fun setUiForShoppingListDetails(){
        viewBinding.bottomNavigation.uncheckAllItems()
        supportActionBar?.title = "Shopping list details"
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) finish()
        else super.onBackPressed()

        when (supportFragmentManager.fragments.last().tag) {
            ActiveShoppingListsFragment.TAG -> {
                viewBinding.bottomNavigation.selectedItemId = R.id.activeShoppingList
                supportActionBar?.title = "Shopping lists"
            }
            ArchivedShoppingListsFragment.TAG -> {
                viewBinding.bottomNavigation.selectedItemId = R.id.archivedShoppingList
                supportActionBar?.title = "Archived shopping lists"
            }
            else -> {
                setUiForShoppingListDetails()
            }
        }
    }

    private fun BottomNavigationView.uncheckAllItems() {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}