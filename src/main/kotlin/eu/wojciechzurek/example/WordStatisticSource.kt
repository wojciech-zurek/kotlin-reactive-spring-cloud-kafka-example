package eu.wojciechzurek.example

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.reactive.FluxSender
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
@EnableBinding(WordStatisticSource.Source::class)
class WordStatisticSource(source: Source) {

    private val logger = loggerFor(javaClass)
    private val output = source.output()


    fun send(message: Word) = send(Flux.just(message))

    fun send(message: Flux<Word>) {
        logger.info("Sending message: {}", message)
        val result = output.send(message)

        logger.info("Sending message: {}", result)
    }

    @Component
    interface Source {
        @Output(OUTPUT)
        fun output(): FluxSender

        companion object {
            const val OUTPUT = "word-statistic-topic-out"
        }
    }
}