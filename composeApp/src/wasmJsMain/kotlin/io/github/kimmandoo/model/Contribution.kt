package io.github.kimmandoo.model


import io.github.kimmandoo.model.EducationDetail.Companion.SSAFY_DETAIL
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.*
import kimmandoo_porfolio.composeapp.generated.resources.ic_ssafy
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class Contribution(
    val logoRes: DrawableResource,
    val periodRes: StringResource,
    val nameRes: StringResource,
    val introRes: StringResource,
    val descRes: StringResource,
    val link: StringResource,
) {
    Ssafy(
        logoRes = Res.drawable.ic_kaigi,
        periodRes = Res.string.contribute_kaigi_period,
        nameRes = Res.string.contribute_kaigi,
        introRes = Res.string.contribute_kaigi_subtitle,
        descRes = Res.string.contribute_kaigi_desc,
        link = Res.string.contribute_kaigi_pr_link,
    ),
}
