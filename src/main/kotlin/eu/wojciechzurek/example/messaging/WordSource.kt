package eu.wojciechzurek.example.messaging

import eu.wojciechzurek.example.Word
import eu.wojciechzurek.example.loggerFor
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.reactive.StreamEmitter
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration

@Component
@EnableBinding(WordSource.Source::class)
class WordSource(source: Source) {

    private val logger = loggerFor(javaClass)
    private val output = source.output()

    fun send(message: Word) = send(MessageBuilder.withPayload(message).build())

    fun send(message: Message<Word>) {
        logger.info("Sending message: {}", message)
        val status = output.send(message)
        logger.info("Sending status: {}", status)
    }

    @StreamEmitter
    @Output(Source.OUTPUT)
    fun send(): Flux<Word> {
        return Flux.interval(Duration.ofSeconds(5))
                .map { Word("Hello world from stream emitter") }
    }

    @Component
    interface Source {

        @Output(OUTPUT)
        fun output(): MessageChannel

        companion object {
            const val OUTPUT = "word-topic-out"
        }
    }
}