fun main() {

    fun Sequence<String>.toInt() = map(String::toInt)

    fun Sequence<Int>.countIncreasing(): Int {
        var prev = Int.MIN_VALUE
        return count { (prev < it).apply { prev = it } } - 1
    }

    fun part1(lines: Sequence<String>): Int = lines.toInt().countIncreasing()

    fun part2(lines: Sequence<String>): Int {
        return lines.toInt()
            .windowed(3).map(List<Int>::sum)
            .countIncreasing()
    }

    println(invokeOnLines("Day01", ::part1))
    println(invokeOnLines("Day01", ::part2))
}
