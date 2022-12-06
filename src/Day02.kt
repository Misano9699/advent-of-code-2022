const val LOSS = 0
const val DRAW = 3
const val WIN = 6

fun main() {

    val elvesMap: Map<String, RockPaperScissors> = mapOf("A" to RockPaperScissors.ROCK, "B" to RockPaperScissors.PAPER, "C" to RockPaperScissors.SCISSORS)

    val meMap: Map<String, RockPaperScissors> = mapOf("X" to RockPaperScissors.ROCK, "Y" to RockPaperScissors.PAPER, "Z" to RockPaperScissors.SCISSORS)

    val winMap: Map<RockPaperScissors, RockPaperScissors> = mapOf(
        RockPaperScissors.ROCK to RockPaperScissors.PAPER,
        RockPaperScissors.PAPER to RockPaperScissors.SCISSORS,
        RockPaperScissors.SCISSORS to RockPaperScissors.ROCK
    )

    val lossMap: Map<RockPaperScissors, RockPaperScissors> = mapOf(
        RockPaperScissors.ROCK to RockPaperScissors.SCISSORS,
        RockPaperScissors.PAPER to RockPaperScissors.ROCK,
        RockPaperScissors.SCISSORS to RockPaperScissors.PAPER
    )

    fun calculateScore(elf: RockPaperScissors, outcome: String): Int = when (outcome) {
        "X" -> elf.score(lossMap[elf]!!)
        "Y" -> elf.score(elf)
        "Z" -> elf.score(winMap[elf]!!)
        else -> throw IllegalArgumentException("Ongeldige waarde")
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val split = it.split(" ")
            elvesMap[split[0]]!!.score(meMap[split[1]]!!)
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val split = it.split(" ")
            calculateScore(elvesMap[split[0]]!!, split[1])
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    check(part1(testInput).also { println("Answer test input part1: $it") } == 15)
    println("Answer part1: " + part1(input))

    check(part2(testInput).also { println("Answer test input part2: $it") } == 12)
    println("Answer part2: " + part2(input))
}

enum class RockPaperScissors(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun score(other: RockPaperScissors): Int = when {
        this.ordinal == other.ordinal -> DRAW + other.score
        this == ROCK && other == SCISSORS -> LOSS + other.score
        this == SCISSORS && other == ROCK -> WIN + other.score
        this.ordinal < other.ordinal -> WIN + other.score
        else -> LOSS + other.score
    }
}
