package io.github.kimmandoo.model

import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource


data class CareerProject(
    val titleRes: StringResource,
    val periodRes: StringResource,
    val techStackRes: StringResource,
    val contributionsRes: StringResource,
) {
    companion object {
        val MARUSYS_PROJECTS =
            listOf(
                CareerProject(
                    titleRes = Res.string.career_marusys_project1_title,
                    periodRes = Res.string.career_marusys_project1_period,
                    techStackRes = Res.string.career_marusys_project1_tech_stack,
                    contributionsRes = Res.string.career_marusys_project1_contributions,
                ),
                CareerProject(
                    titleRes = Res.string.career_marusys_project2_title,
                    periodRes = Res.string.career_marusys_project2_period,
                    techStackRes = Res.string.career_marusys_project2_tech_stack,
                    contributionsRes = Res.string.career_marusys_project2_contributions,
                ),
            )
    }
}
