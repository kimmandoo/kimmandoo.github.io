package io.github.kimmandoo.screen.carex

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.ui.Emerald
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
import kimmandoo_porfolio.composeapp.generated.resources.*
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.career
import kimmandoo_porfolio.composeapp.generated.resources.experience_contribute
import kimmandoo_porfolio.composeapp.generated.resources.experience_education
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
        // 섹션 뱃지
        Box(
            modifier = Modifier
                .background(
                    color = Emerald.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                stringResource(Res.string.section_career_experience),
                color = Emerald,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                letterSpacing = 1.sp,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 구분선
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Emerald.copy(alpha = 0.5f),
                            Emerald,
                            Emerald.copy(alpha = 0.5f)
                        )
                    )
                )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Education 서브섹션
        SubSectionHeader(
            title = stringResource(Res.string.career),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Career.entries.forEachIndexed { index, career ->
            CareerContent(
                career = career,
                isFirst = index == 0,
                isLast = index == Career.entries.lastIndex,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Education 서브섹션
        SubSectionHeader(
            title = stringResource(Res.string.experience_education),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Education.entries.forEachIndexed { index, exp ->
            EducationContent(
                experience = exp,
                isFirst = index == 0,
                isLast = index == Education.entries.lastIndex,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Contribution 서브섹션
        SubSectionHeader(
            title = stringResource(Res.string.experience_contribute),
        )

        Spacer(modifier = Modifier.height(16.dp))

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
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.01f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

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

        Card(
            modifier = Modifier
                .weight(1f)
                .scale(scale)
                .hoverable(interactionSource = interactionSource)
                .shadow(
                    elevation = if (isHovered) 12.dp else 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    spotColor = Emerald.copy(alpha = 0.2f)
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CardImage(
                        logo = career.logoRes,
                        size = CAREER_LOGO_SIZE,
                        cornerRadius = 28.dp,
                        elevation = 4.dp,
                        contentPadding = PaddingValues(6.dp),
                    )

                    Spacer(Modifier.width(20.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = stringResource(career.nameRes),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                        )
                        Spacer(Modifier.height(4.dp))
                        InfoChip(
                            text = stringResource(career.introRes),
                        )
                        Text(
                            text = stringResource(career.teamRes),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Emerald, CircleShape)
                            )
                            Text(
                                text = stringResource(career.periodRes),
                                color = Emerald,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                career.project.forEachIndexed { index, careerProject ->
                    CareerProjectItem(careerProject = careerProject, index = index)
                    if (index < career.project.lastIndex) {
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    AnimatedVisibility(visible = !isLast) {
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun CareerProjectItem(
    careerProject: CareerProject,
    index: Int = 0,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Emerald, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = stringResource(careerProject.titleRes),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }

            Spacer(Modifier.height(12.dp))

            DetailRow(
                label = "기간",
                value = stringResource(careerProject.periodRes),
            )
            Spacer(Modifier.height(6.dp))
            DetailRow(
                label = "기술",
                value = stringResource(careerProject.techStackRes),
            )
            Spacer(Modifier.height(6.dp))
            DetailRow(
                label = "기여",
                value = stringResource(careerProject.contributionsRes),
            )
        }
    }
}

@Composable
private fun EducationContent(
    experience: Education,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.01f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

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

        Card(
            modifier = Modifier
                .weight(1f)
                .scale(scale)
                .hoverable(interactionSource = interactionSource)
                .shadow(
                    elevation = if (isHovered) 12.dp else 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    spotColor = Emerald.copy(alpha = 0.2f)
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CardImage(
                        logo = experience.logoRes,
                        size = CAREER_LOGO_SIZE,
                        cornerRadius = 28.dp,
                        elevation = 4.dp,
                        contentPadding = PaddingValues(6.dp),
                    )

                    Spacer(Modifier.width(20.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = stringResource(experience.nameRes),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                        )
                        Spacer(Modifier.height(4.dp))
                        InfoChip(
                            text = stringResource(experience.introRes),
                        )
                        Text(
                            text = stringResource(experience.descRes),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Emerald, CircleShape)
                            )
                            Text(
                                text = stringResource(experience.periodRes),
                                color = Emerald,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                experience.exp.forEachIndexed { index, exp ->
                    EducationItem(educationDetail = exp, index = index)
                    if (index < experience.exp.lastIndex) {
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    AnimatedVisibility(visible = !isLast) {
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun EducationItem(
    educationDetail: EducationDetail,
    index: Int = 0,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Emerald, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = stringResource(educationDetail.titleRes),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }

            Spacer(Modifier.height(12.dp))

            educationDetail.periodRes?.let { periodRes ->
                DetailRow(
                    label = "기간",
                    value = stringResource(periodRes),
                )
                Spacer(Modifier.height(6.dp))
            }
            educationDetail.techStackRes?.let { techStackRes ->
                DetailRow(
                    label = "기술",
                    value = stringResource(techStackRes),
                )
                Spacer(Modifier.height(6.dp))
            }
            educationDetail.contributionsRes?.let { contributionsRes ->
                DetailRow(
                    label = "기여",
                    value = stringResource(contributionsRes),
                )
                Spacer(Modifier.height(6.dp))
            }
            educationDetail.descriptionRes?.let { descriptionRes ->
                DetailRow(
                    label = "설명",
                    value = stringResource(descriptionRes),
                )
            }
        }
    }
}

@Composable
private fun ContributionContent(
    contribution: Contribution,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.01f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

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

        Card(
            modifier = Modifier
                .weight(1f)
                .scale(scale)
                .hoverable(interactionSource = interactionSource)
                .shadow(
                    elevation = if (isHovered) 12.dp else 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    spotColor = Emerald.copy(alpha = 0.2f)
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CardImage(
                        logo = contribution.logoRes,
                        size = CAREER_LOGO_SIZE,
                        cornerRadius = 28.dp,
                        elevation = 4.dp,
                        contentPadding = PaddingValues(6.dp),
                    )

                    Spacer(Modifier.width(20.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = stringResource(contribution.nameRes),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                        )
                        Spacer(Modifier.height(4.dp))
                        InfoChip(
                            text = stringResource(contribution.introRes),
                        )
                        Text(
                            text = stringResource(contribution.descRes),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Emerald, CircleShape)
                            )
                            Text(
                                text = stringResource(contribution.periodRes),
                                color = Emerald,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                            )
                        }
                        TextWithLink(
                            url = stringResource(contribution.link),
                            fontWeight = FontWeight.Bold,
                            color = Emerald,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }

    AnimatedVisibility(visible = !isLast) {
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
        )
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Emerald)
        )
    }
}

@Composable
private fun SubSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Emerald)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        )
    }
}

@Composable
private fun InfoChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = Emerald.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = Emerald,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 18.sp,
        )
    }
}

private val CAREER_DOT_SIZE = 24.dp
private val CAREER_LOGO_SIZE = 100.dp
