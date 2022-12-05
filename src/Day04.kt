fun main() {

    fun fullyContains(range1: Pair<Int,Int>, range2: Pair<Int,Int>) =
        range1.first <= range2.first && range1.second >= range2.second || range2.first <= range1.first && range2.second >= range1.second

    fun contains(range1: Pair<Int,Int>, range2: Pair<Int, Int>) =
        range1.first <= range2.second && range1.second >= range2.first

    fun determineRange(elfSections: String): Pair<Int, Int> =
        Pair(elfSections.substringBefore("-").toInt(), elfSections.substringAfter("-").toInt())

    fun part1(input: List<String>): Int {
        return input.map {
            fullyContains(determineRange(it.substringBefore(",")), determineRange(it.substringAfter(",")))
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            contains(determineRange(it.substringBefore(",")), determineRange(it.substringAfter(",")))
        }.count { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    check(part1(testInput).also { println("Answer test input part1: $it") } == 2)
    println("Answer part1: " + part1(input))

    check(part2(testInput).also { println("Answer test input part2: $it") } == 4)
    println("Answer part2: " + part2(input))
}
