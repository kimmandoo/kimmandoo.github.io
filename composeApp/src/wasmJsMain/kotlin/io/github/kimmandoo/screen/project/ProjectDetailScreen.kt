package io.github.kimmandoo.screen.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
import kimmandoo_porfolio.composeapp.generated.resources.*
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.contributions
import kimmandoo_porfolio.composeapp.generated.resources.project_intro
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
            // 배경 이미지가 없으면 기본 surface 색상, 있으면 투명(이미지가 보이게)
            containerColor = if (project.banner != null) Color.Transparent else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (project.banner != null) {
                Image(
                    painter = painterResource(project.banner),
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // 꽉 차게 자르기
                    modifier = Modifier.matchParentSize() // Box 크기에 맞춤
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                )
            }

            // 4. 기존 내용 (Column)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 프로젝트 아이콘
                Image(
                    painter = painterResource(project.graphicRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            width = 4.dp,
                            color = Emerald.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 프로젝트 제목
                Text(
                    text = stringResource(project.titleRes),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    // 배경 이미지 유무에 따라 글자색 변경 (이미지 위면 흰색 강제)
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 부제목
                Text(
                    text = stringResource(project.subtitleRes),
                    fontSize = 16.sp,
                    // 배경 이미지 유무에 따라 글자색 변경
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 메타 정보
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MetaBadge(
                        icon = Icons.Default.Person,
                        text = stringResource(project.roleRes),
                        isPrimary = true,
                        isBannerOn = project.banner != null,
                    )

                    MetaBadge(
                        icon = Icons.Default.CalendarMonth,
                        text = stringResource(project.periodRes),
                        isPrimary = false,
                        isBannerOn = project.banner != null
                    )
                }
            }
        }
    }
}

@Composable
private fun MetaBadge(
    icon: ImageVector,
    text: String,
    isPrimary: Boolean,
    isBannerOn: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = if (isPrimary) {
                    if (isBannerOn) {
                        Emerald.copy(alpha = 0.60f)
                    } else {
                        Emerald.copy(alpha = 0.15f)
                    }
                } else MaterialTheme.colorScheme.surfaceVariant,
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
                tint = if (isPrimary)  if(isBannerOn){
                    MaterialTheme.colorScheme.onSurface
                }else {
                    Emerald
                } else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = text,
                fontSize = 13.sp,
                color = if (isPrimary) {
                    if(isBannerOn){
                        MaterialTheme.colorScheme.onSurface
                    }else {
                        Emerald
                    }
                } else MaterialTheme.colorScheme.onSurfaceVariant,
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
                        onClick = { uriHandler.openUri(link.url) },
                        icon = link.icon
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
    icon: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                shape = RoundedCornerShape(16.dp),
                spotColor = Emerald.copy(alpha = 0.3f),
                ambientColor = Emerald.copy(alpha = 0.1f),
            )
            .hoverable(interactionSource = interactionSource)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(width = 2.dp, color = Emerald.copy(0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(
                visible = icon.isNotBlank()
            ) {
                val iconSource = when (icon) {
                    "github" -> {
                        painterResource(Res.drawable.ic_github)
                    }

                    "play" -> {
                        painterResource(Res.drawable.ic_playstore)
                    }

                    else -> {
                        painterResource(Res.drawable.ic_web)
                    }
                }
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = iconSource,
                    contentDescription = "icon",
                    tint = Color.Unspecified // 안하면 틴트때문에 가려짐
                    // Icon 컴포넌트는 기본적으로 현재 테마의 텍스트 색상(LocalContentColor)으로 이미지를 덮어씌우는(Tint) 속성을 가지고 있음
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = if (isHovered) Emerald else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Link,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (isHovered) Emerald else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
