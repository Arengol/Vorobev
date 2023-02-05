package ru.tinkoff.vorobev.exam.network

fun interface Transformable<T> {
    fun transform(): T
}