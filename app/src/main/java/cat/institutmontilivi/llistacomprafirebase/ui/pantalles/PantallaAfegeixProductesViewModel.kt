package cat.institutmontilivi.llistacomprafirebase.ui.pantalles

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.institutmontilivi.llistacomprafirebase.dades.xarxa.ManegadorFirestore
import cat.institutmontilivi.llistacomprafirebase.model.LlistaCompra
import cat.institutmontilivi.llistacomprafirebase.model.Producte
import cat.institutmontilivi.llistacomprafirebase.navegacio.ArgumentDeNavegacio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PantallaAfegeixProductesViewModel(savedStateHandle: SavedStateHandle) : ViewModel(){
    private val idLlista = savedStateHandle.get<String>(ArgumentDeNavegacio.IdLlista.clau)?:""

    private var _estat = MutableStateFlow<AfegirProductesEstat>(AfegirProductesEstat())
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

    fun canviaNomProducte(nom: String){
        _estat.value = _estat.value.copy(nomNouProducte = nom)
    }

    fun canviaQuantitatProducte(quantitat: Int){
        if(quantitat < 1) _estat.value = _estat.value.copy(quantitatNouProducte = 1)
        _estat.value = _estat.value.copy(quantitatNouProducte = quantitat)
    }

    suspend fun afegeixProducte() {
        val nouProducte = Producte(
            nom = _estat.value.nomNouProducte,
            quantitat = _estat.value.quantitatNouProducte,
            comprat = false
        )
        val productId = manegadorFirestore.creaProducte(nouProducte)
        manegadorFirestore.afegirProducteALlista(_estat.value.llista, nouProducte.copy(id = productId))
    }

    fun afegir20Productes() {
        viewModelScope.launch {
            repeat(20) {
                val productName = "Product${it + 1}"
                val randomQuantity = (1..10).random()
                afegeixProducte(Producte(nom = productName, quantitat = randomQuantity, comprat = false))
            }
        }
    }

    private suspend fun afegeixProducte(producte: Producte) {
        val productId = manegadorFirestore.creaProducte(producte)
        manegadorFirestore.afegirProducteALlista(_estat.value.llista, producte.copy(id = productId))
    }

    data class AfegirProductesEstat(
        val estaCarregant:Boolean = true,
        val esErroni:Boolean = false,
        val missatge:String = "",
        val nomNouProducte:String = "",
        val quantitatNouProducte:Int = 1,
        val llista: LlistaCompra = LlistaCompra(),
        val productes: List<Producte> = listOf()
    )
}