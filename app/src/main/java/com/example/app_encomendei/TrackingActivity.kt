package com.example.app_encomendei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_encomendei.data.model.TrackingInfo
import com.example.app_encomendei.ui.theme.App_encomendeiTheme
import com.example.app_encomendei.viewmodel.TrackingViewModel
import kotlinx.coroutines.flow.MutableStateFlow


class TrackingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Criando o ViewModel corretamente
        val viewModel: TrackingViewModel by viewModels()

        setContent {
            App_encomendeiTheme {
                Scaffold() { innerPadding ->
                    HomeScreen(
                        padding = innerPadding,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: TrackingViewModel
) {

    var trackingCode by remember { mutableStateOf("") }
    val trackingInfo by viewModel.trackingInfo.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF6E7777), Color(0xFF02335B))
                )
            )
            .padding(0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = trackingCode,
                onValueChange = { trackingCode = it },
                label = { Text("Digite aqui seu código de rastreio") },
                placeholder = { Text("EX: AB123456789BR") },
                modifier = Modifier
                    .padding(bottom = 70.dp),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text
                ),
                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1E88E5),
                    unfocusedBorderColor = Color(0xFF220556),
                    cursorColor = Color(0xFF03A9F4),
                    focusedLabelColor = Color(0xFF03A9F4),
                    unfocusedLabelColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White,
                    errorPlaceholderColor = Color.Red,
                    disabledPlaceholderColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .height(50.dp)
                    .wrapContentWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White,
                ),
                enabled = true,
                onClick = {
                    viewModel.fetchTrackin(trackingCode)
                }
            ) {
                Text(
                    text = "Rastrear",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }

            //Exibir informações de Rastreamento

            trackingInfo?.let {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text("Status: ${it.status}", color = Color.White)
                    Text("Local: ${it.location}", color = Color.White)
                    Text("Última Atualização: ${it.date}", color = Color.White)
                }
            } ?: Text(
                "Nenhum Rastreamento Encontrado",
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val fakeViewModel = object : TrackingViewModel() {
        override val trackingInfo = MutableStateFlow(
            TrackingInfo("Objeto", "Objeto em Transito", "07/02/2025", "São Paulo")
        )
    }
    App_encomendeiTheme {
        HomeScreen(
            padding = PaddingValues(0.dp),
            viewModel = fakeViewModel
        )
    }
}