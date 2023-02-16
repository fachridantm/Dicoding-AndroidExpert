package com.dicoding.githubapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.githubapp.R
import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.data.source.local.entity.UserEntity
import com.dicoding.githubapp.core.ui.FavoriteAdapter
import com.dicoding.githubapp.core.utils.showMessage
import com.dicoding.githubapp.databinding.ActivityFavoriteUserBinding
import com.dicoding.githubapp.ui.detail.DetailUserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var favoriteUserBinding: ActivityFavoriteUserBinding
    private val favoriteAdapter: FavoriteAdapter by lazy { FavoriteAdapter(::favoriteItemClicked) }
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(favoriteUserBinding.root)

        initView()
        setupData()
        setupAdapter()
    }

    private fun initView() {
        supportActionBar?.apply {
            title = getString(R.string.title_favorite)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupData() {
        favoriteViewModel.getFavoritedUsers().observe(this) {
            when (it) {
                is Resource.Loading -> {
                    favoriteUserBinding.pbFav.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    favoriteUserBinding.pbFav.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        showImage(false)
                        favoriteAdapter.submitList(it.data)
                    } else {
                        showImage(true)
                    }
                }
                is Resource.Error -> {
                    favoriteUserBinding.pbFav.visibility = View.GONE
                    it.message.toString().showMessage(this)
                }
            }
        }
    }

    private fun setupAdapter() {
        favoriteUserBinding.rvFav.apply {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun showImage(isImageVisible: Boolean) {
        favoriteUserBinding.ivDoodleFav.visibility =
            if (isImageVisible) View.VISIBLE else View.INVISIBLE
        favoriteUserBinding.tvDoodleFav.visibility =
            if (isImageVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun favoriteItemClicked(user: UserEntity) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user.username)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}