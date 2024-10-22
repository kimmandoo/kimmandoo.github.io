package com.kimmandoo.portfolio

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.AnnotatedString.*
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
    PortfolioTheme {
        Column(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 260.dp).padding(top = 28.dp)
                    .background(Color.White)
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
        Text(text = "Mingyu Kim", style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace ))
        Text(text = "Android Developer", style = MaterialTheme.typography.h6,  modifier = Modifier.padding(bottom = 8.dp))
        Row(modifier = Modifier.padding(bottom = 8.dp)){
            Text(text = "mingyu5675@gmail.com", style = MaterialTheme.typography.subtitle2, modifier = Modifier.padding(horizontal = 8.dp))
            HyperlinkText(input = "@github", "https://github.com/kimmandoo", modifier = Modifier.padding(horizontal = 8.dp))
            HyperlinkText(input = "@linkedin", "https://www.linkedin.com/in/mingyu-kim-400891193/", modifier = Modifier.padding(horizontal = 8.dp))
        }
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
fun HyperlinkText(input: String , uri: String, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    ClickableText(
        modifier = modifier,
        text = AnnotatedString(input),
        style = MaterialTheme.typography.subtitle2,
        onClick = { uriHandler.openUri(uri) } // 여기에 링크 넣기
    )
}

@Composable
fun EducationSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        Text(text = "Suwon University, Republic of Korea", style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Bachelor’s in Information Security (2018.02 ~ 2024.02)", style = MaterialTheme.typography.body2, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "- GPA: 4.06 / 4.5", style = MaterialTheme.typography.body2)
        Text(text = "- Major GPA: 3.98 / 4.5", style = MaterialTheme.typography.body2, modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = "Industrial Engineer Information Security - certificated by Korea Communications Agency, KCA",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "OPIc IH (2024.08.18)",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Samsung SW Academy for Youth (SSAFY) – Mobile Track, 11th Class (2024.01 ~ 2024.12)",
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
fun SkillsSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        Text(text = "Kotlin, Java, Python, Android, Git, Docker, CI/CD", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun SectionText(
    title: String,
    description: String,
    date: String,
    skills: String,
    achievement: String,
    role: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = "- $description",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Role: $role", style = MaterialTheme.typography.body2)
            Text(text = date, style = MaterialTheme.typography.body2)
        }
        Text(text = "Skills: $skills", style = MaterialTheme.typography.body2, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Achievements: $achievement", style = MaterialTheme.typography.body2, modifier = Modifier.padding(bottom = 4.dp))
        Spacer(modifier = Modifier.height(12.dp))
        DottedRowDivider(modifier = modifier.padding(horizontal = 20.dp).padding(bottom = 8.dp))
    }
}

@Composable
fun ProjectSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        SectionText(
            title = "DRTAA",
            description = "Smart Tour Service Application Using Autonomous Vehicles",
            date = "2 Sep. 2024 – 11 Oct. 2024",
            skills = "Android Development, Wakeword detection ML model (Tensorflow 1D CNN), CI/CD (GitLab Runner), Unit Testing",
            achievement = "Awarded 2nd place in Samsung SW Academy project",
            role = "Android Development Lead, AI Model Building, Presentation"
        )
        SectionText(
            title = "Colorpl",
            description = "Show Reservation, Management, Community Integration Platform Application",
            date = "8 Jul. 2024 – 16 Aug. 2024",
            skills = "Android Development, Image2Text with Vision API, OCR with MLKit",
            achievement = "Reduced OpenAI token cost by 30.69%, Awarded 2nd place in Samsung SW Academy project",
            role = "Android Developer"
        )
        SectionText(
            title = "Ping",
            description = "Gathering Management App with Location and Map Integration",
            date = "11 May. 2024 – 24 May. 2024",
            skills = "Android Development, Google Recommended App Architecture, Firebase Tools, Cloud Function, Unit Testing",
            achievement = "Experience in modern Android architecture, Firebase Cloud Function integration",
            role = "Android Development Lead, UI/UX Design"
        )
        SectionText(
            title = "ShyPolarBear",
            description = "Quiz App Focused on Global Warming Awareness",
            date = "Jul. 2023 – Oct. 2023",
            skills = "Android Development, REST API, Git Branch Strategy, Multi-module, Hilt",
            achievement = "First experience using multi-module and Hilt for dependency injection",
            role = "Android Developer",
            modifier = Modifier.size(0.dp)
        )
    }
}

@Composable
fun Prize(title: String, detail: String, date: String){
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween // 좌우 끝에 맞춰 정렬
    ) {
        Column {
            Text(text = title, style = MaterialTheme.typography.subtitle2)
            Text(text = detail, style = MaterialTheme.typography.body2)
        }
        Text(text = date, style = MaterialTheme.typography.body2)
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun HonorSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Prize("Samsung Software Academy For Youth(SSAFY) - Colorpl","• 2nd Prize in Common Project - Hosted by Samsung Electronics","16 Aug. 2024")
        Prize("Samsung Software Academy For Youth(SSAFY) - DRTAA","• 2nd Prize in Specify Project - Hosted by Samsung Electronics","11 Oct. 2024")
    }
}

@Composable
fun OthersSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        Text(text = "Other", style = MaterialTheme.typography.body2)
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