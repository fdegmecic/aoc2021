import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInputAsListOfString(name: String): List<String> = File("src/inputs", "$name.txt").readLines()

fun readInputAsString(name: String): String = File("src/inputs", "$name.txt").readLines().first()

fun readInputToInts(name: String): List<Int> = File("src/inputs", "$name.txt").readLines().map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
