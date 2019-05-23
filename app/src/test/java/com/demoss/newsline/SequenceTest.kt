package com.demoss.newsline

import org.junit.Test

class SequenceTest {

    @Test
    fun test() {
        print(loadPage(0).toString())
        print('\n')
        print(loadPage(1).toString())
    }

    fun loadPage(n: Int): List<Int> {
        val seq = generateSequence(1) { it + 1 }
        return seq.drop(n * 5).take(5).toList()
    }
}