package com.easytools.currencyconverter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class SpinnerAdapter(context: Context, list: ArrayList<Item>) : ArrayAdapter<Item>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        var conView = convertView
        if(conView == null) {
            conView = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        }

        var tvCurrencyCode: TextView = conView!!.findViewById(R.id.tvCurrencyCode)
        var ivFlag: ImageView = conView!!.findViewById(R.id.ivFlag)

        var currentItem: Item? = getItem(position)

        if(currentItem != null) {
            tvCurrencyCode.setText(currentItem.getCode())
            ivFlag.setImageResource(currentItem.getImage())
        }

        return conView!!
    }

}
