package com.example.peoplewithfriends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import com.google.gson.GsonBuilder

enum class Header_Type(val type: String) {
    ADDRESS("Address"),
    PHONE("Phone"),
    FRIENDS("Friends")
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // - setup recyclerview layout manager
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        // - get person id sent by intent
        val personId = intent.getStringExtra("PERSON_ID")
        if (personId != null && personId.length > 0) {
            // - fetch data when friend row selected
            fetchData(personId)
        } else {
            // - fetch data when app starts
            fetchData(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // - inflate customized menu
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        // - don't reload the list if the request is in progress
        if (progressBar_main.isVisible) {
            return true
        }

        // - refresh data when Person toolbar button selected
        if (id == R.id.action_refresh_user) {
            fetchData(null)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // - fetch data from server
    private fun fetchData(personId: String?) {

        showProgressBar()

        // - url configuration
        var url = "https://interview-api.shiftboard.com/person"
        if (personId != null) {
            url += "/" + personId
        }

        // - request configuration
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        // - request added in background thread using enqueue
        client.newCall(request).enqueue(object: Callback {

            // - request failed
            override fun onFailure(call: Call, e: IOException) {

                // - back to main thread
                runOnUiThread {
                    hideProgressBar()
                    Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_LONG).show()
                }
            }

            // - request succedded
            override fun onResponse(call: Call, response: Response) {
                // - extract resonse json string
                val body = response.body?.string()

                // - convert json string to gson object
                val gson = GsonBuilder().create()

                // - deserialize json and convert to model object (PersonModel)
                val person = gson.fromJson(body, PersonModel::class.java)

                // - prepare list of multiple objects for MainAdapter
                val list = configureData(person)

                // - go back to main thread
                runOnUiThread {
                    hideProgressBar()
                    // - initialize adapter
                    recyclerView_main.adapter = MainAdapter(list)
                }
            }
        })
    }

    // - Data configuration for adapter
    private fun configureData(person: PersonModel): List<Any> {

        // - configure PersonSummaryModel
        val personSummary = PersonSummaryModel(
            name = person.firstName.capitalize() + " " + person.lastName.capitalize(),
            email = person.email,
            imageURL = person.imageURL
        )

        // - configure AddressModel
        val address = AddressModel(
            address = person.address + " " + person.city + ", " + person.state + " " + person.zipCode
        )

        // - configure PhoneModel
        val phoneNumber = PhoneModel(
            phoneNumber = person.phoneNumber
        )

        // - create list to add all objects
        val list = mutableListOf<Any>()
        // - add person summary model
        list.add(personSummary)
        // - add address string header
        list.add(Header_Type.ADDRESS.type)
        // - add address model
        list.add(address)
        // - add phone string header
        list.add(Header_Type.PHONE.type)
        // - add phone model
        list.add(phoneNumber)


        if (person.friends != null && person.friends.count() > 0) {
            // - add firends string header
            list.add(Header_Type.FRIENDS.type)

            for (friend in person.friends) {
                // - configure friend model
                val f = FriendModel(
                    name = friend.firstName.capitalize() + " " + friend.lastName.capitalize(),
                    email = friend.email,
                    id = friend.id,
                    imageURL = friend.imageURL
                )
                // - add friend model
                list.add(f)
            }
        }

        return list
    }

    // - UI Methods
    private fun showProgressBar() {
        progressBar_main.visibility = View.VISIBLE
        recyclerView_main.visibility = View.GONE
    }

    private fun hideProgressBar() {
        progressBar_main.visibility = View.GONE
        recyclerView_main.visibility = View.VISIBLE
    }
}