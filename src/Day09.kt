fun main() {

    val head = 0
    var tail = 1

    var knots = MutableList(tail + 1) { Point(0, 0) }
    var tailPoints = mutableSetOf(knots[tail])

    fun reset() {
        knots = MutableList(tail + 1) { Point(0, 0) }
        tailPoints = mutableSetOf(knots[tail])
    }

    fun moveTailY(knot: Int) = when (knots[knot - 1].y - knots[knot].y) {
        1, 2 -> knots[knot].up()
        -1, -2 -> knots[knot].down()
        else -> knots[knot]
    }

    fun moveTailX(knot: Int) = when (knots[knot - 1].x - knots[knot].x) {
        1, 2 -> knots[knot].right()
        -1, -2 -> knots[knot].left()
        else -> knots[knot]
    }

    fun moveTail(knot: Int): Point {
        var point = when (knots[knot - 1].x - knots[knot].x) {
            2 -> moveTailY(knot).right()
            -2 -> moveTailY(knot).left()
            else -> knots[knot]
        }
        point = when (knots[knot - 1].y - knots[knot].y) {
            2 -> moveTailX(knot).up()
            -2 -> moveTailX(knot).down()
            else -> point
        }
        return point
    }

    fun moveHead(direction: String) = when (direction) {
        "R" -> knots[head].right()
        "L" -> knots[head].left()
        "U" -> knots[head].up()
        "D" -> knots[head].down()
        else -> knots[head]
    }

    fun moveKnots(direction: String, knot: Int) {
        (1..knot).forEach { _ ->
            knots[head] = moveHead(direction)
            (1..tail).forEach {
                knots[it] = moveTail(it)
            }
            tailPoints.add(knots[tail])
        }
    }

    fun part1(input: List<String>): Int {
        tail = 1
        reset()
        input.forEach { line ->
            val move = line.split(" ")
            moveKnots(move[0], move[1].toInt())
        }
        return tailPoints.count()
    }

    fun part2(input: List<String>): Int {
        tail = 9
        reset()
        input.forEach { line ->
            val move = line.split(" ")
            moveKnots(move[0], move[1].toInt())
        }
        return tailPoints.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 13)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 1)
    println("Answer part2: " + part2(input))
}

data class Point(val x: Int, val y: Int) {
    fun left(): Point = Point(this.x - 1, this.y)
    fun right(): Point = Point(this.x + 1, this.y)
    fun up(): Point = Point(this.x, this.y + 1)
    fun down(): Point = Point(this.x, this.y - 1)
}
