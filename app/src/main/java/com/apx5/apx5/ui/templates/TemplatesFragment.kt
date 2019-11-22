package com.apx5.apx5.ui.templates

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders

import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.databinding.FragmentStaticsBinding

/**
 * TemplatesFragment
 */

class TemplatesFragment//    public class TemplatesFragment extends BaseFragment<FragmentStaticsBinding, StaticsViewModel>
//        implements StaticsNavigator {
//
//        private FragmentStaticsBinding mFragmentStaticsBinding;
//        private StaticsViewModel mStaticsViewModel;
//
//        public static TemplatesFragment newInstance() {
//            Bundle args = new Bundle();
//            TemplatesFragment fragment = new TemplatesFragment();
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public int getBindingVariable() {
//            return BR.viewModel;
//        }
//
//        @Override
//        public int getLayoutId() {
//            return R.layout.fragment_statics;
//        }
//
//        @Override
//        public StaticsViewModel getViewModel() {
//            mStaticsViewModel = ViewModelProviders.of(this).get(StaticsViewModel.class);
//            return mStaticsViewModel;
//        }
//
//        @Override
//        public void onCreate(@Nullable Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            mStaticsViewModel.setNavigator(this);
//        }
//
//        @Override
//        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//            super.onViewCreated(view, savedInstanceState);
//            mFragmentStaticsBinding = getViewDataBinding();
//            initView();
//        }
//
//        /**
//         * UI 초기화
//         */
//        private void initView() {
//        }
//    }
