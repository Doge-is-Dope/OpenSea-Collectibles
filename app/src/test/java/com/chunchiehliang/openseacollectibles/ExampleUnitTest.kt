package com.chunchiehliang.openseacollectibles

import com.chunchiehliang.openseacollectibles.util.eth.EthUnit
import com.chunchiehliang.openseacollectibles.util.eth.decodeQuantity
import com.chunchiehliang.openseacollectibles.util.eth.fromWei
import com.chunchiehliang.openseacollectibles.util.eth.toWei
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testHexToBigInteger() {
        val hex = "0xf37f709fbdd4"
        val balance = hex.decodeQuantity()
        println(balance)
        val ether = balance?.toString()?.fromWei(EthUnit.ETHER)
        println(ether)
    }
}