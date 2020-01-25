package model

import optics.Lens

data class Experience(
    val years: Int
) {

    companion object {
        val years = Lens(
            get = Experience::years,
            set = { s, f -> s.copy(years = f) }
        )
    }

}