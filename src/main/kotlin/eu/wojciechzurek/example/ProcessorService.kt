package eu.wojciechzurek.example

import reactor.core.publisher.Flux

interface ProcessorService{
    fun handle(incoming: Flux<Word>): Flux<WordStatistic>
}