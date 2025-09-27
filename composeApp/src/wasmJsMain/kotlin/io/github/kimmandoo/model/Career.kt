package io.github.kimmandoo.model


import io.github.kimmandoo.model.CareerProject.Companion.MARUSYS_PROJECTS
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.android_developer
import kimmandoo_porfolio.composeapp.generated.resources.career_marusys
import kimmandoo_porfolio.composeapp.generated.resources.career_marusys_intro
import kimmandoo_porfolio.composeapp.generated.resources.career_marusys_period
import kimmandoo_porfolio.composeapp.generated.resources.ic_marusys
import kimmandoo_porfolio.composeapp.generated.resources.software_engineer
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class Career(
    val logoRes: DrawableResource,
    val periodRes: StringResource,
    val nameRes: StringResource,
    val introRes: StringResource,
    val teamRes: StringResource,
    val project: List<CareerProject>,
) {
    Marusys(
        logoRes = Res.drawable.ic_marusys,
        periodRes = Res.string.career_marusys_period,
        nameRes = Res.string.career_marusys,
        introRes = Res.string.career_marusys_intro,
        teamRes = Res.string.software_engineer,
        project = MARUSYS_PROJECTS,
    ),
//    Lio(
//        logoRes = Res.drawable.ic_lio,
//        periodRes = Res.string.career_lio_period,
//        nameRes = Res.string.career_lio,
//        introRes = Res.string.career_lio_intro,
//        teamRes = Res.string.android_developer_freelance,
//        project = LIO_PROJECTS,
//    ),
//    Matrios(
//        logoRes = Res.drawable.ic_matrios,
//        periodRes = Res.string.career_matrios_period,
//        nameRes = Res.string.career_matrios,
//        introRes = Res.string.career_matrios_intro,
//        teamRes = Res.string.android_developer_freelance,
//        project = MATRIOS_PROJECTS,
//    ),
}
