fun main() {
    var backFromTheWall = readLine()!!.split(',').map { it }.toTypedArray()
    val returnedWatchman = readLine()!!.toString()  
    // do not touch the lines above
    // write your code here   
    backFromTheWall = backFromTheWall.plus(returnedWatchman)
    println(backFromTheWall.joinToString(", "))
}