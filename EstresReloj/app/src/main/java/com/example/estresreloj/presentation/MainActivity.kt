package com.example.estresreloj.presentation

import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.*
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.estresreloj.R
import com.example.estresreloj.presentation.theme.EstresRelojTheme
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            WearAppEntry()
        }
    }
}

@Composable
fun WearAppEntry() {
    EstresRelojTheme {
        MainAppWithTabs()
    }
}

@Composable
fun MainAppWithTabs() {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 6 })
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageCount: Int
                get() = pagerState.pageCount
            override val selectedPage: Int
                get() = pagerState.currentPage
            override val pageOffset: Float
                get() = pagerState.currentPageOffsetFraction
        }
    }

    Scaffold(
        // Temporizador removido para limpiar la interfaz
        pageIndicator = { HorizontalPageIndicator(pageIndicatorState) }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> PrimeraPestanaContent()
                1 -> EstadoCultivoTab()
                2 -> AlertaDeteccionTab()
                3 -> HistorialTab()
                4 -> AlertasTab()
                5 -> RecordatoriosTab()
            }
        }
    }
}

@Composable
fun PrimeraPestanaContent() {
    val context = LocalContext.current
    val hora = LocalTime.now().hour
    val saludo = when (hora) {
        in 5..11 -> "¡Buenos días!"
        in 12..17 -> "¡Buenas tardes!"
        else -> "¡Buenas noches!"
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = saludo,
                style = MaterialTheme.typography.title2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Proyecto RECA",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.drone_de_camara),
                contentDescription = "Imagen principal del proyecto",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    Toast.makeText(context, "Botón presionado!", Toast.LENGTH_SHORT).show()
                    context.getSystemService(Vibrator::class.java)
                        ?.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Iniciar")
                Spacer(Modifier.width(6.dp))
                Text("Iniciar")
            }
        }
    }
}

@Composable
fun EstadoCultivoTab() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Zona Norte",
                style = MaterialTheme.typography.title2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.img_one),
                contentDescription = "Estado de la planta",
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Estado: Saludable",
                style = MaterialTheme.typography.body1,
                color = Color(0xFF4CAF50),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "27 May, 14:32",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AlertaDeteccionTab() {
    val mostrarDetalles = remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (mostrarDetalles.value) {
        DetallesDesglose()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_two),
                    contentDescription = "Alerta de plaga",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "⚠ Red Rot detectado",
                    color = Color.Red,
                    style = MaterialTheme.typography.title2,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Hace 5 minutos",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Chip(
                    onClick = {
                        mostrarDetalles.value = true
                        context.getSystemService(Vibrator::class.java)
                            ?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                    },
                    label = { Text("Ver detalles", textAlign = TextAlign.Center) },
                    icon = { Icon(Icons.Filled.Info, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(0.6f)
                )
            }
        }
    }
}

@Composable
fun DetallesDesglose() {
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Detalles del Evento",
                style = MaterialTheme.typography.title1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        val detalles = listOf(
            "Zona: N-2",
            "Tipo: Plaga Red Rot",
            "Cambio de temperatura detectado",
            "Hora: 14:25",
            "Fecha: 27 May 2025",
            "Nivel de humedad: Crítico",
            "Acción sugerida: Revisión urgente"
        )
        items(detalles.size) { index ->
            Text(
                text = detalles[index],
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun HistorialTab() {
    val historial = listOf(
        "✔ Saludable - 27/05" to Color(0xFF4CAF50),
        "✘ Red Rot - 25/05" to Color.Red,
        "✔ Saludable - 23/05" to Color(0xFF4CAF50)
    )
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Historial",
                style = MaterialTheme.typography.title2.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(historial.size) { index ->
            val (texto, color) = historial[index]
            Text(
                text = texto,
                style = MaterialTheme.typography.body2,
                color = color,
                modifier = Modifier.padding(vertical = 6.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AlertasTab() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(Modifier.height(12.dp))
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Icono de alertas",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colors.secondary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "↑ Mosaic común esta semana",
                style = MaterialTheme.typography.body1,
                color = Color(0xFFFFA000),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Revisa tus cultivos",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RecordatoriosTab() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Recordatorios",
                style = MaterialTheme.typography.title2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Icon(
                imageVector = Icons.Filled.Alarm,
                contentDescription = "Icono de recordatorio",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colors.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Cada 3 días",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { /* squi voy a meter una funcionalidad no olviedarr   */ },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Configurar")
            }
        }
    }
}

@Composable
fun InfoTabContent(title: String, message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.title2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    EstresRelojTheme {
        MainAppWithTabs()
    }
}
