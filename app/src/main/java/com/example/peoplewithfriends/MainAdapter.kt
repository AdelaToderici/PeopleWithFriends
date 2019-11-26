package com.example.peoplewithfriends

import android.view.*
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(val list: List<Any>): RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        const val TYPE_PERSON = 0
        const val TYPE_ADDRESS = 1
        const val TYPE_PHONE = 2
        const val TYPE_FRIENDS = 3
        const val TYPE_HEADER = 4
    }

    private val data: List<Any> = list

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = data[position]
        // - setup view type based on data object (comparable)
        return when (comparable) {
            is PersonSummaryModel -> TYPE_PERSON
            is AddressModel -> TYPE_ADDRESS
            is PhoneModel -> TYPE_PHONE
            is FriendModel -> TYPE_FRIENDS
            is String -> TYPE_HEADER
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val context = parent.context
        // - inflate row in view holder
        return when (viewType) {
            TYPE_PERSON -> {
                val view = LayoutInflater.from(context).inflate(R.layout.person_row, parent, false)
                PersonViewHolder(view)
            }
            TYPE_ADDRESS -> {
                val view = LayoutInflater.from(context).inflate(R.layout.address_row, parent, false)
                AddressViewHolder(view)
            }
            TYPE_PHONE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.address_row, parent, false)
                PhoneViewHolder(view)
            }
            TYPE_FRIENDS -> {
                val view = LayoutInflater.from(context).inflate(R.layout.friend_row, parent, false)
                FriendsViewHolder(view)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.header_row, parent, false)
                HeaderViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = data[position]
        // - populate view holder ui components with element object
        when(holder) {
            is PersonViewHolder -> holder.bind(element as PersonSummaryModel)
            is AddressViewHolder -> holder.bind(element as AddressModel)
            is PhoneViewHolder -> holder.bind(element as PhoneModel)
            is FriendsViewHolder -> holder.bind(element as FriendModel)
            is HeaderViewHolder -> holder.bind(element as String)
        }
    }
}