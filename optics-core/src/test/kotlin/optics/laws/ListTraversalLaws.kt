package optics.laws

import optics.ListTraversal
import org.junit.jupiter.api.Assertions.assertEquals

object ListTraversalLaws {

    fun <S, I> assertAll(traversal: ListTraversal<S, I>, s: S, item: I, f: (I) -> I, g: (I) -> I) {
        assertSetIdempotent(traversal, s, item)
        assertModifyGetAll(traversal, s, f)
        assertModifyIdentity(traversal, s)
        assertComposeModify(traversal, s, f, g)
    }

    fun <S, I> assertSetIdempotent(traversal: ListTraversal<S, I>, s: S, item: I) {
        assertEquals(
            traversal.set(s, item),
            traversal.set(traversal.set(s, item), item)
        )
    }

    fun <S, I> assertModifyGetAll(traversal: ListTraversal<S, I>, s: S, f: (I) -> I) {
        assertEquals(
            traversal.get(s).map(f),
            traversal.get(traversal.modify(s, f))
        )
    }

    fun <S, I> assertModifyIdentity(traversal: ListTraversal<S, I>, s: S) {
        assertEquals(
            s,
            traversal.modify(s) { it }
        )
    }

    fun <S, I> assertComposeModify(traversal: ListTraversal<S, I>, s: S, f: (I) -> I, g: (I) -> I) {
        assertEquals(
            traversal.modify(s) { f(g(it)) },
            traversal.modify(traversal.modify(s, g), f)
        )
    }


}