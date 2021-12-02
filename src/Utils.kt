import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun <T> invokeOnLines(name: String, block: Sequence<String>.() -> T): T {
    File("src", "$name.txt").bufferedReader().useLines {
        return block(it)
    }
}
