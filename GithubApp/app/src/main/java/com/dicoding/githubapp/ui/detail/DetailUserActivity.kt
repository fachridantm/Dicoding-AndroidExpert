package com.dicoding.githubapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dicoding.githubapp.R
import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.data.source.local.entity.UserEntity
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.ui.follow.TabFollowAdapter
import com.dicoding.githubapp.core.utils.convertToShortNumber
import com.dicoding.githubapp.core.utils.loadUserImage
import com.dicoding.githubapp.core.utils.showMessage
import com.dicoding.githubapp.databinding.ActivityDetailBinding
import com.dicoding.githubapp.ui.favorite.FavoriteViewModel
import com.dicoding.githubapp.ui.follow.FollowFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private var username: String? = null
    private var url: String? = null
    private var avatar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        initView()
        initData()
        setupFavorite()
    }

    private fun initView() {
        supportActionBar?.apply {
            title = getString(R.string.title_detail)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initData() {
        val username = intent.getStringExtra(EXTRA_USER)

        detailUserViewModel.getDetailUser(username).observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    this.username = username
                    it.data?.let { user ->
                        showLoading(false)
                        setupData(user)
                        setupTabLayout(user.username)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    it.message.toString().showMessage(this)
                }
            }
        }
    }

    private fun setupData(user: User) {
        with(detailBinding) {
            tvName.text = user.name
            tvUsername.text = user.username
            tvLocation.text = user.location
            tvCompany.text = user.company
            tvRepository.text = user.repository.convertToShortNumber()
            tvFollowers.text = user.followers.convertToShortNumber()
            tvFollowing.text = user.following.convertToShortNumber()
            ivAvatar.loadUserImage(user.avatar)
        }

        username = user.username
        url = user.url
        avatar = user.avatar
    }

    private fun setupTabLayout(username: String) {
        val fragment = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWING),
            FollowFragment.newInstance(FollowFragment.FOLLOWERS)
        )

        val fragmentTitle = mutableListOf(
            getString(R.string.following),
            getString(R.string.followers)
        )

        val tabFollowAdapter = TabFollowAdapter(this@DetailUserActivity, fragment)
        detailBinding.viewPager.adapter = tabFollowAdapter

        TabLayoutMediator(detailBinding.tabLayout, detailBinding.viewPager) { tab, position ->
            tab.text = fragmentTitle[position]
        }.attach()

        detailBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == FollowFragment.FOLLOWERS) {
                    detailUserViewModel.getUserFollowers(username)
                } else {
                    detailUserViewModel.getUserFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        detailUserViewModel.getUserFollowing(username)
    }

    private fun setupFavorite() {
        favoriteViewModel.getFavoritedUsers().observe(this) { user ->
            val isFavorited = user.data?.any { it.username == username }
            setFavoriteState(isFavorited ?: false)

            detailBinding.fabFav.setOnClickListener {
                val entity = username?.let { UserEntity(it, avatar, false) }

                try {
                    if (entity != null) {
                        user.data?.any { it.username == username }
                            ?.let { state -> favoriteViewModel.setFavoritedUser(entity, state) }
                    }
                } catch (e: Exception) {
                    e.printStackTrace().toString().showMessage(this)
                }

                if (isFavorited == true) {
                    "Remove $username from favorite".showMessage(this)
                    setFavoriteState(false)
                } else {
                    "Add $username to favorite".showMessage(this)
                    setFavoriteState(true)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFavoriteState(isFavorited: Boolean) {
        detailBinding.fabFav.apply {
            if (isFavorited) {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.ic_favorited
                    )
                )
            } else {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.ic_favorite
                    )
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.shareButton -> {
                intentShareAction()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun intentShareAction() {
        val intent = Intent(Intent.ACTION_SEND)
        val shareUser = "Follow $username on github! \n$url"
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareUser)
        startActivity(Intent.createChooser(intent, "Share with..."))
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}