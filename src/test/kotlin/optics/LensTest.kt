package optics

import model.Experience
import optics.laws.LensLaws
import optics.laws.ListTraversalLaws
import optics.laws.OptLensLaws
import org.junit.jupiter.api.Test
import test.model.FullResumeInfo
import test.model.HiddenFieldItem
import test.model.PersonalInfo
import test.model.TestData.resumeInfo

class LensTest {

    @Test
    fun `Lens laws`() {
        val lens = FullResumeInfo.id
        LensLaws.assertAll(lens, resumeInfo, 666, { it * 2 }, { it + 1 })
    }

    @Test
    fun `Lens composes with Lens`() {
        val lens = FullResumeInfo.personalInfo at PersonalInfo.firstName
        LensLaws.assertAll(lens, resumeInfo, "John", String::capitalize, String::reversed)
    }

    @Test
    fun `Lens composes with OptLens`() {
        val optLens = FullResumeInfo.experience at Experience.years
        OptLensLaws.assertAll(optLens, resumeInfo, 100500)
    }

    @Test
    fun `Lens composes with ListTraversal`() {
        val traversal = FullResumeInfo.hiddenFields at ListTraversal.all()
        ListTraversalLaws.assertAll(
            traversal = traversal,
            s = resumeInfo,
            item = HiddenFieldItem(123, "WTF"),
            f = { it.copy(id = it.id * 2) },
            g = { it.copy(id = it.id + 42) }
        )
    }

}