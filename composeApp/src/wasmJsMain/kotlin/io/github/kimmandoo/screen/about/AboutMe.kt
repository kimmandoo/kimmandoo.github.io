package io.github.kimmandoo.screen.about

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.ui.graphics.vector.ImageVector
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.about_me_description1
import kimmandoo_porfolio.composeapp.generated.resources.about_me_description2
import kimmandoo_porfolio.composeapp.generated.resources.about_me_description3
import kimmandoo_porfolio.composeapp.generated.resources.about_me_title1
import kimmandoo_porfolio.composeapp.generated.resources.about_me_title2
import kimmandoo_porfolio.composeapp.generated.resources.about_me_title3
import org.jetbrains.compose.resources.StringResource

enum class AboutMe(
    val titleRes: StringResource,
    val descriptionRes: StringResource,
    val iconsRes: ImageVector,
) {
    CONTENT1(
        titleRes = Res.string.about_me_title1,
        descriptionRes = Res.string.about_me_description1,
        iconsRes = Icons.Default.Info,
    ),
    CONTENT2(
        titleRes = Res.string.about_me_title2,
        descriptionRes = Res.string.about_me_description2,
        iconsRes = Icons.Default.FolderCopy,
    ),
    CONTENT3(
        titleRes = Res.string.about_me_title3,
        descriptionRes = Res.string.about_me_description3,
        iconsRes = Icons.Default.Rocket,
    ),
}
