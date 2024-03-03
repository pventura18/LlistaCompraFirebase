package cat.institutmontilivi.llistacomprafirebase.ui.pantalles

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cat.institutmontilivi.llistacomprafirebase.dades.xarxa.ManegadorFirestore
import cat.institutmontilivi.llistacomprafirebase.model.LlistaCompra
import cat.institutmontilivi.llistacomprafirebase.navegacio.ArgumentDeNavegacio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cat.institutmontilivi.llistacomprafirebase.model.Producte
import kotlinx.coroutines.launch

class PantallaDetallsLlistaViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val idLlista = savedStateHandle.get<String>(ArgumentDeNavegacio.IdLlista.clau)?:""

    private var _estat = MutableStateFlow<DetallsLlistaEstat>(DetallsLlistaEstat())
    val estat = _estat.asStateFlow()
    lateinit var manegadorFirestore: ManegadorFirestore

    init{
        manegadorFirestore = ManegadorFirestore()
        obtenLlista()
        obtenProductes()
    }

    fun obtenLlista() {
        viewModelScope.launch {
            manegadorFirestore.obtenLlistaFlow(idLlista).collect { llista ->
                _estat.value = _estat.value.copy(llista = llista)
            }
        }
    }

    fun obtenProductes(){
        viewModelScope.launch {
            manegadorFirestore.obtenProductesLlistaFlow(idLlista).collect() { productes ->
                _estat.value = _estat.value.copy(productes = productes)
            }
        }
    }

    fun canviaNomLlista(nom: String){
        viewModelScope.launch {
            manegadorFirestore.actualitzaNomLlista(idLlista, nom)
        }
    }

    fun esborraLlista() {
        viewModelScope.launch {
            manegadorFirestore.eliminaLlista(idLlista)
        }
    }

    fun duplicaLlista(llista: LlistaCompra){
        _estat.value = estat.value.copy(estaCarregant = true)
        viewModelScope.launch {
            manegadorFirestore.duplicaLlista(llista)
            _estat.value = estat.value.copy(estaCarregant = false)
        }
    }

    fun canviaCompratProducte(producte: Producte, comprat: Boolean) {
        _estat.value = estat.value.copy(estaCarregant = true)
        val llistaId = _estat.value.llista.id
        viewModelScope.launch {
            val updatedProductes = _estat.value.productes.map { p ->
                if (p.id == producte.id) {
                    p.copy(comprat = comprat)
                } else {
                    p
                }
            }
            _estat.value = _estat.value.copy(productes = updatedProductes)

            manegadorFirestore.marcarComprat(llistaId, producte.id, comprat)

            manegadorFirestore.actualitzaCompratProducte(producte, comprat)
            _estat.value = estat.value.copy(estaCarregant = false)
        }
    }


    data class DetallsLlistaEstat(
        val estaCarregant:Boolean = true,
        val esErroni:Boolean = false,
        val missatge:String = "",
        val llista: LlistaCompra = LlistaCompra(),
        val productes: List<Producte> = listOf()
    )
}