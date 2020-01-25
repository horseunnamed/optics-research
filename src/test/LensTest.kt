package test

import optics.at
import org.junit.jupiter.api.Test
import test.TestData.resumeInfo
import test.model.FullResumeInfo
import test.model.HiddenFieldItem
import test.model.PersonalInfo
import kotlin.test.assertEquals

class LensTest {

    private val isEven = { num: Int -> num % 2 == 0 }

    @Test
    fun `Composed lens "get" returns inner field value`() {
        val lens = FullResumeInfo.personalInfo at PersonalInfo.firstName
        val actual = lens.get(resumeInfo)

        val expected = resumeInfo.personalInfo.firstName

        assertEquals(expected, actual)
    }

    @Test
    fun `Composed lens "set" updates inner field value`() {
        val lens = FullResumeInfo.personalInfo at PersonalInfo.firstName
        val actual = lens.set(resumeInfo, "Mary")

        val expected = resumeInfo.copy(
            personalInfo = resumeInfo.personalInfo.copy(
                firstName = "Mary"
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `"each" list traversal from lens returns correct list items`() {
        val traversal = FullResumeInfo.hiddenFields at isEven at HiddenFieldItem.id
        val actual = traversal.get(resumeInfo)

        val expected = resumeInfo.hiddenFields
            .filterIndexed { ind, _ -> isEven(ind) }
            .map(HiddenFieldItem::id)

        assertEquals(expected, actual)
    }

    @Test
    fun `"each" list traversal from lens updates correct list items`() {
        val traversal = FullResumeInfo.hiddenFields at isEven at HiddenFieldItem.id
        val actual = traversal.set(resumeInfo, 666)

        val expectedHiddenFields = resumeInfo.hiddenFields.mapIndexed { index, hiddenFieldItem ->
            if (isEven(index)) {
                hiddenFieldItem.copy(id = 666)
            } else {
                hiddenFieldItem
            }
        }
        val expected = resumeInfo.copy(hiddenFields = expectedHiddenFields)

        assertEquals(expected, actual)
    }

}