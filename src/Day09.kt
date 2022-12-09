fun main() {

    val head = 0
    var tail = 1

    var knots = MutableList(tail + 1) { Point(0,0)}
    var knot = 1
    var tailPoints = mutableSetOf(knots[tail])

    fun reset() {
        knot = 1
        knots = MutableList(tail + 1) { Point(0,0)}
        tailPoints = mutableSetOf(knots[tail])
        println(knot)
    }

    fun moveTailY() {
        when (knots[knot-1].y - knots[knot].y) {
            1, 2 -> knots[knot] = knots[knot].up()
            -1, -2 -> knots[knot] = knots[knot].down()
        }
    }

    fun moveTailX() {
        when (knots[knot-1].x - knots[knot].x) {
            1, 2 -> knots[knot] = knots[knot].right()
            -1, -2  -> knots[knot] = knots[knot].left()
        }
    }

    fun moveTail() {
        when (knots[knot-1].x - knots[knot].x) {
            2 -> {
                moveTailY()
                knots[knot] = knots[knot].right()
            }
            -2 -> {
                moveTailY()
                knots[knot] = knots[knot].left()
            }
        }
        when (knots[knot-1].y - knots[knot].y) {
            2 -> {
                moveTailX()
                knots[knot] = knots[knot].up()
            }
            -2 -> {
                moveTailX()
                knots[knot] = knots[knot].down()
            }
        }
    }

    fun moveHead(direction: String)= when (direction) {
        "R" -> knots[head].right()
        "L" -> knots[head].left()
        "U" -> knots[head].up()
        "D" -> knots[head].down()
        else -> knots[head]
    }

    fun move(direction:String, number: Int) {
        (1 .. number).forEach { _ ->
            knots[head] = moveHead(direction)
            (1..tail).forEach {
                knot = it
                moveTail()
            }
            tailPoints.add(knots[tail])
            println("Head: ${knots[head]}")
            println("Tail: ${knots[tail]}")
        }
    }

    fun part1(input: List<String>): Int {
        tail = 1
        reset()
        input.forEach { line ->
            val move = line.split(" ")
            move(move[0], move[1].toInt())
        }
        return tailPoints.count()
    }

    fun part2(input: List<String>): Int {
        tail = 9
        reset()
        input.forEach { line ->
            val move = line.split(" ")
            move(move[0], move[1].toInt())
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

data class Point(val x : Int, val y : Int) {
    fun left() : Point = Point(this.x-1, this.y)
    fun right() : Point = Point(this.x+1, this.y)
    fun up() : Point = Point(this.x, this.y+1)
    fun down() : Point = Point(this.x, this.y-1)
}
