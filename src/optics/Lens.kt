package optics

/**
 * Allows to focus on field [F] from data structure [S]
 */
class Lens<S, F>(
    val get: (S) -> F,
    val set: (S, F) -> S
) {
    fun modify(s: S, update: (F) -> F): S {
        return set(s, get(s).let(update))
    }
}

// Composition

infix fun <A, B, C> Lens<A, B>.at(lens: Lens<B, C>): Lens<A, C> {
    return Lens(
        get = { lens.get(this.get(it)) },
        set = { a, c ->
            this.set(a, lens.set(this.get(a), c))
        }
    )
}

// Lens -> ListTraversal

fun <S, F> Lens<S, F>.toListTraversal(): ListTraversal<S, F> {
    return ListTraversal(
        get = { s -> listOf(get(s)) },
        modify = { s, update ->
            set(s, update(get(s)))
        }
    )
}

fun <S, I> Lens<S, List<I>>.each(predicate: (Int, I) -> Boolean): ListTraversal<S, I> {
    return ListTraversal(
        get = { s -> get(s).filterIndexed(predicate) },
        modify = { s, update ->
            set(s, get(s).mapIndexed { index, item ->
                if (predicate(index, item)) update(item) else item
            })
        }
    )
}

infix fun <S, I> Lens<S, List<I>>.at(predicate: (Int) -> Boolean) = each { index, _ -> predicate(index) }
infix fun <S, I> Lens<S, List<I>>.each(predicate: (I) -> Boolean) = each { _, item -> predicate(item) }
fun <S, I> Lens<S, List<I>>.every() = each { _, _ -> true }

infix fun <S, I> Lens<S, List<I>>.at(position: Int) = each { index, _ -> index == position }

