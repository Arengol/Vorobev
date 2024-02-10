package ru.tinkoff.vorobev.exam.network.response

fun interface Transformable<T> {
    fun transform(): T
}