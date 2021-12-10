import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun <T> invokeOnLines(name: String, block: Sequence<String>.() -> T): T {
    File("src", "$name.txt").bufferedReader().useLines {
        return block(it)
    }
}

fun <T> invokeOnLinesInMemory(name: String, block: List<String>.() -> T): T {
    return block(File("src", "$name.txt").readLines())
}

fun <Input, Data : Pair<(Input) -> Output, Output>, Output> evaluateDay(
    dayNumber: Int,
    prepareInput: (List<String>) -> Input,
    part1Data: Data,
    part2Data: Data
) {
    val filename = "Day$dayNumber"
    listOf(part1Data, part2Data).forEach { (func, test) ->
        check(func(invokeOnLinesInMemory("${filename}_test", prepareInput)) == test)
        println(func(invokeOnLinesInMemory(filename, prepareInput)))
    }
}