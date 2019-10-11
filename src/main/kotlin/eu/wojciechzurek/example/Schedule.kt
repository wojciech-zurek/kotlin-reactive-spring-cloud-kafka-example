package eu.wojciechzurek.example

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Schedule(private val source: WordStatisticSource){

	@Scheduled(fixedRate = 5000)
	fun produce(){
		source.send(Word("hello"))
	}
}