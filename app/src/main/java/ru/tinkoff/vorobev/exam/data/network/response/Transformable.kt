package ru.tinkoff.vorobev.exam.data.network.response

fun interface Transformable<T> {
    fun transform(): T
}