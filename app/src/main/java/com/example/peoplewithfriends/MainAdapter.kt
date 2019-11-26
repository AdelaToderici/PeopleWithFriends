package com.example.peoplewithfriends

import android.content.Intent
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.address_row.view.*
import kotlinx.android.synthetic.main.header_row.view.*
import kotlinx.android.synthetic.main.person_row.view.*


class MainAdapter(val list: List<Any>): RecyclerView.Adapter<MainAdapter.BaseViewHolder<*>>() {

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

    // - abstract class created to allow multiple views in a single adapter
    abstract class BaseViewHolder<in T>(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    class PersonViewHolder(itemView: View): BaseViewHolder<PersonSummaryModel>(itemView) {
        private val nameTextView = itemView.textView_name
        private val emailTextView = itemView.textView_email
        private val thumbnail = itemView.imageView_profile

        override fun bind(item: PersonSummaryModel) {
            nameTextView.text = item.name
            emailTextView.text = item.email

            if (item.imageURL != null) {
                Picasso.get().load(item.imageURL).into(thumbnail)
            }
        }
    }

    class AddressViewHolder(itemView: View): BaseViewHolder<AddressModel>(itemView) {

        private val detailTextView = itemView.textView_detail

        override fun bind(item: AddressModel) {
            detailTextView.text = item.address
        }
    }

    class PhoneViewHolder(itemView: View): BaseViewHolder<PhoneModel>(itemView) {

        private val detailTextView = itemView.textView_detail

        override fun bind(item: PhoneModel) {
            detailTextView.text = item.phoneNumber
        }
    }

    class FriendsViewHolder(itemView: View): BaseViewHolder<FriendModel>(itemView) {

        companion object {
            val PERSON_ID_KEY = "PERSON_ID"
        }

        private val nameTextView = itemView.textView_name
        private val emailTextView = itemView.textView_email
        private val thumbnail = itemView.imageView_profile
        private var personId = String()

        override fun bind(item: FriendModel) {
            personId = item.id
            nameTextView.text = item.name
            emailTextView.text = item.email

            // - download profile image if url exists
            if (item.imageURL != null) {
                Picasso.get().load(item.imageURL).into(thumbnail)
            }
        }

        init {
            // - action when friend row is selected
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, MainActivity::class.java)
                // - pass selected person id to MainActivity
                intent.putExtra(PERSON_ID_KEY, personId)
                itemView.context.startActivity(intent)
            }
        }
    }

    class HeaderViewHolder(itemView: View): BaseViewHolder<String>(itemView) {

        private val headerTextView = itemView.textView_header

        override fun bind(item: String) {
            headerTextView.text = item
        }
    }
}