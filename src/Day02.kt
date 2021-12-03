fun main() {

    fun preprocessInput(lines: Sequence<String>): Sequence<Pair<String, Int>> {
        return lines.map { it.split(' ').let { words -> words[0] to words[1].toInt() } }
    }

    fun part1(lines: Sequence<String>): Int {
        var pos = 0
        var depth = 0
        preprocessInput(lines).forEach { (direction, num) ->
            when (direction) {
                "forward" -> pos += num
                "down" -> depth += num
                "up" -> depth -= num
            }
        }
        return pos * depth
    }

    fun part2(lines: Sequence<String>): Int {
        var pos = 0
        var aim = 0
        var depth = 0
        preprocessInput(lines).forEach { (direction, num) ->
            when (direction) {
                "forward" -> {
                    pos += num
                    depth += aim * num
                }
                "down" -> aim += num
                "up" -> aim -= num
            }
        }
        return pos * depth
    }

    println(invokeOnLines("Day02", ::part1))
    println(invokeOnLines("Day02", ::part2))
}
