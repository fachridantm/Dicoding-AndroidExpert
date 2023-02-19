package com.dicoding.githubapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.githubapp.R
import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.ui.ListUserAdapter
import com.dicoding.githubapp.core.utils.showMessage
import com.dicoding.githubapp.databinding.ActivityHomeBinding
import com.dicoding.githubapp.ui.detail.DetailUserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var userQuery: String

    private lateinit var homeBinding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val listUserAdapter: ListUserAdapter by lazy { ListUserAdapter(::userItemClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        searchUser()
        setupData()
        setupTheme()
    }

    private fun searchUser() {
        homeBinding.svUser.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    clearFocus()

                    val getData = homeViewModel.searchUser(userQuery)

                    if (userQuery.isEmpty() || getData.equals(null)) {
                        showImage(true)
                        listUserAdapter.submitList(null)
                    } else {
                        showImage(false)
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    userQuery = newText.toString()
                    if (userQuery.isEmpty()) {
                        showImage(true)
                        listUserAdapter.submitList(null)
                    } else {
                        showImage(false)
                    }
                    return true
                }
            })
        }
    }

    private fun setupData() {
        homeViewModel.result.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                    showImage(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showImage(false)
                    if (!it.data.isNullOrEmpty()) {
                        listUserAdapter.submitList(it.data)
                        setupAdapter()
                    } else {
                        listUserAdapter.submitList(emptyList())
                        showImage(true)
                        "User not found".showMessage(this)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showImage(true)
                    it.message.toString().showMessage(this)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        homeBinding.pbMain.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showImage(isImageVisible: Boolean) {
        homeBinding.ivDoodleFind.visibility = if (isImageVisible) View.VISIBLE else View.INVISIBLE
        homeBinding.tvDoodleFind.visibility = if (isImageVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun setupAdapter() {
        with(homeBinding.rvUsers) {
            setHasFixedSize(true)
            adapter = listUserAdapter
        }
    }

    private fun userItemClicked(user: User) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    private fun setupTheme() {
        homeViewModel.getThemeSetting().observe(this) { isNightMode ->
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val modeButton = menu.findItem(R.id.modeButton)
        homeViewModel.getThemeSetting().observe(this) { isNightMode ->
            if (isNightMode) modeButton.setIcon(R.drawable.ic_light_mode) else modeButton.setIcon(R.drawable.ic_night_mode)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favButton -> {
                val uri = Uri.parse("githubfinduser://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            R.id.modeButton -> {
                themeDialog()
                true
            }
            else -> true
        }
    }

    private fun themeDialog() {
        val modes = arrayOf("Dark Mode", "Light Mode")
        val builder = Builder(this)

        builder.setTitle("Select Theme Mode")
        builder.setItems(modes) { _, which ->
            homeViewModel.saveThemeSetting(which == 0)
            "Apply your theme to ${modes[which]}".showMessage(this)
        }
        builder.show()
    }
}