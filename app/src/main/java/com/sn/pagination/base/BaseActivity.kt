package com.sn.pagination.base

import com.sn.pagination.extension.observe

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    private lateinit var viewModel: VM
    private lateinit var binding: DB

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getVM(): VM

    abstract fun bindVM(binding: DB, vm: VM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = getVM()
        bindVM(binding, viewModel)
        with(viewModel) {
            observe(errorMessage) { msg ->
                Toast.makeText(this@BaseActivity, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        lifecycleScope.launchWhenCreated {
            execute()
        }
    }

}