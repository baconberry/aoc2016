import utils.MutablePair
import utils.contains
import utils.getIntList
import utils.mutablePairOf

typealias Microchip = Int
typealias PMicrochip = MutablePair<Microchip?, Microchip?>
typealias POut = Pair<D10.Output, Int>

class D10 : Solver {
    val bots = mutableMapOf<Int, Bot>()
    val bins = mutableMapOf<Int, Microchip>()

    override fun solve(lines: Array<String>, part: UInt): String {

        lines
            .filter { it.isNotBlank() }
            .forEach { processLine(it) }
        val bot = triggerBots(part == 1u)
        if (part == 2u) {
            val a = requireNotNull(bins[0])
            val b = requireNotNull(bins[1])
            val c = requireNotNull(bins[2])
            val result = a * b * c
            return "$result"
        }
        val result = bots.entries
            .first { it.value == bot }

        return "$result"
    }

    private fun triggerBots(stopAtFilter: Boolean): Bot? {
        val q = ArrayDeque<Bot>()
        bots.values
            .filter { it.isFull() }
            .forEach { q.add(it) }
        while (q.isNotEmpty()) {
            val bot = q.removeFirst()
            if (stopAtFilter && bot.values.contains(61, 17)) {
                return bot
            }
            val bots = bot.flush(bots, bins)
            bots
                .filter { it.isFull() }
                .forEach { q.add(it) }
        }
        return null
    }

    val numberRegex = """\d+""".toRegex()
    private fun processLine(line: String) {
        val nums = getIntList(line);
        if (line.startsWith("value")) {
            val bot = bots.getOrDefault(nums[1], Bot(null, null, mutablePairOf(null, null)))
            bot.acceptValue(nums[0])
            bots.put(nums[1], bot)
            return
        }
        val bot = bots.getOrDefault(nums[0], Bot(null, null, mutablePairOf(null, null)))

        val parts = line.split(numberRegex)
        val low = parts[1].lastWord().toD10Output() to nums[1]
        val high = parts[2].lastWord().toD10Output() to nums[2]
        bots.put(nums[0], Bot(low, high, bot.values))
    }

    enum class Output {
        BOT, BIN
    }

    class Bot(val minOutput: POut?, val maxOutput: POut?, val values: PMicrochip) {

        fun acceptValue(value: Microchip) {
            if (values.first == null) {
                values.first = value
            } else {
                values.second = value
            }
        }

        fun isFull(): Boolean {
            return values.first != null && values.second != null
        }

        fun flush(bots: MutableMap<Int, D10.Bot>, bins: MutableMap<Int, Microchip>): Set<Bot> {
            check(minOutput != null && maxOutput != null)
            val set = mutableSetOf<Bot>()
            when (minOutput.first) {
                Output.BIN -> bins[minOutput.second] = values.takeMin()
                Output.BOT -> {
                    val bot = bots[minOutput.second]
                    if (bot != null) {
                        bot.acceptValue(values.takeMin())
                        set.add(bot)
                    }
                }
            }
            when (maxOutput.first) {
                Output.BIN -> bins[maxOutput.second] = values.takeMin()
                Output.BOT -> {
                    val bot = bots[maxOutput.second]
                    if (bot != null) {
                        bot.acceptValue(values.takeMax())
                        set.add(bot)
                    }
                }
            }
            return set
        }

    }
}

private fun PMicrochip.takeMax(): Int {
    if (this.isSingle()) {
        return this.takeSingle()
    }
    check(first != null && second != null)
    val a = first!!
    val b = second!!
    if (a > b) {
        first = null
        return a
    }
    second = null
    return b
}

private fun PMicrochip.takeSingle(): Int {
    check(this.isSingle())
    val a = first
    val b = second
    if (a != null) {
        first = null
        return a
    }
    checkNotNull(b)
    second = null
    return b
}

private fun PMicrochip.takeMin(): Int {
    if (this.isSingle()) {
        return this.takeSingle()
    }
    check(first != null && second != null)
    val a = first!!
    val b = second!!
    if (a < b) {
        first = null
        return a
    }
    second = null
    return b
}


fun CharSequence.toD10Output(): D10.Output {
    val lower = this.toString().lowercase()
    return when (lower) {
        "output" -> D10.Output.BIN
        "bot" -> D10.Output.BOT
        else -> throw IllegalArgumentException("Invalid output $lower")
    }
}

fun CharSequence.lastWord(): CharSequence {
    var started = false
    var i = this.length - 1
    val sb = StringBuilder()
    while (true) {
        if (started && this[i] == ' ') {
            break
        }
        if (!started && this[i] == ' ') {
            i--
            continue
        }
        started = true
        sb.append(this[i])
        i--
    }
    return sb.toString().reversed()
}