package com.demoss.newsline

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import org.junit.Test

class CoroutinePlayground {

    @Test
    fun getDataFromLocalAndRemote() = runBlocking {
        val channelResult = loadData()
        for (i in channelResult) {
            print(i)
        }
//        print(channelResult.receive())
//        print(channelResult.receive())
    }

    suspend fun CoroutineScope.loadData(): ReceiveChannel<Int> =
//    coroutineScope {
//        val channel = Channel<Int>()
//        launch { channel.send(local()) }
//        launch { channel.send(remote()) }
//        channel
//    }
        produce {
            launch(CoroutineExceptionHandler{ ctx, e -> print(e.localizedMessage) }) { send(local(true)) }
            launch(CoroutineExceptionHandler{ ctx, e -> print(e.localizedMessage) }) { send(remote()) }
        }

    suspend fun remote(fail: Boolean = false) = load("server", 1000, fail)

    suspend fun local(fail: Boolean = false) = load("prefs", 500, fail)

    suspend fun handling() = load("fallback", 100, false)

    suspend fun load(name: String, time: Int, shouldFail: Boolean): Int = if (shouldFail)
        throw RuntimeException("$name failed")
    else {
        delay(time.toLong())
        time
    }

    suspend fun <T> ReceiveChannel<T>.forEach(block: (T) -> Unit) {
        for (i in this) {
            block(i)
        }
    }
}