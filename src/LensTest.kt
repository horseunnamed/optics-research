import model.FullResumeInfo
import model.HiddenFieldItem
import model.PersonalInfo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LensTest {

    private val resumeInfo = FullResumeInfo(
        id = 123,
        personalInfo = PersonalInfo(
            firstName = "Jack"
        ),
        hiddenFields = listOf(
            HiddenFieldItem(id = "123"),
            HiddenFieldItem(id = "456")
        )
    )

    @Test
    fun testComposedLensSet() = with(LensDSL) {
        val expected = resumeInfo.copy(
            personalInfo = resumeInfo.personalInfo.copy(
                firstName = "Mary"
            )
        )

        val actual = resumeInfo
            .at(FullResumeInfo.personalInfo)
            .at(PersonalInfo.firstName)
            .set("Mary")

        assertEquals(actual, expected)
    }

    @Test
    fun testListSetAtPositionLens() = with(LensDSL) {
        val pos = 1

        val expected = resumeInfo.copy(
            hiddenFields = resumeInfo.hiddenFields.mapIndexed { index, hiddenFieldItem ->
                if (index == pos) hiddenFieldItem.copy(id = "666") else hiddenFieldItem
            }
        )

        val actual = resumeInfo
            .at(FullResumeInfo.hiddenFields)
            .at(pos)
            .at(HiddenFieldItem.id)
            .set("666")

        val lens = FullResumeInfo.hiddenFields.at(pos).at(HiddenFieldItem.id)
        lens.set(resumeInfo, "66")

        assertEquals(actual, expected)
    }

    @Test
    fun testListSetEachLens() = with(LensDSL) {
        val expected = resumeInfo.copy(
            hiddenFields = resumeInfo.hiddenFields.map { it.copy(id = "666") }
        )

        val actual = resumeInfo
            .at(FullResumeInfo.hiddenFields)
            .each()
            .at(HiddenFieldItem.id)
            .set("666")

        assertEquals(actual, expected)
    }

    @Test
    fun testListSetEachEmptyLens() = with(LensDSL) {
        val resumeInfoWEmpty = resumeInfo.copy(hiddenFields = emptyList())

        val expected = resumeInfoWEmpty.copy(
            hiddenFields = resumeInfo.hiddenFields.map { it.copy(id = "666") }
        )

        val actual = resumeInfoWEmpty
            .at(FullResumeInfo.hiddenFields)
            .each()
            .at(HiddenFieldItem.id)
            .set("666")

        assertEquals(actual, expected)
    }

}