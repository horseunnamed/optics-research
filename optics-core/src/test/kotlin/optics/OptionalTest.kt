package optics

import model.Experience
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import test.model.FullResumeInfo
import test.model.TestData.resumeInfo

internal class OptionalTest {

    @Test
    fun `Optional gets non-null value`() {
        val optional = FullResumeInfo.experience at Experience.years
        val actual = optional.get(resumeInfo)
        val expected = resumeInfo.experience?.years

        assertEquals(expected, actual)
    }

    @Test
    fun `Optional gets null value`() {
        val resume = resumeInfo.copy(experience = null)
        val optional = FullResumeInfo.experience at Experience.years
        val actual = optional.get(resume)
        val expected = resume.experience?.years

        assertEquals(expected, actual)
    }

    @Test
    fun `Optional sets value`() {
        val optional = FullResumeInfo.experience at Experience.years
        val actual = optional.set(resumeInfo, 100500)
        val expected = resumeInfo.copy(experience = resumeInfo.experience?.copy(years = 100500))

        assertEquals(expected, actual)
    }

    @Test
    fun `Optional composes with ListTraversal`() {
        val traversal = FullResumeInfo.experience at Experience.lastPositions at ListTraversal.all()
        val actual = traversal.set(resumeInfo, "SCP")

        val expected = resumeInfo.copy(
            experience = resumeInfo.experience?.copy(
                lastPositions = resumeInfo.experience.lastPositions.map { "SCP" }
            )
        )

        assertEquals(expected, actual)
    }

}