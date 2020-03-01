package com.apx5.apx5.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtLicenseLists
import com.apx5.apx5.ui.utils.MaterialTools
import java.io.IOException

/**
 * LicenseActivity
 */

class LicenseActivity :
    AppCompatActivity() {

    /* 라이센스 데이터 조합 (이름 / 내용)*/
    private val licenseData: List<AdtLicenseLists>
        get() {
            val items = arrayListOf<AdtLicenseLists>()
            val licenses = resources.getStringArray(R.array.licenses)

            for (lic in licenses) {
                val obj = AdtLicenseLists()
                obj.name = lic
                obj.content = getLicenseContent(lic)
                items.add(obj)
            }
            return items
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        initToolbar()
        initComponent()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = "오픈라이선스"
            setDisplayHomeAsUpEnabled(false)
        }

        MaterialTools.setSystemBarColor(this, R.color.p_navy_10)
    }

    private fun initComponent() {
        val licenseView = findViewById<RecyclerView>(R.id.rcv_license)
        licenseView.layoutManager = LinearLayoutManager(this)
        licenseView.setHasFixedSize(true)
        val licenseItems = licenseData

        val licenseListAdapter = LicenseListAdapter(licenseItems)
        licenseView.adapter = licenseListAdapter
    }

    /* 조항 내용 생성*/
    private fun getLicenseContent(licenseName: String): String {
        val licenseRes = StringBuilder()
        try {
            val filePath = "licenses/$licenseName"
            application.assets.open(filePath).bufferedReader().use {
                val line = it.readText()
                licenseRes.append(line)
            }
        } catch (e: IOException) {
            return ""
        }

        return licenseRes.toString()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, LicenseActivity::class.java)
    }
}
