package com.kimmandoo.portfolio

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 개인 정보 섹션
            PersonalInfoSection()

            Spacer(modifier = Modifier.height(16.dp))

            // 경력 섹션
            SectionTitle("Experiences")
            ExperienceSection()

            Spacer(modifier = Modifier.height(16.dp))

            // 학력 섹션
            SectionTitle("Educate")
            EducationSection()

            Spacer(modifier = Modifier.height(16.dp))

            // 기술 스택 섹션
            SectionTitle("Skills")
            SkillsSection()
        }
    }
}

@Composable
fun PersonalInfoSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 이름 및 사진
        Image(painterResource(Res.drawable.compose_multiplatform),
              modifier = Modifier.size(100.dp),
              contentDescription = "Profile Picture"
        )
        Text(text = "홍길동", style = MaterialTheme.typography.h5)
        Text(text = "소프트웨어 엔지니어", style = MaterialTheme.typography.body1)
        Text(text = "email@example.com", style = MaterialTheme.typography.body2)
        Text(text = "+82 10-1234-5678", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun ExperienceSection() {
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
        Text(text = "대학교 A", style = MaterialTheme.typography.h6)
        Text(text = "컴퓨터 공학과", style = MaterialTheme.typography.body2)
        Text(text = "졸업: 2018", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun SkillsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Kotlin, Java, Python, Git, Docker, CI/CD", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}