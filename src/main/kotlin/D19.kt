class D19 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val total = lines.first().toInt()
        val table = RoundTable(total)
        table.fillPersons()
        val last = table.takeGifts()
        return "${last.idx}"
    }


    class RoundTable(val places: Int) {
        val firstPerson = SeatedPerson(1, 1, null)
        fun fillPersons() {
            var prevPerson = firstPerson
            for (idx in 2..places) {
                val person = SeatedPerson(idx, 1, null)
                prevPerson.nextPerson = person
                prevPerson = person
            }
            prevPerson.nextPerson = firstPerson
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
    }

    data class SeatedPerson(val idx: Int, var gifts: Int, var nextPerson: SeatedPerson?)
}