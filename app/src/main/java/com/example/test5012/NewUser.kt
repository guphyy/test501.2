package com.example.test5012

class NewUser(var name: String, var password: String, var identity: String, var email: String) {

    fun newUser(name: String?, password: String?, indentity: String?, email: String) {
        this.name = name!!
        this.password = password!!
        this.identity = identity!!
        this.email = email!!

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

    @JvmName("getIdentity1")
    fun getIdentity(): String? {
        return identity
    }

    @JvmName("setIdentity1")
    fun setIdentity(identity: String?) {
        this.identity = identity!!
    }

    @JvmName("getEmail1")
    fun getEmail(): String? {
        return email
    }

    @JvmName("setEmail1")
    fun setEmail(email: String?) {
        this.email = email!!
    }
    override fun toString(): String {
        return "newUser{" +
                "name='" + name + '\'' +
                ", identity='" + identity + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}'
    }
}