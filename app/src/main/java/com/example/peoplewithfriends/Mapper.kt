package com.example.peoplewithfriends

class Mapper {

    companion object {

        fun mapPersonSummary(person: PersonModel): PersonSummaryModel {
            return PersonSummaryModel(
                name = person.firstName.capitalize() + " " + person.lastName.capitalize(),
                email = person.email,
                imageURL = person.imageURL
            )
        }

        fun mapAddress(person: PersonModel): AddressModel {
            return AddressModel(
                address = person.address + " " + person.city + ", " + person.state + " " + person.zipCode
            )
        }

        fun mapPhone(person: PersonModel): PhoneModel {
            return PhoneModel(
                phoneNumber = person.phoneNumber
            )
        }

        fun mapFriend(person: PersonModel): FriendModel {
            return FriendModel(
                name = person.firstName.capitalize() + " " + person.lastName.capitalize(),
                email = person.email,
                id = person.id,
                imageURL = person.imageURL
            )
        }

    }
}