package cat.institutmontilivi.llistacomprafirebase.ui.pantalles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Preview
@Composable
fun PantallaCreaLlista(
    //onListaCreada: (String, String) -> Unit
    viewModel: PantallaCreaLlistaViewModel = viewModel(),
    onClic: () -> Unit = {}
){
    val estat = viewModel.estat
    val ambit = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = estat.nomLlista,
            onValueChange = { viewModel.canviaNomLlista(it) },
            label = { Text("Nom de la llista") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (estat.nomLlista.isNotBlank()){
                    ambit.launch{
                        viewModel.creaLlista()
                    }
                }
                onClic()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Crear LLista")
        }
    }
}