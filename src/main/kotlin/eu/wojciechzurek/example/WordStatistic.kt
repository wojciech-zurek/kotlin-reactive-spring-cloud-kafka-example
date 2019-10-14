package eu.wojciechzurek.example

data class WordStatistic(
        val word: Word,
        val length: Int,
        val stats: Map<Char, Int>
)