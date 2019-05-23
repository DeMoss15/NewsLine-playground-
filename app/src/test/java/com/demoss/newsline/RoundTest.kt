package com.demoss.newsline

import org.junit.Test
import kotlin.math.round

class RoundTest {

    @Test
    fun run() {
        val value = -26.6477969789464436445687468797

        print(format(value))
    }

    fun format(x:Double) =
        StringBuilder().append("UAH")
            .append(" ")
            .append(round2(x)).toString()

    fun round2(x: Double): Double =
//        Math.round(x * 100) / 100.0
//        String.format("%.2f", x).toDouble()
        round(x * 100) / 100

}