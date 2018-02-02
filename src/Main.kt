import java.io.File
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    val path = args[0]
    val generalQueue = LinkedList<Long>()
    val queues = HashMap<String, LinkedList<Long>>()
    var maxForAllExchanges = 0
    var maxForAllExchangesTime = 0L
    val maxForEachExchange = HashMap<String, Pair<Int, Long>>()
    val oneSecond = TimeUnit.SECONDS.toNanos(1)

    File(path).useLines {
        val withoutHeader = it.drop(1)
        withoutHeader.forEach {
            val (timeString, _, _, exchange) = it.split(",")
            val time = LocalTime.parse(timeString).toNanoOfDay()
            if (generalQueue.isEmpty() || time - generalQueue.peek() < oneSecond) {
                generalQueue.add(time)
            } else {
                if (maxForAllExchanges < generalQueue.size) {
                    maxForAllExchanges = generalQueue.size
                    maxForAllExchangesTime = generalQueue.pop()
                }
                while (generalQueue.isNotEmpty() && time - generalQueue.peek() >= oneSecond) {
                    generalQueue.pop()
                }
                generalQueue.add(time)
            }

            val currentQueue = queues.getOrPut(exchange) { LinkedList() }
            if (currentQueue.isEmpty() || time - currentQueue.peek() < oneSecond) {
                currentQueue.add(time)
            } else {
                val currentMax = maxForEachExchange[exchange]
                if (currentMax == null || currentMax.first < currentQueue.size) {
                    maxForEachExchange[exchange] = currentQueue.size to currentQueue.pop()
                }
                while (currentQueue.isNotEmpty() && time - currentQueue.peek() >= oneSecond) {
                    currentQueue.pop()
                }
                currentQueue.add(time)
            }
        }
    }

    if (maxForAllExchanges < generalQueue.size) {
        maxForAllExchanges = generalQueue.size
        maxForAllExchangesTime = generalQueue.pop()
    }

    queues.forEach { (exchange, queue) ->
        val currentMax = maxForEachExchange[exchange]
        if (currentMax == null || currentMax.first < queue.size) {
            maxForEachExchange[exchange] = queue.size to queue.pop()
        }
    }

    println("Total max: $maxForAllExchanges at ${LocalTime.ofNanoOfDay(maxForAllExchangesTime)} - "
            + "${LocalTime.ofNanoOfDay(maxForAllExchangesTime + oneSecond)}")
    maxForEachExchange.forEach {
        println("Max for exchange ${it.key}: ${it.value.first} at ${LocalTime.ofNanoOfDay(it.value.second)} - "
                + "${LocalTime.ofNanoOfDay(it.value.second + oneSecond)}")
    }

    //Uncomment the following lines to get the output in russian

    println("Максимальное количество сделок в течение одной секунды было между " +
            "${LocalTime.ofNanoOfDay(maxForAllExchangesTime)} "
            + "и ${LocalTime.ofNanoOfDay(maxForAllExchangesTime + oneSecond)}. " +
            "В этот интервал прошло $maxForAllExchanges сделок.")
    maxForEachExchange.forEach {
        println("Максимальное количество сделок на ${it.key} в течение одной секунды было между " +
                "${LocalTime.ofNanoOfDay(it.value.second)} "
                + "и ${LocalTime.ofNanoOfDay(it.value.second + oneSecond)}. " +
                "В этот интервал прошло ${it.value.first} сделок.")
    }
}
