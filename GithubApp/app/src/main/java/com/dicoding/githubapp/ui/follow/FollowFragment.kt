package com.dicoding.githubapp.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.ui.FollowAdapter
import com.dicoding.githubapp.databinding.FragmentFollowBinding
import com.dicoding.githubapp.ui.detail.DetailUserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment() {

    private var position: Int = 0

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding

    private val followAdapter: FollowAdapter by lazy { FollowAdapter(::followItemClicked) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
    }

    fun setupData(user: List<User>) {
        if (user.isNotEmpty()) {
            followAdapter.submitList(user)
            binding?.tvEmpty?.visibility = View.GONE
        } else {
            binding?.tvEmpty?.visibility = View.VISIBLE
        }
    }

    private fun setupAdapter() {
        binding?.rvFollow?.apply {
            setHasFixedSize(true)
            adapter = followAdapter
        }
    }

    private fun followItemClicked(user: User) {
        val intent = Intent(requireContext(), DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    companion object {
        const val FOLLOWERS = 0
        const val FOLLOWING = 1

        fun newInstance(position: Int): FollowFragment {
            val fragment = FollowFragment()
            fragment.position = position
            return fragment
        }
    }
}