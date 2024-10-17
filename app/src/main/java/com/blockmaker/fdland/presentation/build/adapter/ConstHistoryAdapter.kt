package com.blockmaker.fdland.presentation.build.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blockmaker.fdland.data.model.ConstructHistory
import com.blockmaker.fdland.databinding.ItemResultCheckConstBinding

class ConstHistoryAdapter : ListAdapter<ConstructHistory, ConstHistoryAdapter.ConstHistoryViewHolder>(ConstHistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstHistoryViewHolder {
        val binding = ItemResultCheckConstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConstHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConstHistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ConstHistoryViewHolder(private val binding: ItemResultCheckConstBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConstructHistory) {
            // 생성 날짜를 직접 바인딩하여 아이템에 표시
            binding.tvItemTime.text = item.createDate
        }
    }

    class ConstHistoryDiffCallback : DiffUtil.ItemCallback<ConstructHistory>() {
        override fun areItemsTheSame(oldItem: ConstructHistory, newItem: ConstructHistory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ConstructHistory, newItem: ConstructHistory): Boolean {
            return oldItem == newItem
        }
    }
}