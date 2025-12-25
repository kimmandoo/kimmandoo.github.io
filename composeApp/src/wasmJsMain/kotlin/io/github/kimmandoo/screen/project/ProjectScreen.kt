package io.github.kimmandoo.screen.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.DESKTOP_CONTENT_HORIZONTAL_PADDING
import io.github.kimmandoo.TABLET_CONTENT_HORIZONTAL_PADDING
import io.github.kimmandoo.model.Project
import io.github.kimmandoo.ui.Emerald
import io.github.kimmandoo.ui.adaptive.Device
import io.github.kimmandoo.ui.adaptive.contentPadding
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.project_collapse
import kimmandoo_porfolio.composeapp.generated.resources.project_more
import kimmandoo_porfolio.composeapp.generated.resources.section_project
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProjectScreen(
    modifier: Modifier = Modifier,
    onProjectClick: (Project) -> Unit = {},
) {
    val deviceState = rememberDeviceState()
    var isExpanded by remember { mutableStateOf(false) }

    val initialProjectCount = when (deviceState.value) {
        Device.MOBILE -> 3
        Device.TABLET -> 4
        Device.DESKTOP -> 6
        Device.UNKNOWN -> 4
    }

    val projects = Project.entries
    val visibleProjects = if (isExpanded) projects else projects.take(initialProjectCount)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(deviceState.contentPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                stringResource(Res.string.section_project),
                color = Emerald,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                letterSpacing = 1.sp,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        val horizontalPadding = when (deviceState.value) {
            Device.UNKNOWN -> 0.dp
            Device.MOBILE -> 0.dp
            Device.TABLET -> (TABLET_CONTENT_HORIZONTAL_PADDING / 4).dp
            Device.DESKTOP -> (DESKTOP_CONTENT_HORIZONTAL_PADDING / 3).dp
        }

        // 프로젝트 그리드
        ProjectGrid(
            projects = visibleProjects,
            deviceState = deviceState.value,
            modifier = Modifier.padding(horizontal = horizontalPadding),
            onProjectClick = onProjectClick,
        )

        // 더보기/접기 버튼
        if (projects.size > initialProjectCount) {
            Spacer(modifier = Modifier.height(32.dp))

            TextButton(
                onClick = { isExpanded = !isExpanded },
            ) {
                Text(
                    text = if (isExpanded) stringResource(Res.string.project_collapse) else stringResource(Res.string.project_more),
                    color = Emerald,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Emerald,
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProjectGrid(
    projects: List<Project>,
    deviceState: Device,
    modifier: Modifier = Modifier,
    onProjectClick: (Project) -> Unit,
) {
    val columns = when (deviceState) {
        Device.MOBILE -> 1
        Device.TABLET -> 2
        Device.DESKTOP -> 3
        Device.UNKNOWN -> 2
    }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        maxItemsInEachRow = columns,
    ) {
        projects.forEach { project ->
            ProjectCard(
                project = project,
                deviceState = deviceState,
                onClick = { onProjectClick(project) },
                modifier = when (deviceState) {
                    Device.MOBILE -> Modifier.fillMaxWidth()
                    Device.TABLET -> Modifier.weight(1f).widthIn(max = 360.dp)
                    Device.DESKTOP -> Modifier.weight(1f).widthIn(max = 400.dp)
                    Device.UNKNOWN -> Modifier.weight(1f)
                }
            )
        }
    }
}

@Composable
private fun ProjectCard(
    project: Project,
    deviceState: Device,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.02f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

    Card(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = Emerald.copy(alpha = 0.3f),
                ambientColor = Emerald.copy(alpha = 0.1f),
            )
            .hoverable(interactionSource = interactionSource)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            // 상단: 아이콘 + 기간
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                // 프로젝트 아이콘
                Image(
                    painter = painterResource(project.graphicRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(16.dp)).border(
                        width = 4.dp,
                        color = Emerald.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                )

                // 기간 뱃지
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = stringResource(project.periodRes),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 프로젝트 제목
            Text(
                text = stringResource(project.titleRes),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(6.dp))

            // 부제목
            Text(
                text = stringResource(project.subtitleRes),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 기술 스택 칩
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Code,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Emerald,
                )
                Text(
                    text = stringResource(project.techStackRes).split(",").take(3).joinToString(" • "),
                    fontSize = 12.sp,
                    color = Emerald,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 역할 + 상세보기 버튼
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 역할 뱃지
                Box(
                    modifier = Modifier
                        .background(
                            color = Emerald.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(project.roleRes),
                        color = Emerald,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}
