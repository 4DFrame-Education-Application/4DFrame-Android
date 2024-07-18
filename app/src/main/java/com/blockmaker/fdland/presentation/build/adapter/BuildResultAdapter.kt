package com.blockmaker.fdland.presentation.build.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blockmaker.fdland.data.model.BuildResult
import com.blockmaker.fdland.databinding.ItemResultBuildBinding

class BuildResultAdapter(
    private val buildResults: List<BuildResult>
) : RecyclerView.Adapter<BuildResultAdapter.BuildResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildResultViewHolder {
        val binding = ItemResultBuildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuildResultViewHolder, position: Int) {
        holder.bind(buildResults[position])
    }

    override fun getItemCount(): Int = buildResults.size

    inner class BuildResultViewHolder(private val binding: ItemResultBuildBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(buildResult: BuildResult) {
            binding.buildResult = buildResult
            binding.executePendingBindings()
        }
    }
}
