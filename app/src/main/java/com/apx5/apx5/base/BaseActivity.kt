package com.apx5.apx5.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.apx5.apx5.ProoyaClient
import com.apx5.apx5.utils.CommonUtils


/**
 * BaseActivity
 */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> :
    AppCompatActivity(),
    BaseFragment.Callback {

    private lateinit var viewDataBinding: T

    private val appContext: Context = ProoyaClient.appContext

    @LayoutRes abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    abstract fun getBindingVariable(): Int

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    override fun onFragmentAttached() { }

    override fun onFragmentDetached(tag: String) { }

    private fun hideLoading() {
        if (progressDialog.isShowing) {
            progressDialog.cancel()
        }
    }

    fun showLoading() {
        hideLoading()
        progressDialog = CommonUtils.showProgressDialog(this)
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.setVariable(getBindingVariable(), getViewModel())
        viewDataBinding.executePendingBindings()
    }

    fun binding() = viewDataBinding

    fun getAppContext() = appContext
}