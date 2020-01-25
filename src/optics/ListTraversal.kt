package optics

/**
 * Allows to focus on some list of items [I] (selected by [get]) from structure [S]
 */
class ListTraversal<S, I>(
    val get: (S) -> List<I>,
    val modify: (S, (I) -> I) -> S
) {
    fun set(s: S, item: I) = modify(s) { item }
}

// Composition

infix fun <A, B, C> ListTraversal<A, B>.at(traversal: ListTraversal<B, C>): ListTraversal<A, C> {
    return ListTraversal(
        get = { a ->
            this.get(a).flatMap { item ->
                traversal.get(item)
            }
        },
        modify = { a, update ->
            this.modify(a) { b ->
                traversal.modify(b, update)
            }
        }
    )
}

infix fun <A, B, C> ListTraversal<A, B>.at(lens: Lens<B, C>) = this at lens.toListTraversal()
