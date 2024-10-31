package com.blockmaker.fdland.presentation.signup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import android.widget.ArrayAdapter
import com.blockmaker.fdland.R

class CustomSpinnerAdapter(
    context: Context,
    @LayoutRes private val resource: Int,
    private val items: List<String>
) : ArrayAdapter<String>(context, resource, items) {

    override fun getView(position: Int, @Nullable convertView: View?, @NonNull parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, @Nullable convertView: View?, @NonNull parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val textView = view.findViewById<TextView>(R.id.spinnerTextView)
        textView.text = items[position]
        return view
    }
}