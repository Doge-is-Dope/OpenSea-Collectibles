package com.chunchiehliang.openseacollectibles.util.eth

import java.math.BigDecimal


/**
 * Ethereum unit conversion functions.
 **/

fun String.fromWei(unit: EthUnit): BigDecimal {
    return BigDecimal(this).fromWei(unit)
}

fun BigDecimal.fromWei(unit: EthUnit): BigDecimal {
    return divide(unit.getWeiFactor())
}

fun String.toWei(unit: EthUnit): BigDecimal {
    return BigDecimal(this).toWei(unit)
}

fun BigDecimal.toWei(unit: EthUnit): BigDecimal {
    return multiply(unit.getWeiFactor())
}

enum class EthUnit(private val unitName: String, factor: Int) {
    WEI("wei", 0),
    KWEI("kwei", 3),
    MWEI("mwei", 6),
    GWEI("gwei", 9),
    SZABO("szabo", 12),
    FINNEY("finney", 15),
    ETHER("ether", 18),
    KETHER("kether", 21),
    METHER("mether", 24),
    GETHER("gether", 27);

    private val weiFactor: BigDecimal = BigDecimal.TEN.pow(factor)

    fun getWeiFactor(): BigDecimal {
        return weiFactor
    }

    override fun toString(): String {
        return unitName
    }

    companion object {
        fun fromString(name: String?): EthUnit? {
            name?.let {
                for (unit in values()) {
                    if (name.equals(unit.name, ignoreCase = true)) {
                        return unit
                    }
                }
            }
            return null
        }
    }
}
