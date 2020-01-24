package model

import Lens

data class HiddenFieldItem(
    val id: String
) {
    companion object {
        val id = Lens(
            get = HiddenFieldItem::id,
            set = { obj, field -> obj.copy(id = field)  }
        )
    }
}