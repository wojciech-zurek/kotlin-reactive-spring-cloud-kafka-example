package eu.wojciechzurek.example

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ProcessorServiceImpl : ProcessorService {
    override fun handle(incoming: Flux<Word>): Flux<WordStatistic> {
        return incoming.map { WordStatistic(it, it.payload.length) }
    }
}