package eu.wojciechzurek.example.service

import eu.wojciechzurek.example.Word
import eu.wojciechzurek.example.WordStatistic
import eu.wojciechzurek.example.messaging.WordSource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Service
class WordStatisticSinkServiceImpl(
        private val source: WordSource
) : SinkService<WordStatistic>, ProcessorService<Word, WordStatistic> {

    private val subscribers: MutableList<FluxSink<WordStatistic>> = mutableListOf()

    override fun add(sink: FluxSink<WordStatistic>) {
        subscribers.add(sink)
    }

    override fun send(message: WordStatistic) {
        subscribers.forEach {
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
                            it.payload.toLowerCase().split("").groupingBy { char -> char }.eachCount()
                    )
                }
    }

    @Scheduled(fixedRate = 5000)
    fun produce(){
        source.send(Word("Hello world from schedule"))
    }
}