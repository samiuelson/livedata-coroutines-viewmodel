package com.netguru.sampleapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector

class MainActivity : AppCompatActivity() {

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.state().observe(this, Observer { state ->
            when(state) {
                State.Loading -> showProgressBar()
                is State.Hello -> showHello(state.count)
            }
        })

        viewModel.state2().observe(this, Observer { state ->
            when(state) {
                State.Loading -> showProgressBar2()
                is State.Hello -> showHello2(state.count)
            }
        })

        lifecycleScope.launchWhenResumed {
            viewModel.state3.collect(object : FlowCollector<State> {
                override suspend fun emit(value: State) {
                    when(value) {
                        State.Loading -> showProgressBar3()
                        is State.Hello -> showHello3(value.count)
                    }
                }
            })
        }
    }

    private fun showProgressBar() {
        textView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun showHello(count: Int) {
        progressBar.visibility = View.INVISIBLE
        textView.visibility = View.VISIBLE
        textView.text = "$count"
    }

    private fun showProgressBar2() {
        textView2.visibility = View.INVISIBLE
        progressBar2.visibility = View.VISIBLE
    }

    private fun showHello2(count: Int) {
        progressBar2.visibility = View.INVISIBLE
        textView2.visibility = View.VISIBLE
        textView2.text = "$count"
    }

    private fun showProgressBar3() {
        textView3.visibility = View.INVISIBLE
        progressBar3.visibility = View.VISIBLE
    }

    private fun showHello3(count: Int) {
        progressBar3.visibility = View.INVISIBLE
        textView3.visibility = View.VISIBLE
        textView3.text = "$count"
    }
}
