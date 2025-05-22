import kotlin.math.floor

class D19 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val total = lines.first().toInt()
        val table = RoundTable(total)
        table.fillPersons()
        val last = if(part==1u){
            table.takeGifts()
        }else{
            table.takeFromFront()
        }
        return "${last.idx}"
    }


    class RoundTable(val places: Int) {
        val firstPerson = SeatedPerson(1, 1, null, null)
        var currentCount = places
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

        fun takeFromFront(): SeatedPerson {
            var person = firstPerson
            while (true) {
                val inFront = personInFront(person)
                person.gifts += inFront.gifts
                inFront.prevPerson!!.nextPerson = inFront.nextPerson
                if (person.nextPerson == inFront) {
                    person.nextPerson = inFront.nextPerson
                }
                currentCount--
                if (currentCount == 1) {
                    return person
                }
                person = person.nextPerson!!
            }
        }

        private fun personInFront(person: SeatedPerson): SeatedPerson {
            val middle = floor(currentCount / 2.0).toInt()
            var result = person
            repeat(middle) {
                result = result.nextPerson!!
            }
            return result
        }
    }

    data class SeatedPerson(val idx: Int, var gifts: Int, var nextPerson: SeatedPerson?, var prevPerson: SeatedPerson?)
}