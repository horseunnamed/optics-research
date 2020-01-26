package model

import optics.Lens

data class Experience(
    val years: Int,
    val lastPositions: List<String>
) {

    companion object {
        val years = Lens(
            get = Experience::years,
            set = { s, f -> s.copy(years = f) }
        )

        val lastPositions = Lens(
            get = Experience::lastPositions,
            set = { s, f -> s.copy(lastPositions = f) }
        )
    }

}