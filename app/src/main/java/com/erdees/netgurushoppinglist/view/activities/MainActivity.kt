package com.erdees.netgurushoppinglist.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.Utils
import com.erdees.netgurushoppinglist.Utils.makeGone
import com.erdees.netgurushoppinglist.Utils.makeSnackbar
import com.erdees.netgurushoppinglist.Utils.makeVisible
import com.erdees.netgurushoppinglist.databinding.ActivityMainBinding
import com.erdees.netgurushoppinglist.model.GroceryItem
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.view.fragments.ArchivedShoppingListsFragment
import com.erdees.netgurushoppinglist.view.fragments.ActiveShoppingListsFragment
import com.erdees.netgurushoppinglist.viewModel.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
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
            if (supportFragmentManager.fragments.last().tag!! == ArchivedShoppingListsFragment.TAG ||
                supportFragmentManager.fragments.last().tag!! == ActiveShoppingListsFragment.TAG)
            {buildAlertDialogToAddShoppingList()
            }
            else buildAlertDialogToAddGroceryItemToShoppingList()

        }

        viewModel.presentedShoppingList.observe(this,{
            if(it != null) viewBinding.bottomNavigation.uncheckAllItems()
        })

        viewBinding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.activeShoppingList -> {
                    viewBinding.fab.makeVisible()
                    Utils.openFragment(
                        activeShoppingListsFragment,
                        ActiveShoppingListsFragment.TAG,
                        supportFragmentManager
                    )
                }
                R.id.archivedShoppingList -> {
                    viewBinding.fab.makeGone()
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


    /**Two alert dialogs with for adding shopping list and grocery item respectively, few differences with logic
     * for instance after adding shopping list dialog disappears but after adding grocery item only text field is cleared.
     * I figured that it would be desired behaviour.*/
    private fun buildAlertDialogToAddShoppingList(){
        val editText = EditText(this)
        val alertDialog = AlertDialog.Builder(this)
            .setView(editText)
            .setMessage("Add shopping list")
            .setNegativeButton("Back", null)
            .setPositiveButton("Add", null)
            .show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newShoppingList = ShoppingList(
                0, editText.text.toString(), true,
                java.util.Calendar.getInstance().time
            )
            viewModel.addShoppingList(newShoppingList)
            alertDialog.dismiss()
            viewBinding.root.makeSnackbar("${newShoppingList.shortDescription} created!")
        }
    }

    private fun buildAlertDialogToAddGroceryItemToShoppingList(){
        val editText = EditText(this)
        val alertDialog = AlertDialog.Builder(this)
            .setView(editText)
            .setMessage("Add grocery item to the list")
            .setNegativeButton("Back", null)
            .setPositiveButton("Add", null)
            .show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val groceryItem = GroceryItem(0,editText.text.toString(),viewModel.presentedShoppingList.value!!.id,false)
            viewModel.addGroceryItem(groceryItem)
            editText.text.clear()
            viewBinding.root.makeSnackbar("${groceryItem.name} added!")

        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1) finish()
        else super.onBackPressed()

        when (supportFragmentManager.fragments.last().tag) {
            ActiveShoppingListsFragment.TAG -> {
                viewBinding.fab.makeVisible()
                viewBinding.bottomNavigation.selectedItemId = R.id.activeShoppingList
            }
            ArchivedShoppingListsFragment.TAG -> {
                viewBinding.fab.makeGone()
                viewBinding.bottomNavigation.selectedItemId = R.id.archivedShoppingList
            }
            else -> viewBinding.bottomNavigation.uncheckAllItems()
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