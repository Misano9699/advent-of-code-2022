const val LOSS = 0
const val DRAW = 3
const val WIN = 6

fun main() {

    val elvesMap: Map<String, RockPaperScissors> =
        mapOf(Pair("A", RockPaperScissors.ROCK), Pair("B", RockPaperScissors.PAPER), Pair("C", RockPaperScissors.SCISSORS))
    val meMap: Map<String, RockPaperScissors> =
        mapOf(Pair("X", RockPaperScissors.ROCK), Pair("Y", RockPaperScissors.PAPER), Pair("Z", RockPaperScissors.SCISSORS))

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

fun calculateScore(elf: RockPaperScissors, outcome: String): Int = when(outcome) {
    "X" -> lose(elf)
    "Y" -> draw(elf)
    "Z" -> win(elf)
    else -> throw IllegalArgumentException("Ongeldige waarde")
}

fun win(elf: RockPaperScissors): Int = WIN + when (elf)  {
    RockPaperScissors.ROCK -> RockPaperScissors.PAPER.score
    RockPaperScissors.PAPER -> RockPaperScissors.SCISSORS.score
    RockPaperScissors.SCISSORS -> RockPaperScissors.ROCK.score
}

fun draw(elf: RockPaperScissors): Int = DRAW + when (elf) {
    RockPaperScissors.ROCK -> RockPaperScissors.ROCK.score
    RockPaperScissors.PAPER -> RockPaperScissors.PAPER.score
    RockPaperScissors.SCISSORS -> RockPaperScissors.SCISSORS.score
}

fun lose(elf: RockPaperScissors): Int = LOSS +  when (elf)  {
    RockPaperScissors.ROCK -> RockPaperScissors.SCISSORS.score
    RockPaperScissors.PAPER -> RockPaperScissors.ROCK.score
    RockPaperScissors.SCISSORS -> RockPaperScissors.PAPER.score
}

enum class RockPaperScissors(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun score(other: RockPaperScissors): Int = when {
        this == ROCK && other == SCISSORS -> LOSS + other.score
        this == SCISSORS && other == ROCK -> WIN + other.score
        this.ordinal < other.ordinal -> WIN + other.score
        this.ordinal == other.ordinal -> DRAW + other.score
        else -> LOSS + other.score
    }
}
