package com.example.test5012

class NewUser(var name: String, var password: String, var position: String) {

    fun newUser(name: String?, password: String?, position: String?) {
        this.name = name!!
        this.password = password!!
        this.position = position!!
    }

    @JvmName("getName1")
    fun getName(): String? {
        return name
    }

    @JvmName("setName1")
    fun setName(name: String?) {
        this.name = name!!
    }

    @JvmName("getPassword1")
    fun getPassword(): String? {
        return password
    }

    @JvmName("setPassword1")
    fun setPassword(password: String?) {
        this.password = password!!
    }

    @JvmName("getPosition1")
    fun getPosition(): String? {
        return position
    }

    @JvmName("setPosition1")
    fun setPosition(position: String?) {
        this.position = position!!
    }
    override fun toString(): String {
        return "newUser{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", password='" + password + '\'' +
                '}'
    }
}