package eu.wojciechzurek.example

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.reactive.StreamEmitter
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration

@Component
@EnableBinding(WordStatisticSource.Source::class)
class WordStatisticSource(source: Source) {

    private val logger = loggerFor(javaClass)
    private val output = source.output()

    fun send(message: Word) = send(MessageBuilder.withPayload(message).build())

    fun send(message: Message<Word>) {
        logger.info("Sending message: {}", message)
        val status = output.send(message)
        logger.info("Sending status: {}", status)
    }

    @StreamEmitter
    @SendTo(Source.OUTPUT)
    fun send(): Flux<Word> {
        return Flux.interval(Duration.ofSeconds(1))
                .map { Word("Hello world") }
    }

    @Component
    interface Source {

        @Output(OUTPUT)
        fun output(): MessageChannel

        companion object {
            const val OUTPUT = "word-statistic-topic-out"
        }
    }
}