fun main() {

    fun isUnique(substring: String, markerLength: Int): Boolean {
        val setOfStrings = mutableSetOf<Char>()
        substring.forEach {
            setOfStrings.add(it)
        }
        return setOfStrings.size == markerLength
    }

    fun process(input: List<String>, markerLength: Int): String {
        val startIndex = markerLength - 1
        // although the real input is just one line I want to test all the lines of the test input to be sure I have it right
        return input.map { line ->
            var i = startIndex
            while (i < line.length && !isUnique(line.substring(i - startIndex, i + 1), markerLength)) i++
            i + 1
        }.toString()
    }

    fun part1(input: List<String>): String {
        return process(input, 4)
    }

    fun part2(input: List<String>): String {
        return process(input, 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    check(part1(testInput).also { println("Answer test input part1: $it") } == "[7, 5, 6, 10, 11]")
    println("Answer part1: " + part1(input))

    check(part2(testInput).also { println("Answer test input part2: $it") } == "[19, 23, 23, 29, 26]")
    println("Answer part2: " + part2(input))
}
