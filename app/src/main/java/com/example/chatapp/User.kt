package com.example.chatapp

class User {
    //to store the value of user
    var name : String? = null
    var email : String? = null
    var uid : String? = null

    constructor(){}

    constructor(name:String?, email:String?,uid:String?)
    {
        this.email=email
        this.name=name
        this.uid=uid

    }
}