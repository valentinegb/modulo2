import java.io.File

fun main() {
    print("Enter modulo2.jpg path: ")

    val file = File(readLine()!!)

    file.writeBytes(
        file.readBytes().filterIndexed { index, _ ->
            index.mod(2) == 0
        }.toByteArray()
    )

    println("Done")
}