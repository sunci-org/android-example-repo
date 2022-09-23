package com.example.finalapplication.screen.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.finalapplication.R
import com.example.finalapplication.utils.base.BaseActivity
import com.example.finalapplication.databinding.ActivityMainBinding
import com.example.finalapplication.screen.historycontact.HistoryContactFragment
import com.example.finalapplication.screen.profile.ProfileFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun handleEvent() {
        binding.apply {
            botomnavigationMain.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.message ->
                        pagerMain.currentItem = 0

                    else ->
                        pagerMain.currentItem = 1
                }
                return@setOnItemSelectedListener true
            }
            pagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> botomnavigationMain.menu.findItem(R.id.message).isChecked = true
                        1 -> botomnavigationMain.menu.findItem(R.id.account).isChecked = true
                        else -> {}
                    }
                }
            })
        }
    }

    override fun initData() {
        if (!checkPermission()) requestPermission()
        binding.apply {
            val mainPagerAdapter = MainViewPagerAdapter(this@MainActivity)
            mainPagerAdapter.setFragment(listOf(HistoryContactFragment(), ProfileFragment()))
            pagerMain.adapter = mainPagerAdapter
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            REQUEST_CODE_PERMISSION
        )
    }

    private fun checkPermission(): Boolean {
        val readStorge = ActivityCompat
            .checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        val manageStorge = ActivityCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return readStorge && manageStorge
    }

    override fun bindData() {
        // TODO  no-ip
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 101
    }
}
