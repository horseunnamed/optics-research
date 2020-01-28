package optics

import model.Experience
import model.HttpError
import model.HttpSuccess
import model.NetworkResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import test.model.FullResumeInfo
import test.model.TestData.resumeInfo

internal class OptLensTest {

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

    @Test
    fun `Optional can be used for sealed class`() {
        val networkResultSuccess = HttpSuccess(data = "Ok!")
        val networkResultError = HttpError(code = 404)

        val optics = NetworkResult.success<String>() at HttpSuccess.data()
        val actual1 = optics.set(networkResultSuccess, "hello")
        val actual2 = optics.set(networkResultError, "hello")

        val expected1 = HttpSuccess(HttpSuccess(data = "hello"))
        val expected2 = HttpError(code = 404)

        assertEquals(expected1, actual1)
        assertEquals(expected2, actual2)
    }

}