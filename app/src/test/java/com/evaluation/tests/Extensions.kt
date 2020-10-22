package com.evaluation.tests

import io.reactivex.Single
import io.reactivex.observers.TestObserver

fun <T> Single<T>.testAwait(): TestObserver<T> {
    return this.test().also {
        it.awaitTerminalEvent()
    }
}

fun <T> Single<T>.test(testBlock: TestObserver<T>.() -> Unit) {
    this.testAwait().apply {
        testBlock.invoke(this)
    }
}