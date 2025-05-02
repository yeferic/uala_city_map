package com.yeferic.ualacity.core.commons

import com.yeferic.ualacity.domain.exceptions.ErrorEntity

sealed class UseCaseStatus<out R> {
    data class Success<out T>(
        val data: T,
    ) : UseCaseStatus<T>()

    data class Error(
        val errorEntity: ErrorEntity,
    ) : UseCaseStatus<Nothing>()
}
