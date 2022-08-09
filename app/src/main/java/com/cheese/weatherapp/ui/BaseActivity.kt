package com.cheese.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding>(private val invokeMethod: (LayoutInflater) -> VB) : AppCompatActivity() {
    private var _binding : VB? = null
    protected val binding: VB get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = invokeMethod.invoke(layoutInflater)
        setUpOnCreateView()
        setContentView(binding.root)
    }

    abstract fun setUpOnCreateView()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}