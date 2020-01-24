package model

import Lens

data class FullResumeInfo(
    val id: Int,
    val personalInfo: PersonalInfo,
    val hiddenFields: List<HiddenFieldItem>
) {
    companion object {
        val id = Lens(
            get = FullResumeInfo::id,
            set = { resume, id -> resume.copy(id = id) }
        )

        val personalInfo = Lens(
            get = FullResumeInfo::personalInfo,
            set = { resume, personalInfo -> resume.copy(personalInfo = personalInfo) }
        )

        val hiddenFields = Lens(
            get = FullResumeInfo::hiddenFields,
            set = { resume, hiddenFields -> resume.copy(hiddenFields = hiddenFields) }
        )
    }
}