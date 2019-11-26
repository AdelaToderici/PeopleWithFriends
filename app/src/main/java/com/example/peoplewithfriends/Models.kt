package com.example.peoplewithfriends

class PersonModel(val address: String,
                  val city: String,
                  val email: String,
                  val firstName: String,
                  val friends: List<PersonModel>?,
                  val id: String,
                  val imageURL: String?,
                  val lastName: String,
                  val phoneNumber: String,
                  val state: String,
                  val zipCode: String)

class FriendModel(val name: String, val email: String, val id: String, val imageURL: String?) {

}

class PersonSummaryModel(val name: String, val email: String, val imageURL: String?) {

}

class AddressModel(val address: String) {

}

class PhoneModel(val phoneNumber: String) {

}