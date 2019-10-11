package eu.wojciechzurek.example

import eu.wojciechzurek.example.WordStatisticSink.Sink.Companion.INPUT
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.SubscribableChannel
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
@EnableBinding(WordStatisticSink.Sink::class)
class WordStatisticSink(
) {
    private val logger = loggerFor(javaClass)

    @StreamListener(INPUT)
    fun consume(message: Flux<Word>) {

        message.subscribe {
            logger.info("Incoming message: {}", it)
        }
    }

    @Component
    interface Sink {

        @Input(INPUT)
        fun input(): SubscribableChannel

        companion object {
            const val INPUT = "word-statistic-topic-in"
        }
    }
}
