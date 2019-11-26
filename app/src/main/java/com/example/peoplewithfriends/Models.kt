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
                  val username: String,
                  val zipCode: String)

class FriendModel {
    val name: String
    val email: String
    val id: String
    val imageURL: String?

    constructor(name: String, email: String, id: String, imageURL: String?) {
        this.name = name
        this.email = email
        this.id = id
        this.imageURL = imageURL
    }
}

class PersonSummaryModel {
    val name: String
    val email: String
    val imageURL: String?

    constructor(name: String, email: String, imageURL: String?) {
        this.name = name
        this.email = email
        this.imageURL = imageURL
    }
}

class AddressModel {
    val address: String

    constructor(address: String) {
        this.address = address
    }
}

class PhoneModel {
    val phoneNumber: String

    constructor(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }
}