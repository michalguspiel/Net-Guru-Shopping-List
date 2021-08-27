package com.erdees.netgurushoppinglist.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.erdees.netgurushoppinglist.databinding.ActivityMainBinding
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var viewModel : MainActivityViewModel


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
                .setNegativeButton("Back",null)
                .setPositiveButton("Add",null)
                .show()

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val newShoppingList = ShoppingList(0,editText.text.toString(),true,
                    java.util.Calendar.getInstance().time)
                viewModel.addShoppingList(newShoppingList)

            }

            viewModel.getActiveShoppingLists.observe(this,{ listOfLists ->
                listOfLists.forEach{Log.i(TAG,it.toString())}
            })

        }

    }

    companion object {
        const val TAG = "MainActivity"
    }
}