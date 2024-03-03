package cat.institutmontilivi.llistacomprafirebase.ui.pantalles

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.institutmontilivi.llistacomprafirebase.model.Producte
import kotlinx.coroutines.launch

@Composable
fun PantallaDetallsLlista(idLlista:String, onClic: () -> Unit = {}, onClicAfegeixProductes: () -> Unit = {}, viewModel: PantallaDetallsLlistaViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val estat = viewModel.estat.collectAsState()
    val ambit = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ID: ${estat.value.llista.id}")
        OutlinedTextField(
            value = estat.value.llista.nom,
            onValueChange = {
                viewModel.canviaNomLlista(it)
                            },
            label = { Text("Nom de la llista") },
            modifier = Modifier.fillMaxWidth()
        )
        // Display the list of products
        LazyColumn (modifier = Modifier.weight(1f)) {
            items(estat.value.productes) { producte ->
                Card(
                    modifier = Modifier.padding(8.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            // Display the product name in big letters
                            Text(
                                text = producte.nom,
                                fontSize = 20.sp
                            )

                            // Display the product quantity
                            Text(
                                text = "Quantitat: ${producte.quantitat}",
                                fontSize = 14.sp
                            )
                        }
                        // Display the switch to mark the product as bought
                        Switch(
                            checked = producte.comprat,
                            onCheckedChange = {
                                viewModel.canviaCompratProducte(producte, it)
                            }
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                onClicAfegeixProductes()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Afegeix Productes")
        }
        // Button to delete the list
        Button(
            onClick = {
                viewModel.esborraLlista()
                onClic()
                      },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Esborra Llista")
        }

        Button(
            onClick = {
                viewModel.duplicaLlista(estat.value.llista)
                onClic()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Duplica Llista")
        }
    }

}
