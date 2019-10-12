package com.shionfujie.rx.util

import io.reactivex.observers.DisposableObserver
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

class RecordingObserver<T>:DisposableObserver<T>() {

    private val events = LinkedBlockingDeque<Any>()

    override fun onComplete() {
        events.addLast(OnCompleted())
    }

    override fun onError(e: Throwable) {
        events.addLast(OnError(e))
    }

    override fun onNext(t: T) {
        events.addLast(OnNext(t))
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E> takeEvent(wanted: Class<*>): E {
        val event: Any?
        try {
            event = events.pollFirst(1, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }

        if (event == null) {
            throw NoSuchElementException("No event found while waiting for " + wanted.simpleName)
        }
        assertTrue(wanted.isInstance(event))
        return wanted.cast(event) as E
    }

    fun takeNext(): T {
        val event = takeEvent<OnNext>(OnNext::class.java)
        return event.value
    }

    fun takeError(): Throwable {
        return takeEvent<OnError>(OnError::class.java).throwable
    }

    fun clearEvents() {
        while (events.peek() is RecordingObserver<*>.OnNext) {
            events.removeFirst()
        }
    }

    fun assertOnCompleted() {
        takeEvent<OnCompleted>(OnCompleted::class.java)
    }

    fun assertNoMoreEvents() {
        try {
            val event = takeEvent<Any>(Any::class.java)
            throw IllegalStateException("Expected no more events but got $event")
        } catch (ignored: NoSuchElementException) {
        }
    }

    private inner class OnNext constructor(internal val value: T) {
        override fun toString(): String {
            return "OnNext[$value]"
        }
    }

    private inner class OnCompleted {
        override fun toString(): String {
            return "OnCompleted"
        }
    }

    private inner class OnError constructor(val throwable: Throwable) {
        override fun toString(): String {
            return "OnError[$throwable]"
        }
    }
}