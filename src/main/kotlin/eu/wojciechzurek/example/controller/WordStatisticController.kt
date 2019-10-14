package eu.wojciechzurek.example.controller

import eu.wojciechzurek.example.service.SinkService
import eu.wojciechzurek.example.WordStatistic
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux

@RestController
class WordStatisticController(
        private val sinkService: SinkService<WordStatistic>
) {

    private val emitter = EmitterProcessor.create<WordStatistic>().serialize().also {
        sinkService.add(it)
    }

    @GetMapping("/word-statistic")
    fun get(): Flux<ServerSentEvent<*>> = emitter.publish().autoConnect().map {
        ServerSentEvent.builder(it).build()
    }

    @PostMapping("/word-statistic")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun create(@RequestBody request: Request) = sinkService.create(request.payload)
}
