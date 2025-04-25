package utils

class MutablePair<A, B>(var first: A?, var second: B?) {

    fun isSingle(): Boolean {
        return (first == null && second != null) ||
                (second == null && first != null)
    }

}

fun <A> MutablePair<A?, A?>.contains(a: A, b: A): Boolean {
    return (first == a && second == b) ||
            (first == b && second == a)
}

fun <A, B> mutablePairOf(a: A, b: B): MutablePair<A, B> {
    return MutablePair(a, b)
}

