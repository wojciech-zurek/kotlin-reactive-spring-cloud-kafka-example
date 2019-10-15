package eu.wojciechzurek.example.messaging

import eu.wojciechzurek.example.service.ProcessorService
import eu.wojciechzurek.example.Word
import eu.wojciechzurek.example.WordStatistic
import eu.wojciechzurek.example.loggerFor
import eu.wojciechzurek.example.messaging.WordStatisticProcessor.Processor.Companion.INPUT
import eu.wojciechzurek.example.messaging.WordStatisticProcessor.Processor.Companion.OUTPUT
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
@EnableBinding(WordStatisticProcessor.Processor::class)
class WordStatisticProcessor(
        private val processorService: ProcessorService<Word, WordStatistic>
) {

    private val logger = loggerFor(javaClass)

    @StreamListener(INPUT)
    @Output(OUTPUT)
    fun process(message: Flux<Word>): Flux<WordStatistic> {
        logger.info("Incoming message: {}", message)
        return processorService.handle(message)
    }


    @Component
    interface Processor {

        @Input(INPUT)
        fun input(): SubscribableChannel

        @Output(OUTPUT)
        fun output(): MessageChannel

        companion object {
            const val INPUT = "word-topic-in"
            const val OUTPUT = "word-statistic-topic-out"
        }
    }
}