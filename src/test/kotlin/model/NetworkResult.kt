package model

import optics.Lens
import optics.Optional

sealed class NetworkResult<out T> {
    companion object {
        fun <T> success() = Optional<NetworkResult<T>, HttpSuccess<T>>(
            get = { it as? HttpSuccess },
            set = { _, f -> f }
        )

        fun <T> error() = Optional<NetworkResult<T>, HttpError>(
            get = { it as? HttpError },
            set = { _, f -> f }
        )
    }
}

data class HttpSuccess<T>(
    val data: T
): NetworkResult<T>() {
    companion object {
        fun <T> data() = Lens(
            get = HttpSuccess<T>::data,
            set = { s, f -> s.copy(data = f) }
        )
    }
}

data class HttpError(
    val code: Int
): NetworkResult<Nothing>() {
    companion object {
        val code = Lens(
            get = HttpError::code,
            set = { s, f -> s.copy(code = f) }
        )
    }
}
