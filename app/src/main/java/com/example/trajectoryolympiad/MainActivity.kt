package com.example.trajectoryolympiad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import com.example.trajectoryolympiad.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupOnBackPressedDispatcher()
        setupToolbar()

    }

    private fun setupOnBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        supportFragmentManager.apply {
            addOnBackStackChangedListener {
                if (backStackEntryCount > 0) {
                    toolbar.navigationIcon =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_arrow_back_24)
                    toolbar.setNavigationOnClickListener {
                        popBackStack()
                    }
                } else {
                    toolbar.navigationIcon = null
                }
            }
        }
    }

    var toolbarTitle: String
        get() = binding.toolbar.title.toString()
        set(value) {
            binding.toolbar.title = value
        }
}