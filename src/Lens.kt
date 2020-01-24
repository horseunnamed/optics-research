class Lens<A, B>(
    val get: (A) -> B,
    val set: (A, B) -> A
) {
    companion object
}

infix fun <A, B, C> Lens<A, B>.at(lens: Lens<B, C>): Lens<A, C> {
    return Lens(
        get = { lens.get(this.get(it)) },
        set = { a, c ->
            this.set(a, lens.set(this.get(a), c))
        }
    )
}

infix fun <A, B> Lens<A, List<B>>.at(position: Int): Lens<A, B> {
    return Lens(
        get = { this.get(it)[position] },
        set = { a, b ->
            this.set(a, this.get(a).mapIndexed { index, item ->
                if (index == position) b else item
            })
        }
    )
}

// ???
fun <A, B> Lens<A, List<B>>.each(): Lens<A, B> {
    TODO()
}


// DSL

object LensDSL {
    fun <A, B> A.at(lens: Lens<A, B>) = this to lens
    fun <A, B, C> Pair<A, Lens<A, B>>.at(lens: Lens<B, C>) = this.first to (this.second at lens)
    fun <A, B> Pair<A, Lens<A, List<B>>>.at(position: Int) = this.first to (this.second at position)
    fun <A, B> Pair<A, Lens<A, List<B>>>.each() = this.first to this.second.each()
    fun <A, B> Pair<A, Lens<A, B>>.set(value: B) = this.second.set(this.first, value)
}
