package cat.institutmontilivi.llistacomprafirebase.navegacio

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import cat.institutmontilivi.llistacomprafirebase.dades.xarxa.ManegadorFirestore
import cat.institutmontilivi.llistacomprafirebase.ui.pantalles.PantallaAfegeixProductes
import cat.institutmontilivi.llistacomprafirebase.ui.pantalles.PantallaCreaLlista
import cat.institutmontilivi.llistacomprafirebase.ui.pantalles.PantallaDetallsLlista
import cat.institutmontilivi.llistacomprafirebase.ui.pantalles.PantallaLlistes
import cat.institutmontilivi.llistacomprafirebase.ui.pantalles.Portada
import com.google.firebase.auth.FirebaseUser

@Composable
fun GrafDeNavegacio (controladorDeNavegacio: NavHostController = rememberNavController())
{
    val manegadorFirestore = ManegadorFirestore()//(LocalContext.current)


    val navegaEnrera: ()->Unit = {controladorDeNavegacio.popBackStack()}

    NavHost(navController = controladorDeNavegacio,
        startDestination = CategoriaDeNavegacio.Portada.rutaPrevia)
    {
        navigation(startDestination = Destinacio.Portada.rutaBase,route = CategoriaDeNavegacio.Portada.rutaPrevia )
        {
            composable(route = Destinacio.Portada.rutaGenerica){
                Portada()
            }
        }
        navigation(startDestination = Destinacio.LlistesCompra.rutaBase,route = CategoriaDeNavegacio.LlistesCompra.rutaPrevia )
        {
            composable(route = Destinacio.LlistesCompra.rutaGenerica){
                PantallaLlistes(onClicCrea = {controladorDeNavegacio.navigate(Destinacio.CreaLlistaCompra.rutaGenerica)}, onClicLlista = {idLlista:String -> controladorDeNavegacio.navigate(Destinacio.LlistaCompra.CreaRutaAmbArguments(idLlista))})
            }
            composable(route = Destinacio.CreaLlistaCompra.rutaGenerica){
                PantallaCreaLlista(onClic = navegaEnrera)
            }
            composable(route = Destinacio.LlistaCompra.rutaGenerica){
                val idLlista = it.arguments?.getString(ArgumentDeNavegacio.IdLlista.clau) ?: ""
                requireNotNull(idLlista)
                Log.i("GrafDeNavegacio", "idLlista: $idLlista")
                PantallaDetallsLlista(onClic = navegaEnrera,
                    onClicAfegeixProductes = {controladorDeNavegacio.navigate(Destinacio.AfegeixProductes.CreaRutaAmbArguments(idLlista))},
                    idLlista = idLlista)
            }
            composable(route = Destinacio.AfegeixProductes.rutaGenerica){
                val idLlista = it.arguments?.getString(ArgumentDeNavegacio.IdLlista.clau) ?: ""
                requireNotNull(idLlista)
                Log.i("GrafDeNavegacio", "idLlista: $idLlista")
                PantallaAfegeixProductes(idLlista = idLlista)
            }
        }
    }
}