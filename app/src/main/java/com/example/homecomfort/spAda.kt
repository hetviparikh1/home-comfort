package com.example.homecomfort

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sp_layout.view.*

class spAda(var ctx: Context,var list:ArrayList<Spuser>):RecyclerView.Adapter<spAda.ViewHolder>() {
    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        var s = v.txtNm
        var c = v.txtCn
        var a = v.txtAd
        var e = v.txtEp
        var t = v.txtTm
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(ctx).inflate(R.layout.sp_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  list.size
    }





    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.s.text= list[position].name.toString()
        holder.c.text= list[position].cno.toString()
        holder.a.text= list[position].address.toString()
        holder.e.text= list[position].exp.toString()
        holder.t.text= list[position].time.toString()
        holder.itemView.setOnClickListener {

        }
    }



}