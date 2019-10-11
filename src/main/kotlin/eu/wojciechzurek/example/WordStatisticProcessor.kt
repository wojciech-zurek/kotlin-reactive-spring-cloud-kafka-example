package eu.wojciechzurek.example

import eu.wojciechzurek.example.WordStatisticProcessor.Sink.Companion.INPUT
import eu.wojciechzurek.example.WordStatisticProcessor.Sink.Companion.OUTPUT
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.reactive.StreamEmitter
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
@EnableBinding(WordStatisticProcessor.Sink::class)
class WordStatisticProcessor(private val handleService: HandleService) {

    private val logger = loggerFor(javaClass)

    @StreamListener(INPUT)
    @StreamEmitter
    @Output(OUTPUT)
    fun process(message: Flux<Word>): Flux<WordStatistic> {
        logger.info("Incoming message: {}", message)
        return handleService.handle(message)
    }


    @Component
    interface Sink {

        @Input(INPUT)
        fun input(): SubscribableChannel

        @Output(OUTPUT)
        fun output(): MessageChannel

        companion object {
            const val INPUT = "word-statistic-topic-in"
            const val OUTPUT = "word-statistic-topic-out"
        }
    }
}