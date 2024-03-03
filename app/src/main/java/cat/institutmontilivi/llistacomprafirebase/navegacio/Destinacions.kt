package cat.institutmontilivi.llistacomprafirebase.navegacio

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class CategoriaDeNavegacio(
    val rutaPrevia: String,
    val icona: ImageVector,
    val titol: String
){
    Portada ("Portada", Icons.Default.AccountBox, "Portada"),
    LlistesCompra("LlistesCompra", Icons.Default.AddCircle,"Llistes de la compra"),
}

sealed class Destinacio(
    val rutaBase: String,
    val argumentsDeNavegacio: List <ArgumentDeNavegacio> = emptyList()
)
{

    val navArgs = argumentsDeNavegacio.map { it.toNavArgument()}
    /**
     * Propietat que crea l'string amb la ruta base i tots els arguments separats per barres
     */
    val rutaGenerica = run {
        //cal construir un string tipus: rutabase/{arg1}/{arg2} ...
        val clausArguments = argumentsDeNavegacio.map{"{${it.clau}}"}
        listOf(rutaBase)
            .plus(clausArguments)
            .joinToString("/")
    }

    object Portada: Destinacio(CategoriaDeNavegacio.Portada.rutaPrevia+"/Inici")
    object LlistesCompra:Destinacio(CategoriaDeNavegacio.LlistesCompra.rutaPrevia+"/Inici")
    object CreaLlistaCompra:Destinacio(CategoriaDeNavegacio.LlistesCompra.rutaPrevia+"/CreaLlistaCompra")
    object LlistaCompra:Destinacio(CategoriaDeNavegacio.LlistesCompra.rutaPrevia+"/LlistaCompra",listOf(ArgumentDeNavegacio.IdLlista)){
        fun CreaRutaAmbArguments(idLlista: String) = if (idLlista.isNullOrEmpty()) "$rutaBase/ " else "$rutaBase/$idLlista"
    }
    object AfegeixProductes:Destinacio(CategoriaDeNavegacio.LlistesCompra.rutaPrevia+"/AfegeixProductes",listOf(ArgumentDeNavegacio.IdLlista)){
        fun CreaRutaAmbArguments(idLlista: String) = if (idLlista.isNullOrEmpty()) "$rutaBase/ " else "$rutaBase/$idLlista"
    }
}

enum class ArgumentDeNavegacio (val clau: String, val tipus: NavType<*>){
    IdLlista("IdLlista", NavType.StringType);

    fun toNavArgument (): NamedNavArgument {
        return navArgument(clau) {type = tipus}
    }
}