package com.erdees.netgurushoppinglist.view.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.erdees.netgurushoppinglist.Constants.FRAGMENT_NAME
import com.erdees.netgurushoppinglist.R
import com.erdees.netgurushoppinglist.Utils
import com.erdees.netgurushoppinglist.databinding.ActivityMainBinding
import com.erdees.netgurushoppinglist.view.activeShoppingListFragment.ActiveShoppingListsFragment
import com.erdees.netgurushoppinglist.view.archivedShoppingListFragment.ArchivedShoppingListsFragment
import com.erdees.netgurushoppinglist.view.singleShoppingListFragment.SingleShoppingListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private val activeShoppingListsFragment = ActiveShoppingListsFragment()
    private val archivedShoppingListFragment = ArchivedShoppingListsFragment()
    val singleShoppingListFragment = SingleShoppingListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        if (savedInstanceState != null) {
            val fragment = supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_NAME)
            Utils.openFragment(fragment!!, fragment.tag!!, supportFragmentManager)
            adjustUi()
        } else openActiveShoppingListsFragment()
        val view = viewBinding.root
        setContentView(view)

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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(
            outState,
            FRAGMENT_NAME,
            supportFragmentManager.fragments.last()
        );
    }


    private fun openActiveShoppingListsFragment() {
        Utils.openFragment(
            activeShoppingListsFragment,
            ActiveShoppingListsFragment.TAG,
            supportFragmentManager
        )
        supportActionBar?.title = getString(R.string.shopping_lists)
    }

    private fun openArchivedShoppingListsFragment() {
        Utils.openFragment(
            archivedShoppingListFragment,
            ArchivedShoppingListsFragment.TAG,
            supportFragmentManager
        )
        supportActionBar?.title = getString(R.string.archived_shopping_lists)
    }

    private fun setUiForShoppingListDetails() {
        viewBinding.bottomNavigation.uncheckAllItems()
        supportActionBar?.title = getString(R.string.shopping_lists_details)
    }

    private fun adjustUi() {
        supportFragmentManager.fragments.last().tag?.let { Log.i(TAG, it) }
        when (supportFragmentManager.fragments.last().tag) {
            ActiveShoppingListsFragment.TAG -> {
                viewBinding.bottomNavigation.selectedItemId = R.id.activeShoppingList
                supportActionBar?.title = getString(R.string.shopping_lists)
            }
            ArchivedShoppingListsFragment.TAG -> {
                viewBinding.bottomNavigation.selectedItemId = R.id.archivedShoppingList
                supportActionBar?.title = getString(R.string.archived_shopping_lists)
            }
            else -> {
                setUiForShoppingListDetails()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) finish()
        else super.onBackPressed()
        adjustUi()
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