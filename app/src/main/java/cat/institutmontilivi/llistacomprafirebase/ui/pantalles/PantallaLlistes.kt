package cat.institutmontilivi.llistacomprafirebase.ui.pantalles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
fun PantallaLlistes(onClicCrea: () -> Unit = {}, onClicLlista: (String) -> Unit = {}, viewModel: PantallaLlistesViewModel = viewModel()) {

    val estat = viewModel.estat.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Llistes de la compra",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displaySmall)

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(estat.value.llistes) { llista ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onClicLlista(llista.id) }, // Clickable card

                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Display ID in small letters
                        Text(
                            text = "ID: ${llista.id}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Display name in big letters
                        Text(
                            text = llista.nom,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

        }

        Button(
            onClick = { onClicCrea() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Crear llista buida", style = MaterialTheme.typography.headlineLarge)
        }
    }
}