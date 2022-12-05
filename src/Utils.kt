import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <T> List<T>.splitIntoSublists(delimiter : T) : List<List<T>> {
    val result = mutableListOf<List<T>>()
    var current = mutableListOf<T>()
    this.forEach {
        if (it == delimiter) {
            result.add(current)
            current = mutableListOf()
        } else {
            current.add(it)
        }
    }
    if (current.isNotEmpty()) {
        result.add(current)
    }
    return result
}

fun <T> List<T>.splitIntoNumberOfLists(number : Int) : List<List<T>> {
    val result = mutableListOf<List<T>>()
    var current = mutableListOf<T>()
    var counter = 0
    this.forEach {
        if (counter == number) {
            result.add(current)
            current = mutableListOf()
            counter = 0
        }
        current.add(it)
        counter++
    }
    if (current.isNotEmpty()) {
        result.add(current)
    }
    return result
}