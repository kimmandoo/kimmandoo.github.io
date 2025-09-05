package io.github.kimmandoo.model

import kimmandoo_porfolio.composeapp.generated.resources.*
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.section_about
import kimmandoo_porfolio.composeapp.generated.resources.section_career
import kimmandoo_porfolio.composeapp.generated.resources.section_home
import org.jetbrains.compose.resources.StringResource

enum class Section(
    val title: StringResource,
) {
    Home(Res.string.section_home),
    About(Res.string.section_about),
    Career(Res.string.section_career),
    Project(Res.string.section_project),
    Experience(Res.string.section_experience),
}