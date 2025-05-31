import kotlin.math.floor

class D19 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val total = lines.first().toInt()
        val table = RoundTable(total)
        table.fillPersons()
        val last = if (part == 1u) {
            table.takeGifts()
        } else {
            return "${table.takeFromFront()}"
        }
        return "${last.idx}"
    }


    class RoundTable(val places: Int) {
        val firstPerson = SeatedPerson(1, 1, null, null)
        fun fillPersons() {
            var prevPerson = firstPerson
            for (idx in 2..places) {
                val person = SeatedPerson(idx, 1, null, prevPerson)
                prevPerson.nextPerson = person
                prevPerson = person
            }
            prevPerson.nextPerson = firstPerson
            firstPerson.prevPerson = prevPerson
        }

        fun takeGifts(): SeatedPerson {
            var person = firstPerson
            while (true) {
                person.gifts += person.nextPerson!!.gifts
                person.nextPerson = person.nextPerson!!.nextPerson
                if (person == person.nextPerson) {
                    break
                }
                person = person.nextPerson!!
            }
            return person
        }

        fun takeFromFront(): Int {
            val middle = floor(places / 2.0).toInt()
            val q1 = ArrayDeque<Int>()
            val q2 = ArrayDeque<Int>()
            for (i in 1..middle) {
                q1.addLast(i)
            }
            for (i in middle + 1..places) {
                q2.addLast(i)
            }
            while (q2.isNotEmpty()) {
                if (q1.isEmpty()) {
                    return q2.removeFirst()
                }
                val v1 = q1.removeFirst()
                q2.removeFirst()
                q2.addLast(v1)
                if (q2.size - q1.size > 1) {
                    q1.addLast(q2.removeFirst())
                }
            }
            return q1.removeFirst()
        }
    }

    data class SeatedPerson(val idx: Int, var gifts: Int, var nextPerson: SeatedPerson?, var prevPerson: SeatedPerson?)
}