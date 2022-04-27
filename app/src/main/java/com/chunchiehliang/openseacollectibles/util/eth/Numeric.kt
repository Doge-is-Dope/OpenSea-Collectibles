package com.chunchiehliang.openseacollectibles.util.eth

import java.math.BigInteger

private const val HEX_PREFIX = "0x"

fun String.decodeQuantity(): BigInteger? {
    if (isLongValue(this)) {
        return BigInteger.valueOf(this.toLong())
    }
    if (!isValidHexQuantity(this)) {
        throw Exception("Value must be in format 0x[1-9]+[0-9]* or 0x0")
    }
    return try {
        BigInteger(this.substring(2), 16)
    } catch (e: NumberFormatException) {
        throw Exception("Negative ", e)
    }
}

private fun isLongValue(value: String): Boolean {
    return try {
        value.toLong()
        true
    } catch (e: java.lang.NumberFormatException) {
        false
    }
}

private fun isValidHexQuantity(value: String?): Boolean {
    if (value == null) {
        return false
    }
    if (value.length < 3) {
        return false
    }
    return value.startsWith(HEX_PREFIX)
}

