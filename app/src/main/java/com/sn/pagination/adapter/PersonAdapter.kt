package com.sn.pagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sn.pagination.data.Person
import com.sn.pagination.databinding.ItemPersonBinding
import javax.inject.Inject

class PersonAdapter @Inject constructor() :
    PagingDataAdapter<Person, PersonAdapter.PersonViewHolder>(PersonComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PersonViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PersonViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.person = person
        }
    }

    object PersonComparator : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Person, newItem: Person) =
            oldItem == newItem
    }
}