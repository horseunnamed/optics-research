package optics

class OptLens<S, F>(
    val get: (S) -> F?,
    val set: (S, F) -> S
)

// Composition

infix fun <A, B, C> OptLens<A, B>.at(optLens: OptLens<B, C>): OptLens<A, C> {
    return OptLens(
        get = { a ->
            this.get(a)?.let { b ->
                optLens.get(b)
            }
        },
        set = { a, c ->
            val b = this.get(a)
            if (b != null && c != null) {
                this.set(a, optLens.set(b, c))
            } else {
                a
            }
        }
    )
}

infix fun <A, B, C> OptLens<A, B>.at(lens: Lens<B, C>): OptLens<A, C> = this at lens.toOptional()
infix fun <A, B, C> OptLens<A, B>.at(listTraversal: ListTraversal<B, C>) = this.toListTraversal() at listTraversal

// Conversions

fun <S, F> OptLens<S, F>.toListTraversal(): ListTraversal<S, F> {
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