package com.example.asodes.infrastructure.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BackgroundRunner {

    companion object {
        @JvmStatic
        fun run(func: suspend () -> Unit) {
            GlobalScope.launch {
                func()
            }
        }
    }
}