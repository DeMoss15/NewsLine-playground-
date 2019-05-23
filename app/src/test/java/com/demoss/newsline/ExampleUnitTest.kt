package com.demoss.newsline

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val myBoolean = true
        var takeIfResult = myBoolean takeOne 10 ?: 20
        myBoolean ifTrue { takeIfResult += 10 } ifFalse { takeIfResult += 20 }
        assertEquals(20, takeIfResult)
    }

    infix fun <T: Any>Boolean.takeOne(then: T): T? = then.takeIf { this }

    infix fun <T> Boolean.returnOneOf(then: Pair<T, T>): T = if (this) then.first else then.second

    infix fun Boolean.ifTrue(then: () -> Unit): Boolean = this.apply { if (this) then() }
    infix fun Boolean.ifFalse(then: () -> Unit): Boolean = this.apply { if (!this) then() }
}
