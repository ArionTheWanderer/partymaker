package com.example.partymaker.presentation.ui

import android.os.Bundle
import com.example.partymaker.databinding.ActivityMainBinding
import com.example.partymaker.presentation.ui.common.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
