package eu.wojciechzurek.example

import reactor.core.publisher.Flux

interface HandleService{
    fun handle(incoming: Flux<Word>): Flux<WordStatistic>
}