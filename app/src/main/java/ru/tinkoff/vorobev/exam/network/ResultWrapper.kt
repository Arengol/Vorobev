package ru.tinkoff.vorobev.exam.network

sealed class ResultWrapper <out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    object NetworkError: ResultWrapper<Nothing>()
}