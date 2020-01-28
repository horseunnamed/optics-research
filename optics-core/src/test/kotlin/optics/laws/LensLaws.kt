package optics.laws

import optics.Lens
import org.junit.jupiter.api.Assertions.assertEquals

object LensLaws {

    fun <S, F> assertAll(lens: Lens<S, F>, s: S, f: F, g: (F) -> F, h: (F) -> F) {
        assertGetSet(lens, s)
        assertSetGet(lens, s, f)
        assertSetIdempotent(lens, s, f)
        assertModifyIdentity(lens, s)
        assertComposeModify(lens, s, g, h)
        assertConsistentSetModify(lens, s, f)
    }

    private fun <S, F> assertGetSet(lens: Lens<S, F>, s: S) {
        assertEquals(s, lens.set(s, lens.get(s)))
    }

    private fun <S, F> assertSetGet(lens: Lens<S, F>, s: S, f: F) {
        assertEquals(f, lens.get(lens.set(s, f)))
    }

    private fun <S, F> assertSetIdempotent(lens: Lens<S, F>, s: S, f: F) {
        assertEquals(
            lens.set(s, f),
            lens.set(lens.set(s, f), f)
        )
    }

    private fun <S, F> assertModifyIdentity(lens: Lens<S, F>, s: S) {
        assertEquals(s, lens.modify(s) { it })
    }

    private fun <S, F> assertComposeModify(lens: Lens<S, F>, s: S, g: (F) -> F, h: (F) -> F) {
        assertEquals(
            lens.modify(lens.modify(s, h), g),
            lens.modify(s, { g(h(it)) })
        )
    }

    private fun <S, F> assertConsistentSetModify(lens: Lens<S, F>, s: S, f: F) {
        assertEquals(
            lens.set(s, f),
            lens.modify(s) { f }
        )
    }

}