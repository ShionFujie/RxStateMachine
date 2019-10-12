package com.shionfujie.rx

import com.shionfujie.rx.util.RecordingObserver
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RxStateMachineTest {

    private val reducer: (State, Action) -> Observable<State> = { state, action ->
        when {
            action is INCREMENT -> Observable.just(state + 1)
            action is DECREMENT && state > 0 -> Observable.just(state - 1)
            else -> Observable.empty()
        }
    }

    @Test
    fun stateMachine() {
        val o = RecordingObserver<State>()
        val action = PublishSubject.create<Action>()
        action.stateMachine(0, reducer).subscribe(o)

        assertEquals(0, o.takeNext())

        action.onNext(INCREMENT)
        assertEquals(1, o.takeNext())
        action.onNext(INCREMENT)
        assertEquals(2, o.takeNext())

        action.onNext(DECREMENT)
        assertEquals(1, o.takeNext())
        action.onNext(DECREMENT)
        assertEquals(0, o.takeNext())

        action.onNext(DECREMENT)
        o.assertNoMoreEvents()
    }
}

private sealed class Action
private object INCREMENT:Action()
private object DECREMENT:Action()

private typealias State = Int