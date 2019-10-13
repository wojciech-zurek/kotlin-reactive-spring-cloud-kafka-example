package eu.wojciechzurek.example.controller

import eu.wojciechzurek.example.service.SinkService
import eu.wojciechzurek.example.WordStatistic
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
class WordStatisticController(
        private val sinkService: SinkService<WordStatistic>
) {

    @GetMapping("/word-statistic")
    fun get(): Flux<WordStatistic> {

        return Flux.create { sink ->
            sinkService.add(sink)

        }

    }

    @PostMapping("/word-statistic")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun create(@RequestBody request: Request) {
        sinkService.create(request.payload)
    }
}
