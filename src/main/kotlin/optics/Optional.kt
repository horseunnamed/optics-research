package optics

class Optional<S, F>(
    val get: (S) -> F?,
    val set: (S, F) -> S
)

// Composition

infix fun <A, B, C> Optional<A, B>.at(optional: Optional<B, C>): Optional<A, C> {
    return Optional(
        get = { a ->
            this.get(a)?.let { b ->
                optional.get(b)
            }
        },
        set = { a, c ->
            val b = this.get(a)
            if (b != null && c != null) {
                this.set(a, optional.set(b, c))
            } else {
                a
            }
        }
    )
}

infix fun <A, B, C> Optional<A, B>.at(lens: Lens<B, C>): Optional<A, C> = this at lens.toOptional()
infix fun <A, B, C> Optional<A, B>.at(listTraversal: ListTraversal<B, C>) = this.toListTraversal() at listTraversal

// Optional -> ListTraversal

fun <S, F> Optional<S, F>.toListTraversal(): ListTraversal<S, F> {
    return ListTraversal(
        get = { s ->
            get(s)?.let { listOf(it) } ?: emptyList()
        },
        modify = { s, update ->
            get(s)?.let(update)?.let {
                set(s, it)
            } ?: s
        }
    )
}