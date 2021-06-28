package search

import java.io.File

fun main(args: Array<String>) {
    val fileNameIndex = args.indexOf("--data") + 1
    val fileName = args[fileNameIndex]
    val people = File(fileName).readLines().toTypedArray()
    val index = createIndex(people)
    println("\nEnter the number of search queries:")
    var decision = -1
    while (decision != 0) {
        printMenu()
        decision = readLine()!!.toInt()
        when (decision) {
            1 -> findAPerson(people, index)
            2 -> printAllPeople(people)
            0 -> break
            else -> printErrorMessage()
        }
    }
    println("Bye!")
}

fun createIndex(people: Array<String>): Map<String, List<Int>> {
    val index = emptyMap<String, MutableList<Int>>().toMutableMap()
    for (lineNumber in people.indices) {
        val person = people[lineNumber]
        val words = person.toLowerCase().split(" ")
        for (word in words) {
            val lowercaseWord = word.toLowerCase()
            if (index.containsKey(lowercaseWord)) {
                index[lowercaseWord]!!.add(lineNumber)
            } else {
                index[lowercaseWord] = listOf(lineNumber).toMutableList()
            }
        }
    }
    return index
}

fun printMenu() {
    println()
    println("=== Menu ===")
    println("1. Find a person")
    println("2. Print all people")
    println("0. Exit")
}

fun findAPerson(people: Array<String>, index: Map<String, List<Int>>) {
    println("Select a matching strategy: ALL, ANY, NONE")
    val matchingStrategy = readLine()!!.toLowerCase()
    println("Enter a name or email to search all suitable people.")
    println("\nEnter data to search people:")
    val searchQuery = readLine()!!.toLowerCase()
    val foundEntries = findPeople(matchingStrategy, searchQuery, people)
    if (foundEntries.isEmpty()) {
        println("No matching people found.")
    } else {
        println("${foundEntries.size} persons found:")
        println(foundEntries.joinToString("\n"))
    }
}

fun findPeople(matchingStrategy: String, searchQuery: String, people: Array<String>): Set<String> {
    return when (matchingStrategy) {
        "any" -> findPeopleWithAnyMatch(searchQuery, people)
        "all" -> findPeopleWithAllMatch(searchQuery, people)
        "none" -> findPeopleWithNoneMatch(searchQuery, people)
        else -> emptySet()
    }
}

fun findPeopleWithAnyMatch(searchQuery: String, people: Array<String>): Set<String> {
    val searchTerms = searchQuery.split(" ")
    val foundEntries = emptyList<String>().toMutableSet()
    people.forEach { person ->
        if (searchTerms.any { searchTerm -> person.toLowerCase().contains(searchTerm) }) {
            foundEntries.add(person)
        }
    }
    return foundEntries
}

fun findPeopleWithAllMatch(searchQuery: String, people: Array<String>): Set<String> {
    val searchTerms = searchQuery.split(" ")
    val foundEntries = emptyList<String>().toMutableSet()
    people.forEach { person ->
        if (searchTerms.all { searchTerm -> person.toLowerCase().contains(searchTerm) }) {
            foundEntries.add(person)
        }
    }
    return foundEntries
}

fun findPeopleWithNoneMatch(searchQuery: String, people: Array<String>): Set<String> {
    val searchTerms = searchQuery.split(" ")
    val foundEntries = emptyList<String>().toMutableSet()
    people.forEach { person ->
        if (searchTerms.none { searchTerm -> person.toLowerCase().contains(searchTerm) }) {
            foundEntries.add(person)
        }
    }
    return foundEntries
}

fun printAllPeople(people: Array<String>) {
    println("=== List of people ===")
    println(people.joinToString("\n"))
}

fun printErrorMessage() {
    println("Incorrect option! Try again.")
}