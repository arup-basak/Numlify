package com.arup.numlify

class History internal constructor(val value: String, val ans: String, val time: String) {
    override fun toString(): String {
        return value + ans + time
    }
}