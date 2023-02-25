package com.example.socket_whatsapp.controladores;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socket_whatsapp.R;
import com.example.socket_whatsapp.adaptadores.RAdapterMsg;
import com.example.socket_whatsapp.modelos.Informacion;
import com.example.socket_whatsapp.modelos.Interconexion;

import java.util.ArrayList;
import java.util.List;

public class ActivivyMensaje extends AppCompatActivity {
    private RecyclerView rvMensaje;
    private Button btnEnviar;
    private EditText etMensaje;

    private RAdapterMsg radapterMsg;
    private ArrayList<Informacion> listInformacion;

    private Interconexion interconexion;


    /**
     * Tendremos este metodo para declarar variables y crear listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        //TODO 3.1- LLamaremos al metodo que modifica las bars
        modInterfaz();
        //TODO 3.2- Inicializaremos los elementos de la interfaz y las variables necesarias
        rvMensaje = (RecyclerView) findViewById(R.id.rvMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        etMensaje = (EditText) findViewById(R.id.txtMensaje);
        interconexion = new Interconexion();
        listInformacion = new ArrayList<>();
        //TODO 3.3- Crearemos el adapatdor del reclicler view
        radapterMsg = new RAdapterMsg(listInformacion);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMensaje.setAdapter(radapterMsg);
        rvMensaje.setLayoutManager(layoutManager);
        //TODO 3.4- Tendremos un listenner al bóton para que envie el mensaje al otro dispositivo
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crearemos el mensaje
                Informacion msgAux = new Informacion(ActividadLog.receptor.getNombre(),ActividadLog.receptor.getIp(),etMensaje.getText().toString());
                //Guardaremos el mensaje en la lista
                listInformacion.add(msgAux);
                radapterMsg.setListInformacion(listInformacion);
                //Enviaremos el mensaje al usuario
                interconexion.exportarMSG(msgAux,ActividadLog.destinatario.getIp());

            }
        });
        //TODO 3.5- Tendremos un hilo para leer los mensajes e guardarlos
        interconexion.importarMSG(new Runnable() {
            @Override
            public void run() {
                listInformacion.add(interconexion.getTxtMSG());
                radapterMsg.setListInformacion(listInformacion);
            }
        });
    }

    /**
     * Este método nos permite salir de los mensajes cuando pulsemos el botón del menú de arriba
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    /**
     * Este método lo llamaremos para modificar la statusbar y actionbar
     */
    private void modInterfaz(){
        getWindow().setStatusBarColor(Color.parseColor("#000000"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#F9031C'>Esperando MSG</font>"));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setNavigationBarColor(Color.parseColor("#000000"));
    }


    /**
     * Cerraremos el socket servidor cuando entre en la actividad
     */
    @Override
    protected void onResume() {
        super.onResume();
        interconexion.openServer();
    }
    /**
     * Cerraremos el socket servidor cuando se salga de la actividad
     */
    @Override
    protected void onStop() {
        super.onStop();
        interconexion.cerrarServer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        interconexion.cerrarServer();
    }
}