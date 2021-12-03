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
