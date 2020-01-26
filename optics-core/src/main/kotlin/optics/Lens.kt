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

    companion object {
        fun <S> Id(s: S): Lens<S, S> {
            return Lens(
                get = { s },
                set = { _, updS -> updS }
            )
        }
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

infix fun <A, B, C> Lens<A, B>.at(traversal: ListTraversal<B, C>) = toListTraversal() at traversal

// Lens -> ListTraversal

fun <S, F> Lens<S, F>.toListTraversal(): ListTraversal<S, F> {
    return ListTraversal(
        get = { s -> listOf(get(s)) },
        modify = { s, update ->
            set(s, update(get(s)))
        }
    )
}

// Lens -> Optional

fun <S, F> Lens<S, F>.toOptional(): Optional<S, F> {
    return Optional(
        get = get,
        set = set
    )
}

