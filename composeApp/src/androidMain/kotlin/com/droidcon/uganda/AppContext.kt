package com.droidcon.uganda

import android.content.Context

object AppContext {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun get(): Context = context
}
