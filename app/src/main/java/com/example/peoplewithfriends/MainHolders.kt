package com.example.peoplewithfriends

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.address_row.view.*
import kotlinx.android.synthetic.main.header_row.view.*
import kotlinx.android.synthetic.main.person_row.view.*

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