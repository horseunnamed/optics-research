package optics.laws

import optics.OptLens
import org.junit.jupiter.api.Assertions

object OptLensLaws {

    fun <S, F> assertAll(optLens: OptLens<S, F>, s: S, f: F) {
        assertGetOptSet(optLens, s)
        assertSetGetOpt(optLens, s, f)
    }

    fun <S, F> assertGetOptSet(optLens: OptLens<S, F>, s: S) {
        Assertions.assertEquals(
            s,
            optLens.get(s)?.let { optLens.set(s, it) } ?: s
        )
    }

    fun <S, F> assertSetGetOpt(optLens: OptLens<S, F>, s: S, f: F?) {
        Assertions.assertEquals(
            f,
            f?.let { optLens.get(optLens.set(s, f)) }
        )
    }

}