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

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment() {

    private lateinit var viewDataBinding: T
    private var activity: BaseActivity<*, *>? = null
    private val appContext: Context = ProoyaClient.appContext

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun getViewModel(): V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BaseActivity<*, *>) {
            activity = context
            activity?.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(getBindingVariable(), getViewModel())
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    fun binding() : T {
        return viewDataBinding
    }

    fun getBaseActivity() : BaseActivity<*, *>? {
        return activity
    }

    fun getAppContext() : Context {
        return appContext
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    fun hideKeyboard() {
        if (activity != null) {
            activity!!.hideKeyboard()
        }
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
//    var baseActivity: BaseActivity<*, *>? = null
//        private set
//    private var mRootView: View? = null
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
//        get() = baseActivity != null && baseActivity!!.isNetworkConnected
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is BaseActivity<*, *>) {
//            this.baseActivity = context
//            context.onFragmentAttached()
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mViewModel = viewModel
//        setHasOptionsMenu(false)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
//        mRootView = viewDataBinding!!.root
//        return mRootView
//    }
//
//    override fun onDetach() {
//        baseActivity = null
//        super.onDetach()
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
//        viewDataBinding!!.executePendingBindings()
//    }
//
//    interface Callback {
//        fun onFragmentAttached()
//        fun onFragmentDetached(tag: String)
//    }
}

