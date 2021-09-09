package com.apx5.apx5.ui.histories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.databinding.FragmentHistoriesBinding
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.datum.pitcher.PtDelHistory
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.network.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.adapter.HistoriesPagingAdapter
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utilities.PrUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HistoriesFragment
 */

@AndroidEntryPoint
class HistoriesFragment : BaseFragment<FragmentHistoriesBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    @Inject
    lateinit var prUtils: PrUtils

    private val hvm: HistoriesViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_histories
    override fun getBindingVariable() = BR.viewModel

    private var historiesPagingAdapter: HistoriesPagingAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscriber()
        collectUiState()
    }

    /**
     * collectUiState
     * @desc 전체기록 Fetch
     */
    private fun collectUiState() {
        val email = prPreference.userEmail?: ""
        viewLifecycleOwner.lifecycleScope.launch {
            hvm.getAllHistories(PtPostTeams(email, 0)).collectLatest { histories ->
                historiesPagingAdapter?.submitData(histories)
            }
        }
        cancelSpinKit()
    }

    /**
     * cancelSpinKit
     * @desc 로딩중 SpinKit 제거
     */
    private fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /**
     * initView
     */
    private fun initView() {
        historiesPagingAdapter = HistoriesPagingAdapter(prUtils, ::delHistoryItem)

        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding().apply {
            rvAllList.apply {
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                layoutManager = linearLayoutManager
                adapter = historiesPagingAdapter
            }
        }
    }

    /**
     * delHistoryItem
     * @desc 기록삭제 / Adapter Item
     */
    private fun delHistoryItem(delPlay: AdtPlayDelTarget) {
        val email = prPreference.userEmail?: ""

        if (email.isNotBlank()) {
            DialogActivity.dialogHistoryDelete(
                requireContext(),
                PtDelHistory(
                    pid = email,
                    rid = delPlay.id,
                    year = delPlay.season,
                    versus = delPlay.versus,
                    result = delPlay.result
                ),
                ::delHistory)
        }
    }

    /**
     * delHistory
     * @desc 기록삭제 / POST
     */
    private fun delHistory(delHistory: PtDelHistory) {
        hvm.requestDelHistory(delHistory)
    }

    /**
     * subscriber
     */
    private fun subscriber() {
        hvm.apply {
            getDelResult().observe(viewLifecycleOwner, PrObserver {
                if (it.count == 1) {
                    historiesPagingAdapter?.refresh()
                }
            })
        }
    }

    companion object {
        fun newInstance() = HistoriesFragment().apply { }
    }
}