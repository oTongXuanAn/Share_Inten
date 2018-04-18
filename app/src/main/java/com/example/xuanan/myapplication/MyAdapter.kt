package com.example.xuanan.myapplication

import android.content.Context
import android.content.pm.ResolveInfo
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_view.view.*
import java.util.ArrayList

/**
 * Created by xuan an on 4/19/2018.
 */
class MyAdapter(var c: Context, var items: ArrayList<ResolveInfo>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder? {
        val inflater = LayoutInflater.from(parent!!.getContext())
        val view = inflater.inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view).listen { pos, type ->
            val item = items.get(pos)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: ResolveInfo) {
            itemView.text1.text = item.activityInfo.applicationInfo.name
            //  itemView.icon.setImageDrawable(item.activityInfo.applicationInfo.loadIcon(item.activityInfo.applicationInfo.packageName(con)))
        }

    }
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(getAdapterPosition(), getItemViewType())
    }
    return this
}