package io.github.kimmandoo.model


import io.github.kimmandoo.model.EducationDetail.Companion.SSAFY_DETAIL
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.*
import kimmandoo_porfolio.composeapp.generated.resources.ic_ssafy
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class Education(
    val logoRes: DrawableResource,
    val periodRes: StringResource,
    val nameRes: StringResource,
    val introRes: StringResource,
    val descRes: StringResource,
    val exp: List<EducationDetail>,
) {
    Ssafy(
        logoRes = Res.drawable.ic_ssafy,
        periodRes = Res.string.edu_ssafy_period,
        nameRes = Res.string.edu_ssafy,
        introRes = Res.string.edu_ssafy_subtitle,
        descRes = Res.string.edu_ssafy_desc,
        exp = SSAFY_DETAIL,
    ),
}
