package cat.institutmontilivi.llistacomprafirebase.ui.pantalles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.institutmontilivi.llistacomprafirebase.model.Producte
import kotlinx.coroutines.launch

@Composable
fun PantallaAfegeixProductes(
    idLlista:String,
    viewModel: PantallaAfegeixProductesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val estat = viewModel.estat.collectAsState()
    val ambit = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text field for entering product name
        OutlinedTextField(
            value = estat.value.nomNouProducte,
            onValueChange = { viewModel.canviaNomProducte(it) },
            label = { Text("Nom del producte") },
            modifier = Modifier.fillMaxWidth()
        )

        // Integer up-down field for entering quantity
        IntegerUpDownField(
            value = estat.value.quantitatNouProducte,
            onValueChange = { viewModel.canviaQuantitatProducte(it) }
        )

        // Button to add the product
        Button(
            onClick = {
                if (estat.value.nomNouProducte.isNotBlank()) {
                    ambit.launch {
                        viewModel.afegeixProducte()
                    }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Afegir")
        }

        Button(
            onClick = {
                ambit.launch {
                    viewModel.afegir20Productes()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Afegir 20 productes")
        }

        // LazyColumn to display the list of products
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(estat.value.productes) { producte ->
                // Display each product in the list
                ProductItem(producte = producte)
            }
        }
    }
}

@Composable
fun IntegerUpDownField(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onValueChange(value - 1) },
            shape = CircleShape
        ) {
            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Remove")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = value.toString(),
            modifier = Modifier.width(48.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { onValueChange(value + 1) },
            shape = CircleShape
        ) {
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Add")
        }
    }
}

@Composable
fun ProductItem(producte: Producte) {
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
        }
    }
}