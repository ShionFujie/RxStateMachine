package com.shionfujie.rxstatemachine

import io.reactivex.Observable

fun <S, A> Observable<A>.stateMachine(initialState: S, reducer: Reducer<S, A>): Observable<S> {
    val alphabet = reducer.lift()
    val initial = Observable.just(initialState)

    return this.map(alphabet)
            .scan(initial) { m, f -> f(m) }
            .flatMap { it }
}

private typealias Reducer<S, A> = (S, A) -> Observable<S>

private fun <S, A> Reducer<S, A>.lift(): (A) -> ((Observable<S>) -> Observable<S>) =
    { action ->
        { stateStream ->
            stateStream.takeLast(1)
                    .flatMap { state -> this(state, action) }
                    .cache()
        }
    }