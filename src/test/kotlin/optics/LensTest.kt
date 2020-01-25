package optics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import test.model.TestData.resumeInfo
import test.model.FullResumeInfo
import test.model.PersonalInfo

class LensTest {

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

}