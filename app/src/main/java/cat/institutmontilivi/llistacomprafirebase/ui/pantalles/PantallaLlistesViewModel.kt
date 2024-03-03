package cat.institutmontilivi.llistacomprafirebase.ui.pantalles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.institutmontilivi.llistacomprafirebase.dades.xarxa.ManegadorFirestore
import cat.institutmontilivi.llistacomprafirebase.model.LlistaCompra
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PantallaLlistesViewModel: ViewModel(){

    private var _estat = MutableStateFlow<LlistesEstat>(LlistesEstat())
    val estat = _estat.asStateFlow()
    lateinit var manegadorFirestore: ManegadorFirestore

    init{
        manegadorFirestore = ManegadorFirestore()
        obtenLlistes()
    }

    private fun obtenLlistes(){
        _estat.value = estat.value.copy(estaCarregant = true)
        viewModelScope.launch{
            manegadorFirestore.obtenLlistesFlow()
                .collect{
                    _estat.value = estat.value.copy(estaCarregant = false, llistes = it)
                }
        }
    }

    data class LlistesEstat(
        val estaCarregant:Boolean = true,
        val esErroni:Boolean = false,
        val missatge:String = "",
        val llistes: List<LlistaCompra> = listOf()
    )
}