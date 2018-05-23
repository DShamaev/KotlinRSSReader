package com.dmitriyshamaev.kotlinreader.ui

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import com.dmitriyshamaev.kotlinreader.AppStorage
import com.dmitriyshamaev.kotlinreader.R
import com.dmitriyshamaev.kotlinreader.viewmodel.NewsListViewModel
import org.koin.android.ext.android.inject


class NewsActivity() : AppCompatActivity() {

    private val viewModel : NewsListViewModel by inject()
    private val appStorage : AppStorage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
    }

    override fun onSupportNavigateUp()
            = findNavController(this, R.id.nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actions_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_refresh -> {
            // User chose the "Refresh" item, update data from data source
            viewModel.updateDataFromNetwork()
            true
        }

        R.id.action_edit_source -> {
            // User chose the "Edit source" action, show dialog with option to change base url
            showEditSourceDialogIfNeeded()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun showEditSourceDialogIfNeeded() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("RSS source URL")

        // Set up the input
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(appStorage.rssUrl, TextView.BufferType.EDITABLE)
        input.setSelection(input.text.length)
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which -> viewModel.tryToLoadDataFromNewSource(input.text.toString()) })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
        viewModel.tryToLoadDataFromNewSource("")
    }
}
