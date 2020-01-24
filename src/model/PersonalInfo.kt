package model

import Lens

data class PersonalInfo(
    val firstName: String
) {
    companion object {
        val firstName = Lens(
            get = PersonalInfo::firstName,
            set = { personalInfo, firstName -> personalInfo.copy(firstName = firstName) }
        )
    }
}