package eu.wojciechzurek.example.messaging

import eu.wojciechzurek.example.service.SinkService
import eu.wojciechzurek.example.WordStatistic
import eu.wojciechzurek.example.loggerFor
import eu.wojciechzurek.example.messaging.WordStatisticSink.Sink.Companion.INPUT
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.SubscribableChannel
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
@EnableBinding(WordStatisticSink.Sink::class)
class WordStatisticSink(
        private val sinkService: SinkService<WordStatistic>
) {
    private val logger = loggerFor(javaClass)

    @StreamListener(INPUT)
    fun consume(message: Flux<WordStatistic>) {

        message.subscribe {
            logger.info("Incoming message: {}", it)
            sinkService.send(it)
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
