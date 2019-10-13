package eu.wojciechzurek.example.service

import reactor.core.publisher.Flux

interface ProcessorService<T, X>{
    fun handle(message: Flux<T>): Flux<X>
}