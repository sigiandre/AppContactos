package pe.edu.appcontactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ListView
import android.widget.Switch
import android.widget.ViewSwitcher
import androidx.appcompat.widget.SearchView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var lista:ListView? = null
    var grid:GridView? = null
    var viewSwitcher:ViewSwitcher? = null

    companion object {
        var contactos:ArrayList<Contacto>? = null
        var adaptador:AdaptadorCustom? = null
        var adaptadorGrid:AdaptadorCustomGrid? = null

        fun agregarContacto(contacto: Contacto) {
            adaptador?.addItem(contacto)
        }

        fun obtenerContacto(index:Int) : Contacto{
            return adaptador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index:Int) {
            adaptador?.removeItem(index)
        }

        fun actualizarContacto(index:Int, nuevoContacto: Contacto) {
            adaptador?.updateItem(index, nuevoContacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Andre","Diaz Quiroz","Programador", 29,74.0F,"Jr. Huiracocha 2307 Dpto 1003 Jesus Maria","952252313","andrew_programador@hotmail.com", R.drawable.profile1))
        contactos?.add(Contacto("Alexandra","Crisosto Silva","Publisher", 20,55.0F,"La Mar 484 Trujillo","996212571","alexandra@hotmail.com", R.drawable.profile2))
        contactos?.add(Contacto("Diana","Lopez Aguilar","Ediciones", 28,85.0F,"Los Eucaliptos 323 California","990321400","diana@gmail.com", R.drawable.profile3))
        contactos?.add(Contacto("Nicole","Kuylen","Programadora", 22,60.0F,"Av Salaverry 1000 San Isidro","999301201","nicole@gmail.com", R.drawable.profile4))
        contactos?.add(Contacto("Belther","Lecca Revilla","Analistas", 24,75.0F,"Calle Amazonas 303 Magdalena","988098321","belther@hotmail.com", R.drawable.profile5))
        contactos?.add(Contacto("Alexandra","Diaz Quiroz","Diseñadora de Moda", 25,55.0F,"Las Palmas Mz X lote 12 Las Palmas del Golf","908765432","alexandra@gmail.com", R.drawable.profile6))
        contactos?.add(Contacto("Claudia","Quiroz Llanos","Empresarial", 24,58.0F,"Jr Huiracocha 2307 Dpto 802 Jesus Maria","901324543","claudia@gmail.com", R.drawable.profile7))
        contactos?.add(Contacto("Stephany","Medina Llanos","Contadora", 25,60.0F,"Calle Riva Aguero 323 Palermo","900345101","stephany@gmail.com", R.drawable.profile8))
        contactos?.add(Contacto("Fredy","Cerron Gomez","Programador", 28,80.0F,"Av Canada 2035 San Borja","980117201","fredydaniel@gmail.com", R.drawable.profile9))

        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid)
        adaptador = AdaptadorCustom(this, contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)

        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        grid?.adapter = adaptadorGrid

        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as SearchView

        val itemSwitch = menu?.findItem(R.id.switchView)
        itemSwitch?.setActionView(R.layout.switch_item)

        val switchView = itemSwitch?.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto..."

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            //preparar los datos
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                //filtrar
                adaptador?.filtrar(newText!!)
                Log.d("ITEMS!", contactos?.count().toString())
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                //filtrar
                return true
            }
        })

        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitcher?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.iNuevo -> {
                val intent  = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }
            else -> { return super.onOptionsItemSelected(item) }
        }
    }

    override fun onResume() {
        super.onResume()

        adaptador?.notifyDataSetChanged()
    }
}