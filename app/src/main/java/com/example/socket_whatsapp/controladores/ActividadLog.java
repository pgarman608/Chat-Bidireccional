package com.example.socket_whatsapp.controladores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socket_whatsapp.R;
import com.example.socket_whatsapp.modelos.Informacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ActividadLog extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtipDes;
    private Button btnEntrar;

    private TextView txtMiIp;

    public static Informacion receptor;
    public static Informacion destinatario;


    /**
     * Tendremos este metodo para declarar variables y crear listeners
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO 1.1- Modificaremos la statusbar, actionbar y navigationbar a nuestro gusto
        getWindow().setStatusBarColor(Color.parseColor("#000000"));
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(Color.parseColor("#000000"));
        // TODO 1.2- Inicializaremos los elementos de la interfaz y las variables necesarias
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtipDes = (EditText) findViewById(R.id.txtipdes);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        txtMiIp = (TextView) findViewById(R.id.txtMiIp);
        destinatario = new Informacion();
        // TODO 1.3- Tendremos visible la IP del usuario para que otro pueda verla y acceder a los mensajes 
        txtMiIp.setText("Mi ip: " + getUserIpAddress());
        /**
         * TODO 2- Tendremos un listenner en el botón de entrar que comprobaremos tanto el nombre como de
         * la IP introducida por los editText de la interfaz. Después, iremos con un intent a la pestaña de los mensajes
         */
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipDes = txtipDes.getText().toString();
                String ipYo = getUserIpAddress();
                String nombreYo = txtNombre.getText().toString();
                if (is_Nombre(nombreYo)){
                    if (is_ipv4(ipDes)){
                        receptor = new Informacion(nombreYo,ipYo);
                        destinatario = new Informacion(ipDes);
                        Intent intentMensajes = new Intent(ActividadLog.this , ActivivyMensaje.class);
                        startActivity(intentMensajes);
                    }else{
                        Toast.makeText(ActividadLog.this,"IP incorrecta",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ActividadLog.this,"Nombre incorrecto",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Recogeremos la ip del usuario para utilizarla posteriormente
     * @return Devolveremos la ip encontrada como string
     */
    private String getUserIpAddress(){
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    /**
     * Comprovaremos que la ip introducida se o no una ip valida devolviendo un boleano
     * @param ip
     * @return
     */
    private boolean is_ipv4(String ip){
        boolean aux = false;
        if (!(ip == null || ip.isEmpty())) {
            ip = ip.trim();
            if (!((ip.length() < 6) & (ip.length() > 15))) {
                try {
                    Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
                    Matcher matcher = pattern.matcher(ip);
                    aux = matcher.matches();
                } catch (PatternSyntaxException ex) {
                }
            }
        }
        return aux;
    }

    /**
     * Comporbaremos que el string introducido no esté vacio y tenga texto devolviendo un boleano
     * @param nombre
     * @return
     */
    private boolean is_Nombre(String nombre){
        boolean aux = false;
        if (!(nombre == null || nombre.isEmpty())){
            nombre = nombre.trim();
            if (nombre.length() > 1){
                aux = true;
            }
        }
        return aux;
    }
}