package com.apx5.apx5.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.apx5.apx5.ProoyaClient
import com.apx5.apx5.utils.CommonUtils


/**
 * BaseActivity
 */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseFragment.Callback {

    private lateinit var viewDataBinding: T
    private val appContext: Context = ProoyaClient.appContext

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    /**
     * Binding 을 위한 함수
     */
    abstract fun getBindingVariable(): Int

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    fun showKeyboard(targetEditText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(targetEditText, 0)
    }

    open fun hideKeyboard() {
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    fun hideLoading() {
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

    fun getViewDataBinding() : T {
        return viewDataBinding
    }

    fun getAppContext() : Context {
        return appContext
    }

//    private var mProgressDialog: ProgressDialog? = null
//    var viewDataBinding: T? = null
//        private set
//    private var mViewModel: V? = null
//
//    /**
//     * Override for set binding variable
//     *
//     * @return variable id
//     */
//    abstract val bindingVariable: Int
//
//    /**
//     * @return layout resource id
//     */
//    @get:LayoutRes
//    abstract val layoutId: Int
//
//    /**
//     * Override for set view model
//     *
//     * @return view model instance
//     */
//    abstract val viewModel: V
//
//    val isNetworkConnected: Boolean
//        get() = NetworkUtils.isNetworkConnected(applicationContext)
//
//    override fun onFragmentAttached() {
//
//    }
//
//    override fun onFragmentDetached(tag: String) {
//
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        performDataBinding()
//    }
//
//    fun hideLoading() {
//        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
//            mProgressDialog!!.cancel()
//        }
//    }
//
//    fun showLoading() {
//        hideLoading()
//        mProgressDialog = CommonUtils.showLoadingDialog(this)
//    }
//
//    private fun performDataBinding() {
//        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
//        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
//        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
//        viewDataBinding!!.executePendingBindings()
//    }
}