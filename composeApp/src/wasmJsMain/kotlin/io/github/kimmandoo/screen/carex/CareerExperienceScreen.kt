package io.github.kimmandoo.screen.carex

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.component.CardImage
import io.github.kimmandoo.component.TextWithLink
import io.github.kimmandoo.ui.adaptive.contentPadding
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import io.github.kimmandoo.component.TimelineIndicator
import io.github.kimmandoo.model.Career
import io.github.kimmandoo.model.CareerProject
import io.github.kimmandoo.model.Contribution
import io.github.kimmandoo.model.Education
import io.github.kimmandoo.model.EducationDetail
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.career
import kimmandoo_porfolio.composeapp.generated.resources.experience_contribute
import kimmandoo_porfolio.composeapp.generated.resources.experience_education
import kimmandoo_porfolio.composeapp.generated.resources.section_career
import kimmandoo_porfolio.composeapp.generated.resources.section_experience
import org.jetbrains.compose.resources.stringResource

@Composable
fun CareerExperienceScreen(modifier: Modifier = Modifier) {
    val deviceState = rememberDeviceState()

    Column(
        modifier =
            modifier
                .padding(deviceState.contentPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            stringResource(Res.string.section_career),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 36.sp,
            fontSize = 36.sp,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                stringResource(Res.string.career),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Career.entries.forEachIndexed { index, career ->
            CareerContent(
                career = career,
                isFirst = index == 0,
                isLast = index == Career.entries.lastIndex,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                stringResource(Res.string.section_experience),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                stringResource(Res.string.experience_education),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Education.entries.forEachIndexed { index, exp ->
            EducationContent(
                experience = exp,
                isFirst = index == 0,
                isLast = index == Education.entries.lastIndex,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                stringResource(Res.string.experience_contribute),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Contribution.entries.forEachIndexed { index, cont ->
            ContributionContent(
                contribution = cont,
                isFirst = index == 0,
                isLast = index == Education.entries.lastIndex,
            )
        }
    }
}

@Composable
private fun CareerContent(
    career: Career,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top,
    ) {
        TimelineIndicator(
            isFirst = isFirst,
            isLast = isLast,
            frontHeight = ((CAREER_LOGO_SIZE - CAREER_DOT_SIZE) / 2) - 8.dp,
            dotSize = CAREER_DOT_SIZE,
        )

        Spacer(Modifier.width(24.dp))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CardImage(
                    logo = career.logoRes,
                    size = CAREER_LOGO_SIZE,
                    cornerRadius = 32.dp,
                    elevation = 6.dp,
                    contentPadding = PaddingValues(6.dp),
                )

                Spacer(Modifier.width(24.dp))

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(career.nameRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = stringResource(career.introRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = stringResource(career.teamRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = stringResource(career.periodRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            career.project.forEach { careerProject ->
                CareerProjectItem(careerProject = careerProject)
                Spacer(Modifier.height(24.dp))
            }
            AnimatedVisibility(visible = !isLast) {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun CareerProjectItem(careerProject: CareerProject) {
    Text(
        text = stringResource(careerProject.titleRes),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    )
    Text(
        text = stringResource(careerProject.periodRes),
        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )
    Text(
        text = stringResource(careerProject.techStackRes),
        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )
    Text(
        text = stringResource(careerProject.contributionsRes),
        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )
}

@Composable
private fun EducationContent(
    experience: Education,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top,
    ) {
        TimelineIndicator(
            isFirst = isFirst,
            isLast = isLast,
            frontHeight = ((CAREER_LOGO_SIZE - CAREER_DOT_SIZE) / 2) - 8.dp,
            dotSize = CAREER_DOT_SIZE,
        )

        Spacer(Modifier.width(24.dp))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CardImage(
                    logo = experience.logoRes,
                    size = CAREER_LOGO_SIZE,
                    cornerRadius = 32.dp,
                    elevation = 6.dp,
                    contentPadding = PaddingValues(6.dp),
                )

                Spacer(Modifier.width(24.dp))

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(experience.nameRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = stringResource(experience.introRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = stringResource(experience.descRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = stringResource(experience.periodRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            experience.exp.forEach { experience ->
                EducationItem(educationDetail = experience)
                Spacer(Modifier.height(24.dp))
            }
            AnimatedVisibility(visible = !isLast) {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun EducationItem(educationDetail: EducationDetail) {
    Text(
        text = stringResource(educationDetail.titleRes),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    )
    educationDetail.periodRes?.let { periodRes ->
        Text(
            text = stringResource(periodRes),
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
    educationDetail.techStackRes?.let { techStackRes ->
        Text(
            text = stringResource(techStackRes),
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
    educationDetail.contributionsRes?.let { contributionsRes ->
        Text(
            text = stringResource(contributionsRes),
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
    educationDetail.descriptionRes?.let { descriptionRes ->
        Text(
            text = stringResource(descriptionRes),
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun ContributionContent(
    contribution: Contribution,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top,
    ) {
        TimelineIndicator(
            isFirst = isFirst,
            isLast = isLast,
            frontHeight = ((CAREER_LOGO_SIZE - CAREER_DOT_SIZE) / 2) - 8.dp,
            dotSize = CAREER_DOT_SIZE,
        )

        Spacer(Modifier.width(24.dp))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CardImage(
                    logo = contribution.logoRes,
                    size = CAREER_LOGO_SIZE,
                    cornerRadius = 32.dp,
                    elevation = 6.dp,
                    contentPadding = PaddingValues(6.dp),
                )

                Spacer(Modifier.width(24.dp))

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(contribution.nameRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = stringResource(contribution.introRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = stringResource(contribution.descRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = stringResource(contribution.periodRes),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                    TextWithLink(
                        url = stringResource(contribution.link),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f),
                        fontSize = 14.sp,
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

//            career.project.forEach { careerProject ->
//                CareerProjectItem(careerProject = careerProject)
//                Spacer(Modifier.height(24.dp))
//            }
            AnimatedVisibility(visible = !isLast) {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

private val CAREER_DOT_SIZE = 24.dp
private val CAREER_LOGO_SIZE = 108.dp
