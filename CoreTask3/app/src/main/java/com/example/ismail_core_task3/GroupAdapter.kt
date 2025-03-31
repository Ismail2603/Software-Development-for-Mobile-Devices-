package com.example.ismail_core_task3

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupAdapter(private var groups: List<Group>, private val onItemClick:
    (Group) -> Unit) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainText: TextView = itemView.findViewById(R.id.tvMainText)
        val subtitle1: TextView = itemView.findViewById(R.id.tvSubtitle1)
        val subtitle2: TextView = itemView.findViewById(R.id.tvSubtitle2)

        init {
            itemView.setOnClickListener {
                onItemClick(groups[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]
        holder.mainText.text = group.name
        holder.subtitle1.text = group.dateTime.toString()
        holder.subtitle2.text = group.location

        // Set background color based on the class name
        when (group.name) {
            "Apolitical" -> holder.itemView.setBackgroundColor(Color.parseColor("#00FF00")) // Green
            "Football" -> holder.itemView.setBackgroundColor(Color.parseColor("#FF0000")) // Red
            "UN Students" -> holder.itemView.setBackgroundColor(Color.parseColor("#0000FF")) // Blue
            "XSPORTS" -> holder.itemView.setBackgroundColor(Color.parseColor("#BEBEBE")) // Light Gray
            else -> holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF")) // Default White
        }
    }

    override fun getItemCount() = groups.size

    // Method to update the list
    fun updateList(newGroups: List<Group>) {
        groups = newGroups
        notifyDataSetChanged()
    }
}
