package optics.laws

import optics.Lens
import org.junit.jupiter.api.Assertions

object LensLaws {

    fun <S, F> assertAll(lens: Lens<S, F>, s: S, f: F) {
        assertGetSet(lens, s)
        assertSetGet(lens, s, f)
    }

    fun <S, F> assertGetSet(lens: Lens<S, F>, s: S) {
        Assertions.assertEquals(s, lens.set(s, lens.get(s)))
    }

    fun <S, F> assertSetGet(lens: Lens<S, F>, s: S, f: F) {
        Assertions.assertEquals(f, lens.get(lens.set(s, f)))
    }

}