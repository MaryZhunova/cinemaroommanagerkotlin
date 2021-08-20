import java.util.*

/**
 * Project: Cinema Room Manager
 * https://hyperskill.org/projects/138
 */

fun main() {
    var rows = 0
    var seats = 0
    while (rows == 0 && seats == 0) {
        try {
            println("Enter the number of rows:")
            rows = readLine()!!.toInt()
            println("Enter the number of seats in each row:")
            seats = readLine()!!.toInt()
            if (rows == 0 && seats == 0) println("Wrong input!")
        } catch (e: NumberFormatException) {
            rows = 0
            seats = 0
            println("Wrong input!")
        }
    }
    var array = createArray(rows, seats)
    if (!array.contentEquals(emptyArray())) {
        options()
        var n = readLine()!!.toInt()
        while (n != 0) {
            when (n) {
                1 -> showTheSeats(rows, seats, array)
                2 -> array = checkSeats(rows, seats, array)
                3 -> statistics(rows, seats, array)
            }
            options()
            n = readLine()!!.toInt()
        }
    }
}

/**
 * Creates an array filled with "S" strings
 *
 * @param rows number of rows in the cinema
 * @param seats number of seats in one row
 * @return Array<Array<String>> array representing the cinema
 */

fun createArray(rows: Int, seats: Int) : Array<Array<String>> {
    if (seats <= 0 || rows <= 0) {
        println("Wrong input!")
        return emptyArray()
    }
    return Array(rows) { Array(seats) { "S" } }
}

/**
 * Outputs the options in the console
 */

fun options() {
    println()
    println("""
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit""".trimIndent()
    )
    println()
}

/**
 * Outputs the cinema representation in the console
 *
 * @param rows number of rows in the cinema
 * @param seats number of seats in one row
 * @param  array represents the cinema with filled/empty seats
 */

fun showTheSeats(rows: Int, seats: Int, array: Array<Array<String>>) {
    println("Cinema:")
    for (r in 0..rows) {
        for (s in 0..seats) {
            if (r == 0) {
                if (s != 0) {
                    print("$s ")
                } else {
                    print("  ")
                }
            } else if (s == 0) {
                print("$r ")
            } else {
                print("${array[r - 1][s - 1]} ")
            }
        }
        println()
    }
}

/**
 * Checks if the input of row and seat is correct
 * and switches "S" string to "B" string in the array cell
 *
 * @param rows number of rows in the cinema
 * @param seats number of seats in one row
 * @param  array represents the cinema with filled/empty seats
 * @return updated array
 */

fun checkSeats(rows: Int, seats: Int, array: Array<Array<String>>): Array<Array<String>> {
    var check = false
    while (!check) {
        val (r, s) = buyATicket()
        try {
            if (array[r][s] != "B") {
                array[r][s] = "B"
                printTicketPrice(rows, seats, r + 1)
                check = true
            } else {
                println("That ticket has already been purchased!")
            }
        } catch (e: NumberFormatException) {
            println("Wrong input!")
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
        }
    }
    return array
}

/**
 * Calculates and outputs current cinema statistics
 *
 * @param rows number of rows in the cinema
 * @param seats number of seats in one row
 * @param  array represents the cinema with filled/empty seats
 */

fun statistics(rows: Int, seats: Int, array: Array<Array<String>>) {
    val totalSeats = rows * seats
    var takenSeats = 0
    var currentIncome = 0
    val totalIncome = if (rows * seats <= 60) {
        10 * totalSeats
    } else {
        rows / 2 * seats * 10 + (rows - rows / 2) * seats * 8
    }
    for (r in array) {
        for (s in r) {
            if (s == "B") {
                takenSeats++
                currentIncome += countTicketPrice(rows, seats, array.indexOf(r) + 1)
            }
        }
    }
    val percentage = String.format(Locale.US,"%.2f", takenSeats * 1.0 / totalSeats * 100)
    println("""
    Number of purchased tickets: $takenSeats
    Percentage: $percentage%
    Current income: $$currentIncome
    Total income: $$totalIncome""".trimIndent())
}

/**
 * Suggests to user to input the row and seat number
 *
 * @return IntArray array of 2 ints representing the row and the seat numbers chosen by the user
 */

fun buyATicket(): IntArray {
    var check = false
    val array = IntArray(2)
    while (!check) {
        try {
            println("Enter a row number:")
            val row = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            val seat = readLine()!!.toInt()
            array[0] = row - 1
            array[1] = seat - 1
            check = true
        } catch (e: NumberFormatException) {
            println("Wrong input!")
        }
    }
    return array
}

/**
 * Calculates and outputs current ticket price
 *
 * @param rows number of rows in the cinema
 * @param seats number of seats in one row
 * @param  row current row
 */

fun printTicketPrice(rows: Int, seats: Int, row: Int) {
    println()
    if (rows * seats <= 60) println("Ticket price: $10")
    else {
        if (row <= rows / 2) println("Ticket price: $10")
        else println("Ticket price: $8")
    }
}

/**
 * Calculates the current ticket price
 *
 * @param rows number of rows in the cinema
 * @param seats number of seats in one row
 * @param  row current row
 * @return ticket price
 */

fun countTicketPrice(rows: Int, seats: Int, row: Int): Int {
    if (rows * seats > 60) {
        if (row > rows / 2) return 8
    }
    return 10
}