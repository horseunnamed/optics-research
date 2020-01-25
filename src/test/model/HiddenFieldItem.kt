package test.model

import optics.Lens


data class HiddenFieldItem(
    val id: Int,
    val name: String
) {
    companion object {
        val id = Lens(
            get = HiddenFieldItem::id,
            set = { s, f -> s.copy(id = f)  }
        )

        val name = Lens(
            get = HiddenFieldItem::name,
            set = { s, f -> s.copy(name = f) }
        )
    }
}