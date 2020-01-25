package test.model

import optics.Lens


data class FullResumeInfo(
    val id: Int,
    val personalInfo: PersonalInfo,
    val hiddenFields: List<HiddenFieldItem>
) {
    companion object {
        val id = Lens(
            get = FullResumeInfo::id,
            set = { s, f -> s.copy(id = f) }
        )

        val personalInfo = Lens(
            get = FullResumeInfo::personalInfo,
            set = { s, f -> s.copy(personalInfo = f) }
        )

        val hiddenFields = Lens(
            get = FullResumeInfo::hiddenFields,
            set = { s, f -> s.copy(hiddenFields = f) }
        )
    }
}