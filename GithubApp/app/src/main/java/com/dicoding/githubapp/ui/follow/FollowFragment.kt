package com.dicoding.githubapp.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.domain.model.Follow
import com.dicoding.githubapp.core.ui.FollowAdapter
import com.dicoding.githubapp.core.utils.showMessage
import com.dicoding.githubapp.databinding.FragmentFollowBinding
import com.dicoding.githubapp.ui.detail.DetailUserActivity
import com.dicoding.githubapp.ui.detail.DetailUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment() {

    private var position = 0

    private val bindingFragment: FragmentFollowBinding by viewBinding()
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private val followAdapter: FollowAdapter by lazy { FollowAdapter(::followItemClicked) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentFollowBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
        setupAdapter()
    }

    private fun setupData() {
        detailUserViewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    bindingFragment.pbFollow.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    bindingFragment.pbFollow.visibility = View.GONE
                    if (it.data != null) {
                        followAdapter.submitList(it.data)
                    } else {
                        bindingFragment.tvEmpty.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    bindingFragment.pbFollow.visibility = View.GONE
                    bindingFragment.tvEmpty.visibility = View.VISIBLE
                    it.message.toString().showMessage(requireContext())
                }
            }
        }
    }

    private fun setupAdapter() {
        bindingFragment.rvFollow.apply {
            setHasFixedSize(true)
            adapter = followAdapter
        }
    }

    private fun followItemClicked(user: Follow) {
        val intent = Intent(requireActivity(), DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user.username)
        startActivity(intent)
    }

    companion object {
        const val FOLLOWING = 0
        const val FOLLOWERS = 1

        fun newInstance(position: Int): FollowFragment {
            val fragment = FollowFragment()
            fragment.position = position
            return fragment
        }
    }
}