package eu.wojciechzurek.example

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class HandleServiceImpl : HandleService{
    override fun handle(incoming: Flux<Word>): Flux<WordStatistic> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}