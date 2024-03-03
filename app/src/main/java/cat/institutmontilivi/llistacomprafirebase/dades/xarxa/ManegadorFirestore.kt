package cat.institutmontilivi.llistacomprafirebase.dades.xarxa

import android.util.Log
import cat.institutmontilivi.llistacomprafirebase.model.LlistaCompra
import cat.institutmontilivi.llistacomprafirebase.model.Producte
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ManegadorFirestore {
    private val LLISTES = "llistes"
    private val PRODUCTES = "productes"
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun creaLlista(llista: LlistaCompra) {
        val ref = firestore.collection(LLISTES).document()
        val novaLlista = llista.copy(id = ref.id)
        ref.set(novaLlista).await()
    }

    suspend fun creaProducte(producte: Producte): String {
        val ref = firestore.collection(PRODUCTES).document()
        val producteId = ref.id
        val nouProducte = producte.copy(id = producteId)
        ref.set(nouProducte).await()
        return producteId
    }

    suspend fun eliminaLlista(clau: String) {
        firestore.collection(LLISTES).document(clau).delete().await()
    }

    suspend fun afegirProducteALlista(llista: LlistaCompra, producte: Producte) {
        val llistaQuery = firestore.collection(LLISTES)
            .whereEqualTo("id", llista.id).get().await()
        if(!llistaQuery.isEmpty) {
            llistaQuery.documents.forEach {
                it.reference.collection(PRODUCTES).document(producte.id).set(producte).await()
            }
        }
    }

    fun obtenLlistesFlow():
            Flow<List<LlistaCompra>> = callbackFlow {
                val llistesRef = firestore.collection(LLISTES)
                    .orderBy("nom")
                val subscripcio = llistesRef
                    .addSnapshotListener{snapshot, exception ->
                        snapshot?.let{
                            val llistes = mutableListOf<LlistaCompra>()
                            for(document in snapshot){
                                val llista = document.toObject(LlistaCompra::class.java)
                                llista.let { llistes.add(it) }
                            }
                            trySend(llistes)
                        }
                    }
                awaitClose{subscripcio.remove()}
            }

    fun obtenLlistaFlow(idLlista: String): Flow<LlistaCompra> {
        return callbackFlow {
            val llistaRef = firestore.collection(LLISTES).document(idLlista)
            val subscripcio = llistaRef
                .addSnapshotListener { snapshot, exception ->
                    snapshot?.let {
                        val llista = snapshot.toObject(LlistaCompra::class.java)
                        llista?.let { trySend(it) }
                    }
                }
            awaitClose { subscripcio.remove() }
        }
    }

    fun actualitzaNomLlista(idLlista: String, nom: String) {
        firestore.collection(LLISTES).document(idLlista).update("nom", nom)
    }

    suspend fun duplicaLlista(llista: LlistaCompra) {
        val ref = firestore.collection(LLISTES).document()
        val novaLlista = llista.copy(id = ref.id, nom = "${llista.nom} (còpia)")
        ref.set(novaLlista).await()

        val productesRef = firestore.collection(LLISTES).document(llista.id).collection(PRODUCTES)
        val originalProductes = productesRef.get().await()

        originalProductes.documents.forEach { producteDocument ->
            val producte = producteDocument.toObject(Producte::class.java)
            producte?.let {
                // Duplicate the product
                val producteRef = firestore.collection(LLISTES).document(novaLlista.id).collection(PRODUCTES).document(producte.id)
                producteRef.set(it).await()
            }
        }
    }


    suspend fun marcarComprat(idLlista: String, producteId: String, comprat: Boolean) {
        val producteRef = firestore.collection(LLISTES)
            .document(idLlista)
            .collection(PRODUCTES)
            .document(producteId)

        producteRef.update("comprat", comprat).await()
    }

    fun obtenProductesLlistaFlow(idLlista: String): Flow<List<Producte>> {
        return callbackFlow {
            val productesRef = firestore.collection(LLISTES)
                .document(idLlista)
                .collection(PRODUCTES)
                .orderBy("nom")
            val subscripcio = productesRef
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        trySend(emptyList())
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        val productes = mutableListOf<Producte>()
                        for (document in snapshot) {
                            val producte = document.toObject(Producte::class.java)
                            productes.add(producte)
                        }
                        trySend(productes)
                    } else {
                        // If snapshot is null or empty, emit an empty list
                        trySend(emptyList())
                    }
                }
            awaitClose { subscripcio.remove() }
        }
    }

    fun actualitzaCompratProducte(producte: Producte, it: Boolean) {
        Log.d("ManegadorFirestore", "Actualitzant producte")
        firestore.collection(PRODUCTES).document(producte.id).update("comprat", it)
    }
}