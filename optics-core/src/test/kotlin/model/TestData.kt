package test.model

import model.Experience
import test.model.FullResumeInfo
import test.model.HiddenFieldItem
import test.model.PersonalInfo

object TestData {

    val resumeInfo = FullResumeInfo(
        id = 1,
        personalInfo = PersonalInfo(
            firstName = "Jack"
        ),
        hiddenFields = listOf(
            HiddenFieldItem(id = 123, name = "first_item"),
            HiddenFieldItem(id = 456, name = "last_item"),
            HiddenFieldItem(id = 789, name = "third_item")
        ),
        experience = Experience(
            years = 100,
            lastPositions = listOf("Bookstore", "Soda magazine", "Big credit company")
        )
    )

}