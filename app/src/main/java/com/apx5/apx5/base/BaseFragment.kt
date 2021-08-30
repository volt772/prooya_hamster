package com.apx5.apx5.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.apx5.apx5.ProoyaClient

/**
 * BaseFragment
 */

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private lateinit var viewDataBinding: T

    private var activity: BaseActivity<*>? = null

    private val appContext: Context = ProoyaClient.appContext

    @LayoutRes abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.apply {
            lifecycleOwner = this@BaseFragment.viewLifecycleOwner
            executePendingBindings()
        }
    }

    fun binding() = viewDataBinding

    fun getBaseActivity() = activity

    fun getAppContext() = appContext

    override fun onDetach() {
        activity = null
        super.onDetach()
    }
}