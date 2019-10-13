package eu.wojciechzurek.example.service

import reactor.core.publisher.FluxSink

interface SinkService<T> {
    fun add(sink: FluxSink<T>)
    fun send(message: T)
    fun create(payload: String)
}