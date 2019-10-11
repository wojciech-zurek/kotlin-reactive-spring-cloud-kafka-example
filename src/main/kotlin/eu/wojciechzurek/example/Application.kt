package eu.wojciechzurek.example

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

fun <T> loggerFor(clazz: Class<T>): Logger = LoggerFactory.getLogger(clazz)