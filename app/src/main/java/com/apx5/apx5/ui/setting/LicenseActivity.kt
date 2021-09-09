package com.apx5.apx5.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity
import com.apx5.apx5.constants.PrAdapterViewType
import com.apx5.apx5.databinding.ActivityLicenseBinding
import com.apx5.apx5.datum.adapter.AdtLicenseLists
import com.apx5.apx5.ext.setSystemBarColor
import com.apx5.apx5.ui.adapter.PrCentralAdapter
import com.apx5.apx5.ui.utilities.PrUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * LicenseActivity
 */

@AndroidEntryPoint
class LicenseActivity : BaseActivity<ActivityLicenseBinding>() {

    @Inject
    lateinit var prUtils: PrUtils

    override fun getLayoutId() = R.layout.activity_license
    override fun getBindingVariable() = BR.viewModel

    private lateinit var prCentralAdapter: PrCentralAdapter

    /* 라이센스 데이터 조합 (이름 / 내용)*/
    private val licenseList: List<AdtLicenseLists>
        get() {
            val licenses = resources.getStringArray(R.array.licenses)

            return arrayListOf<AdtLicenseLists>().also { _list ->
                licenses.forEach { _lic ->
                    _list.add(AdtLicenseLists(name = _lic, content = prUtils.getFileContents("licenses", _lic)))
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initComponent()
    }

    /**
     * initToolbar
     */
    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = "오픈라이선스"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        setSystemBarColor(this, R.color.p_main_first)
    }

    /**
     * initComponent
     */
    private fun initComponent() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        prCentralAdapter = PrCentralAdapter(
            context = this,
            viewType = PrAdapterViewType.LICENSE,
            prUtils = prUtils
        )

        binding().apply {
            rvLicense.apply {
                layoutManager = linearLayoutManager
                adapter = prCentralAdapter
            }
        }

        prCentralAdapter.apply {
            addLicenses(licenseList)
            notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) { android.R.id.home -> finish() }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, LicenseActivity::class.java)
    }
}