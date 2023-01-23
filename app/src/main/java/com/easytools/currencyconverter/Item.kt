package com.easytools.currencyconverter

class Item {
    var currencyCode: String
    var flag: Int = 0

    constructor(code: String, image: Int) {
        currencyCode = code
        flag = image
    }

    fun getCode() = currencyCode
    fun getImage() = flag

}