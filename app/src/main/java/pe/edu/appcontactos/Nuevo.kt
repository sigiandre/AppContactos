package pe.edu.appcontactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import java.util.ArrayList

class Nuevo : AppCompatActivity() {

    var fotoindex:Int = 0

    val fotos = arrayOf(R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5, R.drawable.profile6, R.drawable.profile7, R.drawable.profile8, R.drawable.profile9)

    var foto:ImageView? = null

    var index:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById<ImageView>(R.id.ivFoto)

        foto?.setOnClickListener {
            seleccionarFoto()
        }

        //Reconocer la accion de nuevo vs editar
        if (intent.hasExtra("ID")){
            index = intent.getStringExtra("ID").toString().toInt()
            rellenarDatos(index)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){

            android.R.id.home -> {
                finish()
                return true
            }

            R.id.iCrearNuevo -> {
                //Crear un nuevo elemento de tipo contacto
                val nombre = findViewById<EditText>(R.id.tvNombre)
                val apellidos = findViewById<EditText>(R.id.tvApellido)
                val empresa = findViewById<EditText>(R.id.tvEmpresa)
                val edad = findViewById<EditText>(R.id.tvEdad)
                val peso = findViewById<EditText>(R.id.tvPeso)
                val telefono = findViewById<EditText>(R.id.tvTelefono)
                val email = findViewById<EditText>(R.id.tvEmail)
                val direccion = findViewById<EditText>(R.id.tvDireccion)

                //validar campos
                var campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellidos.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(direccion.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())

                var flag = 0

                for (campo in campos) {
                    if (campo.isNullOrEmpty())
                        flag++
                }

                if (flag > 0) {
                    Toast.makeText(this,"Rellena todos los campos", Toast.LENGTH_SHORT).show()
                }else{
                    if(index > -1) {
                        MainActivity.actualizarContacto(index, Contacto(campos.get(0), campos.get(1), campos.get(2), campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6), campos.get(7), obtenerFoto(fotoindex)))
                    }else {
                        MainActivity.agregarContacto(Contacto(campos.get(0), campos.get(1), campos.get(2), campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6), campos.get(7), obtenerFoto(fotoindex)))
                    }
                    finish()
                    Log.d("No Elementos", MainActivity.contactos?.count().toString())
                }
                return true
            }
            else -> { return super.onOptionsItemSelected(item) }
        }
        return super.onOptionsItemSelected(item)
    }

    fun seleccionarFoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una imagen de perfil")

        val adaptadorDialogo = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Foto 01")
        adaptadorDialogo.add("Foto 02")
        adaptadorDialogo.add("Foto 03")
        adaptadorDialogo.add("Foto 04")
        adaptadorDialogo.add("Foto 05")
        adaptadorDialogo.add("Foto 06")
        adaptadorDialogo.add("Foto 07")
        adaptadorDialogo.add("Foto 08")
        adaptadorDialogo.add("Foto 09")

        builder.setAdapter(adaptadorDialogo) {
            dialog, which ->
            fotoindex = which
            foto?.setImageResource(obtenerFoto(fotoindex))
        }

        builder.setNegativeButton("Cancelar") {
            dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun obtenerFoto(index:Int) : Int {
        return fotos.get(index)
    }

    fun rellenarDatos(index:Int) {
        val contacto = MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<EditText>(R.id.tvNombre)
        val tvApellidos = findViewById<EditText>(R.id.tvApellido)
        val tvEmpresa = findViewById<EditText>(R.id.tvEmpresa)
        val tvEdad = findViewById<EditText>(R.id.tvEdad)
        val tvPeso = findViewById<EditText>(R.id.tvPeso)
        val tvTelefono = findViewById<EditText>(R.id.tvTelefono)
        val tvEmail = findViewById<EditText>(R.id.tvEmail)
        val tvDireccion = findViewById<EditText>(R.id.tvDireccion)
        val ivFoto = findViewById<ImageView>(R.id.ivFoto)

        tvNombre.setText(contacto.nombre, TextView.BufferType.EDITABLE)
        tvApellidos.setText(contacto.apellidos, TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa, TextView.BufferType.EDITABLE)
        tvEdad.setText(contacto.edad.toString(), TextView.BufferType.EDITABLE)
        tvPeso.setText(contacto.peso.toString(), TextView.BufferType.EDITABLE)
        tvTelefono.setText(contacto.telefono, TextView.BufferType.EDITABLE)
        tvEmail.setText(contacto.email, TextView.BufferType.EDITABLE)
        tvDireccion.setText(contacto.direccion, TextView.BufferType.EDITABLE)
        ivFoto.setImageResource(contacto.foto)

        var posicion = 0

        for (foto in fotos) {
            if (contacto.foto == foto) {
                fotoindex = posicion
            }
            posicion++
        }
    }
}