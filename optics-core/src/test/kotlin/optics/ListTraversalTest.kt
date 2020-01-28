package optics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import test.model.TestData.resumeInfo
import test.model.FullResumeInfo
import test.model.HiddenFieldItem

class ListTraversalTest {

    private val isEven = { num: Int -> num % 2 == 0 }


    @Test
    fun `"eachPos" traversal returns correct list items`() {
        val traversal = FullResumeInfo.hiddenFields at ListTraversal.eachPos(isEven) at HiddenFieldItem.id
        val actual = traversal.get(resumeInfo)

        val expected = resumeInfo.hiddenFields
            .filterIndexed { ind, _ -> isEven(ind) }
            .map(HiddenFieldItem::id)

        assertEquals(expected, actual)
    }

    @Test
    fun `"eachPos" traversal updates correct list items`() {
        val traversal = FullResumeInfo.hiddenFields at ListTraversal.eachPos(isEven) at HiddenFieldItem.id
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