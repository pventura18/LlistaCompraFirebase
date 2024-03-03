package cat.institutmontilivi.llistacomprafirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cat.institutmontilivi.llistacomprafirebase.ui.Aplicacio
import cat.institutmontilivi.llistacomprafirebase.ui.PantallaDeLAplicacio
import cat.institutmontilivi.llistacomprafirebase.ui.theme.LlistaCompraFirebaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaDeLAplicacio {
                Aplicacio()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LlistaCompraFirebaseTheme {
        Greeting("Android")
    }
}