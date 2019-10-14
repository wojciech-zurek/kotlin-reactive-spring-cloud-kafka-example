package eu.wojciechzurek.example.service

import eu.wojciechzurek.example.Word
import eu.wojciechzurek.example.WordStatistic
import eu.wojciechzurek.example.messaging.WordSource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxProcessor
import reactor.core.publisher.FluxSink
import java.util.concurrent.CopyOnWriteArrayList

@Service
class WordStatisticSinkServiceImpl(
        private val source: WordSource
) : SinkService<WordStatistic>, ProcessorService<Word, WordStatistic> {

    private val sinks: CopyOnWriteArrayList<FluxSink<WordStatistic>> = CopyOnWriteArrayList()

    override fun add(emitter: FluxProcessor<WordStatistic, WordStatistic>) {
        val sink = emitter.sink()
        sinks.add(sink)
        sink.onCancel { sinks.remove(sink) }
        sink.onDispose { sinks.remove(sink) }
    }

    override fun send(message: WordStatistic) {
        sinks.forEach {
            it.next(message)
        }
    }

    override fun create(payload: String) {
        source.send(Word(payload))
    }

    override fun handle(message: Flux<Word>): Flux<WordStatistic> {
        return message
                .map {
                    WordStatistic(
                            it,
                            it.payload.length,
                            it.payload.toLowerCase().filter { char -> char in it.payload }.groupingBy { char -> char }.eachCount()
                    )
                }
    }

    @Scheduled(fixedRate = 10000)
    fun produce() {
        source.send(Word("Hello world from schedule"))
    }
}