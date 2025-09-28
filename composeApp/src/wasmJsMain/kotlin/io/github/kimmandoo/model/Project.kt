package io.github.kimmandoo.model

import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.github
import kimmandoo_porfolio.composeapp.generated.resources.project_junjange_dev_contributions
import kimmandoo_porfolio.composeapp.generated.resources.project_link_letter_techStack
import kimmandoo_porfolio.composeapp.generated.resources.project_lucky_lottery
import kimmandoo_porfolio.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class Project(
    val titleRes: StringResource,
    val subtitleRes: StringResource,
    val graphicRes: DrawableResource,
    val periodRes: StringResource,
    val descriptionRes: StringResource,
    val roleRes: StringResource,
    val techStackRes: StringResource,
    val links: List<Link>,
    val contributionsRes: StringResource,
) {
//    JUNJANGE_DEV(
//        titleRes = Res.string.project_junjange_dev,
//        subtitleRes = Res.string.project_junjange_dev_subtitle,
//        graphicRes = drawable.ic_junjange_dev_graphic,
//        periodRes = Res.string.project_junjange_dev_period,
//        descriptionRes = Res.string.project_junjange_dev_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_junjange_dev_techStack,
//        links =
//            listOf(
//                Res.string.f to "https://github.com/junjange/junjange.github.io",
//            ),
//        contributionsRes = Res.string.project_junjange_dev_contributions,
//    ),
//    CAMPING_TOUR(
//        titleRes = Res.string.project_camping_tour,
//        subtitleRes = Res.string.project_camping_tour_subtitle,
//        graphicRes = drawable.ic_camping_tour_graphic,
//        periodRes = Res.string.project_camping_tour_period,
//        descriptionRes = Res.string.project_camping_tour_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_camping_tour_techStack,
//        links =
//            listOf(
//                Res.string.google_play_store to "https://play.google.com/store/apps/details?id=com.junjange.touring",
//                Res.string.github to "https://github.com/junjange/camping-tour-android",
//                Res.string.notion to "https://www.notion.so/c6cc6728e74c44d2bf2c1749fe0d7469",
//            ),
//        contributionsRes = Res.string.project_camping_tour_contributions,
//    ),
}
