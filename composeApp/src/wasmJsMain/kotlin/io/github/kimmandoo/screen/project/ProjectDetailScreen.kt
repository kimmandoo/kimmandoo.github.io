package io.github.kimmandoo.screen.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kimmandoo.model.Project
import io.github.kimmandoo.ui.Emerald
import io.github.kimmandoo.ui.adaptive.Device
import io.github.kimmandoo.ui.adaptive.contentPadding
import io.github.kimmandoo.ui.adaptive.rememberDeviceState
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.contributions
import kimmandoo_porfolio.composeapp.generated.resources.project_intro
import kimmandoo_porfolio.composeapp.generated.resources.role
import kimmandoo_porfolio.composeapp.generated.resources.techStack
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProjectDetailScreen(
    project: Project,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val deviceState = rememberDeviceState()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(deviceState.contentPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 뒤로가기 버튼
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로가기",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 프로젝트 헤더
            ProjectDetailHeader(project = project, deviceState = deviceState.value)

            Spacer(modifier = Modifier.height(40.dp))

            // 컨텐츠 영역
            val contentMaxWidth = when (deviceState.value) {
                Device.MOBILE -> Modifier.fillMaxWidth()
                Device.TABLET -> Modifier.widthIn(max = 700.dp)
                Device.DESKTOP -> Modifier.widthIn(max = 900.dp)
                Device.UNKNOWN -> Modifier.widthIn(max = 700.dp)
            }

            Column(
                modifier = contentMaxWidth,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 프로젝트 소개
                DetailSection(
                    icon = Icons.Default.Description,
                    title = stringResource(Res.string.project_intro),
                    content = stringResource(project.descriptionRes),
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 기술 스택
                DetailSection(
                    icon = Icons.Default.Code,
                    title = stringResource(Res.string.techStack),
                    content = stringResource(project.techStackRes),
                    isChipStyle = true,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 주요 기여
                DetailSection(
                    icon = Icons.Default.CheckCircle,
                    title = stringResource(Res.string.contributions),
                    content = stringResource(project.contributionsRes),
                    isBulletList = true,
                )

                // 관련 링크
                if (project.links.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    LinksSection(project = project)
                }

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
private fun ProjectDetailHeader(
    project: Project,
    deviceState: Device,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Emerald.copy(alpha = 0.15f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 프로젝트 아이콘
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Emerald.copy(alpha = 0.2f),
                                Emerald.copy(alpha = 0.05f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0f)
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Emerald.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(project.graphicRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 프로젝트 제목
            Text(
                text = stringResource(project.titleRes),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 부제목
            Text(
                text = stringResource(project.subtitleRes),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 메타 정보 (역할, 기간)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 역할 뱃지
                MetaBadge(
                    icon = Icons.Default.Person,
                    text = stringResource(project.roleRes),
                    isPrimary = true,
                )

                // 기간 뱃지
                MetaBadge(
                    icon = Icons.Default.CalendarMonth,
                    text = stringResource(project.periodRes),
                    isPrimary = false,
                )
            }
        }
    }
}

@Composable
private fun MetaBadge(
    icon: ImageVector,
    text: String,
    isPrimary: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = if (isPrimary) Emerald.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isPrimary) Emerald else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = text,
                fontSize = 13.sp,
                color = if (isPrimary) Emerald else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun DetailSection(
    icon: ImageVector,
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    isChipStyle: Boolean = false,
    isBulletList: Boolean = false,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = Emerald.copy(alpha = 0.1f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        ) {
            // 섹션 헤더
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Emerald.copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Emerald,
                    )
                }
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 1.dp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 컨텐츠
            when {
                isChipStyle -> {
                    TechStackChips(content)
                }
                isBulletList -> {
                    BulletList(content)
                }
                else -> {
                    Text(
                        text = content,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 26.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun TechStackChips(techStack: String) {
    val chips = techStack.split(",").map { it.trim() }

    androidx.compose.foundation.layout.FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        chips.forEach { chip ->
            Box(
                modifier = Modifier
                    .background(
                        color = Emerald.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Emerald.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = chip,
                    fontSize = 13.sp,
                    color = Emerald,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun BulletList(content: String) {
    val items = content.split("\n").filter { it.isNotBlank() }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            val cleanItem = item.trimStart('-', ' ', '\t')
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(6.dp)
                        .background(Emerald, CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = cleanItem,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp,
                )
            }
        }
    }
}

@Composable
private fun LinksSection(
    project: Project,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = Emerald.copy(alpha = 0.1f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        ) {
            // 섹션 헤더
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Emerald.copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Emerald,
                    )
                }
                Text(
                    text = "관련 링크",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 1.dp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 링크 목록
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                project.links.forEach { link ->
                    LinkItem(
                        title = stringResource(link.title),
                        url = link.url,
                        onClick = { uriHandler.openUri(link.url) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LinkItem(
    title: String,
    url: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.02f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .hoverable(interactionSource = interactionSource)
            .clickable(onClick = onClick)
            .background(
                color = if (isHovered) Emerald.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = if (isHovered) Emerald.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = if (isHovered) Emerald else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
            )
            Icon(
                imageVector = Icons.Default.Link,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (isHovered) Emerald else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
