package com.example.coroutinesteste.base

import androidx.lifecycle.MutableLiveData

interface AdapterViewModel<T> {
    val items: MutableLiveData<List<T>>

    fun item(position: Int): T {
        return items.value!![position]
    }

    val itemCount: Int
        get() = items.value?.size ?: 0

}