class Day14 : Evaluator<Pair<String, Map<String, String>>>(part1Test = 1588L, part2Test = 2188189693529L) {

    override fun prepareInput(lines: List<String>): Pair<String, Map<String, String>> {
        val mapping = lines
            .drop(2)
            .map { line -> line.split(" -> ").let { it[0] to it[1] } }
            .toMap()
        return lines.first() to mapping
    }

    private fun <K> incrementBy(hashMap: HashMap<K, Long>, key: K, value: Long): HashMap<K, Long> {
        hashMap[key] = (hashMap[key] ?: 0) + value
        return hashMap
    }

    private fun union(a: HashMap<Char, Long>, b: HashMap<Char, Long>): HashMap<Char, Long> {
        val res = a.toMutableMap() as HashMap
        b.forEach { (key, value) -> incrementBy(res, key, value) }
        return res
    }

    private fun dfs(
        depth: Int,
        charA: Char,
        charB: Char,
        mapping: Map<String, String>,
        cache: HashMap<String, HashMap<Char, Long>>
    ): HashMap<Char, Long> {
        if (depth == 0) return hashMapOf()
        val st = "$charA$charB"
        if ("$depth$st" !in cache) {
            val found = mapping[st]?.first()
            cache["$depth$st"] = if (found != null) {
                val left = dfs(depth - 1, charA, found, mapping, cache)
                val right = dfs(depth - 1, found, charB, mapping, cache)
                incrementBy(union(left, right), found, 1)
            } else {
                hashMapOf()
            }
        }
        return cache["$depth$st"]!!
    }

    private fun countSteps(depth: Int, data: Pair<String, Map<String, String>>): Long {
        val (polymer, mapping) = data
        val cache = HashMap<String, HashMap<Char, Long>>()
        var resMap = hashMapOf<Char, Long>()
        polymer.forEach { incrementBy(resMap, it, 1) }
        polymer.windowed(2) {
            resMap = union(resMap, dfs(depth, it[0], it[1], mapping, cache))
        }

        val counts = resMap.values.filter { it != 0L }.sorted()
        return counts.last() - counts.first().toLong()
    }

    override fun part1(data: Pair<String, Map<String, String>>): Long {
        return countSteps(10, data)
    }

    override fun part2(data: Pair<String, Map<String, String>>): Long {
        return countSteps(40, data)
    }
}

fun main() {
    Day14().evalutate()
}
  
