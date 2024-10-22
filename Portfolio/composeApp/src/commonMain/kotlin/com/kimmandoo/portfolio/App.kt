package com.kimmandoo.portfolio

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 200.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 개인 정보 섹션
            PersonalInfoSection()
            Spacer(modifier = Modifier.height(32.dp))
            // 학력 섹션
            SectionTitle("Education & Certificate")
            RowDivider()
            EducationSection()
            Spacer(modifier = Modifier.height(32.dp))
            // 경력 섹션
            SectionTitle("Project")
            RowDivider()
            ProjectSection()
            Spacer(modifier = Modifier.height(32.dp))
            // 수상
            SectionTitle("Honors & Awards")
            RowDivider()
            HonorSection()
            Spacer(modifier = Modifier.height(32.dp))
            RowDivider()
            // 기술 스택 섹션
            SectionTitle("Skills And Other")
            SkillsSection()
        }
    }
}

@Composable
fun RowDivider(){
    Divider(
        color = Color.Gray, // 원하는 색상으로 변경
        thickness = 1.dp,   // 두께를 1.dp로 설정하여 얇은 Divider 생성
        modifier = Modifier
            .fillMaxWidth().padding(bottom = 8.dp) // 가로로 채움
    )
}

@Composable
fun PersonalInfoSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 이름 및 사진
        Image(painterResource(Res.drawable.compose_multiplatform),
              modifier = Modifier.size(120.dp),
              contentDescription = "Profile Picture"
        )
        Text(text = "Mingyu Kim", style = MaterialTheme.typography.h3)
        Text(text = "Android Developer", style = MaterialTheme.typography.body1)
        Text(text = "mingyu5675@gmail.com", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun ProjectSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 경력 1
        Text(text = "회사 A", style = MaterialTheme.typography.h6)
        Text(text = "2020 - 현재", style = MaterialTheme.typography.body2)
        Text(text = "Android 앱 개발 및 유지보수 담당", style = MaterialTheme.typography.body2)

        Spacer(modifier = Modifier.height(8.dp))

        // 경력 2
        Text(text = "회사 B", style = MaterialTheme.typography.h6)
        Text(text = "2018 - 2020", style = MaterialTheme.typography.body2)
        Text(text = "웹 애플리케이션 개발", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun HonorSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 경력 1
        Text(text = "회사 A", style = MaterialTheme.typography.h6)
        Text(text = "2020 - 현재", style = MaterialTheme.typography.body2)
        Text(text = "Android 앱 개발 및 유지보수 담당", style = MaterialTheme.typography.body2)

        Spacer(modifier = Modifier.height(8.dp))

        // 경력 2
        Text(text = "회사 B", style = MaterialTheme.typography.h6)
        Text(text = "2018 - 2020", style = MaterialTheme.typography.body2)
        Text(text = "웹 애플리케이션 개발", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun EducationSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 학력 1
        Text(text = "Suwon Univ.", style = MaterialTheme.typography.h6)
        Text(text = "컴퓨터 공학과", style = MaterialTheme.typography.body2)
        Text(text = "Graduation: 2024", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun SkillsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Kotlin, Android, Java, Python, Git, Docker, CI/CD", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}