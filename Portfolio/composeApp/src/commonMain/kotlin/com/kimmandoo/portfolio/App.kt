package com.kimmandoo.portfolio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
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
                .fillMaxSize()
                .padding(horizontal = 200.dp).padding(top = 28.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 개인 정보 섹션
            PersonalInfoSection()
            Spacer(modifier = Modifier.height(32.dp))
            // 학력 섹션
            SectionTitle("Education & Certificate")
            RowDivider(Modifier)
            EducationSection()
            Spacer(modifier = Modifier.height(32.dp))
            // 기술 스택 섹션
            SectionTitle("Skills")
            RowDivider(Modifier)
            SkillsSection()
            Spacer(modifier = Modifier.height(32.dp))

            // 경력 섹션
            SectionTitle("Project")
            RowDivider(Modifier)
            ProjectSection()
            Spacer(modifier = Modifier.height(32.dp))
            // 수상
            SectionTitle("Honors & Awards")
            RowDivider(Modifier)
            HonorSection()
            Spacer(modifier = Modifier.height(32.dp))
            RowDivider(Modifier)
            // Other 섹션
            SectionTitle("Others")
            OthersSection()
            Spacer(modifier = Modifier.height(32.dp))
            Text(text="Developed with Kotlin Compose Multi Platform",
                 modifier = Modifier.padding(8.dp),
                 style = MaterialTheme.typography.body2.copy(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Thin)
            )
        }
    }
}

@Composable
fun RowDivider(modifier: Modifier, color: Color = Color.Gray){
    Divider(
        color = Color.Gray, // 원하는 색상으로 변경
        thickness = 1.dp,   // 두께를 1.dp로 설정하여 얇은 Divider 생성
        modifier = modifier
            .fillMaxWidth().padding(bottom = 8.dp) // 가로로 채움
    )
}

@Composable
fun DottedRowDivider(modifier: Modifier, color: Color = Color.Gray) {
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(1.dp)
        .padding(bottom = 8.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val dashWidth = 10f // 점선의 길이
        val gapWidth = 10f  // 점선 사이의 간격

        var startX = 0f
        while (startX < canvasWidth) {
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(startX, canvasHeight / 2),
                end = androidx.compose.ui.geometry.Offset(startX + dashWidth, canvasHeight / 2),
                strokeWidth = canvasHeight
            )
            startX += dashWidth + gapWidth
        }
    }
}

@Composable
fun PersonalInfoSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberImagePainter("https://avatars.githubusercontent.com/u/46841652?v=4")
        Image(
            painter = painter,
            contentDescription = "image",
            Modifier.clip(CircleShape).size(140.dp)
        )
        Text(text = "Mingyu Kim", style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold))
        Text(text = "Android Developer", style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
        Text(text = "mingyu5675@gmail.com", style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(bottom = 8.dp))
        Text(text="- I am an Android developer with a passion for continuous growth and development. \n" +
                  "- I focus on creating interactive services for users and enjoy exploring new technologies. \n" +
                  "- I actively collaborate with my team to solve challenges and prioritize tasks efficiently. \n" +
                  "- I document my experiences with new technologies to share insights, and I regularly attend conferences to stay up to date with the latest trends. \n" +
                  "- Recently, I’ve been focusing on learning Jetpack Compose, CI/CD and writing test code.",
             style = MaterialTheme.typography.body2,
             modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun SectionText(title: String, detail: String, date: String, skills: String, acheivement:String, role: String, modifier: Modifier = Modifier){
    Column {
        Text(text = title, style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(bottom = 2.dp))
        Text(text = detail, style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold))
        Text(text = "Period: $date", style = MaterialTheme.typography.body2)
        Text(text = "Skills: $skills", style = MaterialTheme.typography.body2)
        Text(text = "Role: $role", style = MaterialTheme.typography.body2)
        Text(text = "Achievements: $acheivement", style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(12.dp))
        DottedRowDivider(modifier = modifier.padding(horizontal = 20.dp).padding(bottom = 8.dp))
    }
}


@Composable
fun EducationSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Suwon University, Republic of Korea", style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
        Text(text = "Bachelor’s in Information Security (2018.02 ~ 2024.02)", style = MaterialTheme.typography.body2)
        Text(text = "- GPA: 4.06 / 4.5", style = MaterialTheme.typography.body2)
        Text(text = "- Major GPA: 3.98 / 4.5", style = MaterialTheme.typography.body2, modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = "Industrial Engineer Information Security - issued by Korea Communications Agency, KCA",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "OPIc IH (2024.08.18)",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Samsung SW Academy for Youth (SSAFY) – Mobile Track, 11th Class (2024.01 ~ 2024.12)",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun ProjectSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        SectionText("DRTAA",
                    "- Smart Tour Service Application Using Autonomous Vehicles",
                    "2024.09.02 ~ 2024.10.11",
                    "Android Development, Create Wakeword detection ML model with Tensorflow(1D CNN), Android CI/CD with Gitlab Runner, UnitTest",
                    "awarded 2nd place in the Samsung SW Academy project",
                    "Android Development Leader, AI Model build, Presentation",
            )
        SectionText("Colorpl",
                    "- Show Reservation, Management, Community Integration Platform Application",
                    "2024.07.08 ~ 2024.08.16",
                    "Android Development, Image2Text with VisionAPI - ImageProcessing, OCR with MLkit",
                    "Reduced OpenAI token cost by 30.69%, awarded 2nd place in the Samsung SW Academy project",
                    "Android Development",
            )
        SectionText("Ping",
                    "- Gathering management app with location and map integration",
                    "2024.05.11 ~ 2024.05.24",
                    "Android Development",
                    "Gained experience in Google recommended App architecture, Firebase Tools especially CloudFunction, and writing test cases with Junit",
                    "Android Development Leader, UI/UX Design",
            )
        SectionText("ShyPolarBear",
                    "- Quiz app focused on global warming awareness",
                    "2023.07 ~ 2023.10",
                    "Android Development",
                    "Gained a deeper understanding of REST API and Git branch strategies, Used Multi-module, Hilt for the first time",
                    "Android Development",
                    modifier = Modifier.size(0.dp)
            )
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
fun SkillsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Kotlin, Android, Java, Python, Git, Docker, CI/CD", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun OthersSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Other", style = MaterialTheme.typography.body2)
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