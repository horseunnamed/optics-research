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