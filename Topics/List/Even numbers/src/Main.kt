fun solution(numbers: List<Int>) {
    val evenNumbers = emptyList<Int>().toMutableList()
    for (number in numbers) {
        if (number % 2 == 0) {
            evenNumbers.add(number)
        }
    }
    println(evenNumbers.joinToString(" "))
}
