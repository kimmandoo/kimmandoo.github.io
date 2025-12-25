package io.github.kimmandoo.model

import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

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
    BUBBY_CHAT(
        titleRes = Res.string.project_bubbychat,
        subtitleRes = Res.string.project_bubbychat_subtitle,
        graphicRes = Res.drawable.ic_bubbychat,
        periodRes = Res.string.project_bubbychat_period,
        descriptionRes = Res.string.project_bubbychat_description,
        roleRes = Res.string.android_developer,
        techStackRes = Res.string.project_bubbychat_techStack,
        links = listOf(Link(Res.string.github, "https://github.com/LAWGICAL-AI/BubbyChat", "github")),
        contributionsRes = Res.string.project_bubbychat_contributions,
    ),
    DRTAA(
        titleRes = Res.string.project_data,
        subtitleRes = Res.string.project_data_subtitle,
        graphicRes = Res.drawable.ic_drtaa,
        periodRes = Res.string.project_data_period,
        descriptionRes = Res.string.project_data_description,
        roleRes = Res.string.android_developer_leader, // 'Android 개발 팀장'에 맞는 역할 리소스 적용
        techStackRes = Res.string.project_data_techStack,
        links = listOf(
            Link(Res.string.github, "https://github.com/kimmandoo/DRTAA", "github") // GitHub URL 입력 필요
        ),
        contributionsRes = Res.string.project_data_contributions,
    ),
    COLORPL(
        titleRes = Res.string.project_colorful,
        subtitleRes = Res.string.project_colorful_subtitle,
        graphicRes = Res.drawable.ic_colorpl, // 이미지 리소스명 확인 필요
        periodRes = Res.string.project_colorful_period,
        descriptionRes = Res.string.project_colorful_description,
        roleRes = Res.string.android_developer,
        techStackRes = Res.string.project_colorful_techStack,
        links = listOf(
            Link(Res.string.github, "https://github.com/kimmandoo/Colorpl", "github")
        ),
        contributionsRes = Res.string.project_colorful_contributions,
    ),
    SUCHELIN(
        titleRes = Res.string.project_soochelin,
        subtitleRes = Res.string.project_soochelin_subtitle,
        graphicRes = Res.drawable.ic_suchelin, // 이미지 리소스명 확인 필요
        periodRes = Res.string.project_soochelin_period,
        descriptionRes = Res.string.project_soochelin_description,
        roleRes = Res.string.owner, // '1인 개발'에 맞는 역할 리소스 적용
        techStackRes = Res.string.project_soochelin_techStack,
        links = listOf(
            Link(Res.string.google_play_store, "https://play.google.com/store/apps/details?id=com.Guide.suchelin", "play"), // 플레이스토어 링크가 있다면 입력
            Link(Res.string.github, "https://github.com/SuChelin/SuChelinV2", "github")
        ),
        contributionsRes = Res.string.project_soochelin_contributions,
    ),
//    LUCKY_LOTTERY(
//        titleRes = Res.string.project_lucky_lottery,
//        subtitleRes = Res.string.project_lucky_lottery_subtitle,
//        graphicRes = Res.drawable.ic_android,
//        periodRes = Res.string.project_lucky_lottery_period,
//        descriptionRes = Res.string.project_lucky_lottery_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_lucky_lottery_techStack,
//        links = listOf(),
//        contributionsRes = Res.string.project_lucky_lottery_contributions,
//    ),
//    FRIENDOGLY(
//        titleRes = Res.string.project_friendogly,
//        subtitleRes = Res.string.project_friendogly_subtitle,
//        graphicRes = Res.drawable.ic_android,
//        periodRes = Res.string.project_friendogly_period,
//        descriptionRes = Res.string.project_friendogly_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_friendogly_techStack,
//        links = listOf(),
//        contributionsRes = Res.string.project_friendogly_contributions,
//    ),
//    MOVE_MOVE(
//        titleRes = Res.string.project_move_move,
//        subtitleRes = Res.string.project_move_move_subtitle,
//        graphicRes = Res.drawable.ic_android,
//        periodRes = Res.string.project_move_move_period,
//        descriptionRes = Res.string.project_move_move_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_move_move_techStack,
//        links = listOf(),
//        contributionsRes = Res.string.project_move_move_contributions,
//    ),
//    WHATNOW(
//        titleRes = Res.string.project_whatnow,
//        subtitleRes = Res.string.project_whatnow_subtitle,
//        graphicRes = Res.drawable.ic_android,
//        periodRes = Res.string.project_whatnow_period,
//        descriptionRes = Res.string.project_whatnow_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_whatnow_techStack,
//        links = listOf(),
//        contributionsRes = Res.string.project_whatnow_contributions,
//    ),
//    OH_SOON_TAXI(
//        titleRes = Res.string.project_oh_soon_taxi,
//        subtitleRes = Res.string.project_oh_soon_taxi_subtitle,
//        graphicRes = Res.drawable.ic_android,
//        periodRes = Res.string.project_oh_soon_taxi_period,
//        descriptionRes = Res.string.project_oh_soon_taxi_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_oh_soon_taxi_techStack,
//        links = listOf(),
//        contributionsRes = Res.string.project_oh_soon_taxi_contributions,
//    ),
//    KNOCKNOCK(
//        titleRes = Res.string.project_knocknock,
//        subtitleRes = Res.string.project_knocknock_subtitle,
//        graphicRes = Res.drawable.ic_android,
//        periodRes = Res.string.project_knocknock_period,
//        descriptionRes = Res.string.project_knocknock_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_knocknock_techStack,
//        links = listOf(),
//        contributionsRes = Res.string.project_knocknock_contributions,
//    ),
//    KORDLE(
//        titleRes = Res.string.project_kordle,
//        subtitleRes = Res.string.project_kordle_subtitle,
//        graphicRes = Res.drawable.ic_android,
//        periodRes = Res.string.project_kordle_period,
//        descriptionRes = Res.string.project_kordle_description,
//        roleRes = Res.string.android_developer,
//        techStackRes = Res.string.project_kordle_techStack,
//        links = listOf(),
//        contributionsRes = Res.string.project_kordle_contributions,
//    ),
}
