package eu.wojciechzurek.example.service

import reactor.core.publisher.FluxProcessor

interface SinkService<T> {
    fun add(emitter: FluxProcessor<T, T>)
    fun send(message: T)
    fun create(payload: String)
}