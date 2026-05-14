package com.example.deepfakedetector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deepfakedetector.data.model.HistoryItem
import com.example.deepfakedetector.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter(
    private val items: MutableList<HistoryItem>,
    private val onItemClick: ((HistoryItem) -> Unit)? = null,
    private val onDeleteClick: ((HistoryItem) -> Unit)? = null
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoryItem) {
            binding.apply {
                // Load image
                // Glide.with(root.context)
                //     .load(item.imageUri)
                //     .into(imageViewHistory)

                textViewLabel.text = item.label
                textViewConfidence.text = "Confidence: ${String.format("%.2f%%", item.confidenceScore * 100)}"

                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val dateString = dateFormat.format(Date(item.timestamp))
                textViewTimestamp.text = dateString

                root.setOnClickListener { onItemClick?.invoke(item) }
                buttonDelete.setOnClickListener { onDeleteClick?.invoke(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<HistoryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

