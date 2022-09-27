package com.example.finalapplication.screen.historycontact

import android.content.Intent
import com.example.finalapplication.utils.base.BaseFragment
import com.example.finalapplication.databinding.FragmentHistoryContactBinding
import com.example.finalapplication.screen.search.SearchActivity

class HistoryContactFragment :
    BaseFragment<FragmentHistoryContactBinding>(FragmentHistoryContactBinding::inflate) {

    override fun handleEvent() {
        binding.apply {
            containerSearch.setOnClickListener {
                val intent = Intent(context, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun initData() {
        // TODO("Not yet implemented")
    }

    override fun bindData() {
        // TODO("Not yet implemented")
    }
}
