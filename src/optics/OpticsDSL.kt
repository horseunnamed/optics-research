package optics

object OpticsDSL {
    fun <A, B> A.at(lens: Lens<A, B>) = this to lens
    fun <A, B, C> Pair<A, Lens<A, B>>.at(lens: Lens<B, C>) = this.first to (this.second at lens)
    fun <A, B> Pair<A, Lens<A, List<B>>>.at(position: Int) = this.first to (this.second at position)
    fun <A, B> Pair<A, Lens<A, List<B>>>.every() = this.first to this.second.every()
    fun <A, B> Pair<A, Lens<A, B>>.set(value: B) = this.second.set(this.first, value)
}
