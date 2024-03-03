package cat.institutmontilivi.llistacomprafirebase.ui.pantalles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.institutmontilivi.llistacomprafirebase.dades.xarxa.ManegadorFirestore
import cat.institutmontilivi.llistacomprafirebase.model.LlistaCompra
import cat.institutmontilivi.llistacomprafirebase.model.Producte
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PantallaCreaLlistaViewModel : ViewModel() {
    var estat by mutableStateOf(CreaLlistesEstat())
        private set
    lateinit var manegadorFirestore: ManegadorFirestore

    init{
        manegadorFirestore = ManegadorFirestore()
        estat = estat.copy(nomLlista = "")
    }

    fun canviaNomLlista(nom: String){
        estat = estat.copy(nomLlista = nom)
    }

    suspend fun creaLlista(){
        val llista = LlistaCompra(nom = estat.nomLlista)
        manegadorFirestore.creaLlista(llista)
    }



    data class CreaLlistesEstat(
        val nomLlista: String = ""
    )
}