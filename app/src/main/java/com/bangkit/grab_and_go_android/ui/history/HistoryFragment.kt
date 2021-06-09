package com.bangkit.grab_and_go_android.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.grab_and_go_android.databinding.FragmentHistoryBinding
import com.bangkit.grab_and_go_android.utils.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var rvAdapter: HistoryRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = binding.toolbar
        toolbar.setNavigationIcon(Util.getBackButtonIconRes())
        toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })
        setupRecyclerView()

        viewModel.history.observe(viewLifecycleOwner, { list ->
            list?.let {
                rvAdapter.setData(it)
            }
        })
    }


    private fun setupRecyclerView() {
        rvAdapter = HistoryRecyclerAdapter()
        binding.rvHistory.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

}