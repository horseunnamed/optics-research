package test.model

import optics.Lens


data class PersonalInfo(
    val firstName: String
) {
    companion object {
        val firstName = Lens(
            get = PersonalInfo::firstName,
            set = { s, f -> s.copy(firstName = f) }
        )
    }
}