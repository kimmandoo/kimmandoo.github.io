package io.github.kimmandoo.model

import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource


data class EducationDetail(
    val titleRes: StringResource,
    val periodRes: StringResource? = null,
    val techStackRes: StringResource? = null,
    val contributionsRes: StringResource? = null,
    val descriptionRes: StringResource? = null,
) {
    companion object Companion {
        val SSAFY_DETAIL =
            listOf(
                EducationDetail(
                    titleRes = Res.string.experience_ssafy_title,
                    descriptionRes = Res.string.experience_ssafy_desc
                ),
            )
    }
}
