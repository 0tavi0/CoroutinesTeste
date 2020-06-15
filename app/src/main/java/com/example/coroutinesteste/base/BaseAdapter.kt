package com.example.coroutinesteste.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T>(protected open val viewModel: AdapterViewModel<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected abstract val itemLayout: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                itemLayout,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = viewModel.itemCount

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as BaseAdapter<*>.ViewHolder).bind(position)

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            bindItem(getItem(position), view, position)
        }
    }

    protected open fun getItem(position: Int): T {
        return viewModel.item(position)
    }
    abstract fun bindItem(item: T, view: View, position: Int)

}