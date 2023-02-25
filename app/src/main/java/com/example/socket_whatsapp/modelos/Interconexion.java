package com.example.socket_whatsapp.modelos;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import android.os.Handler;

/**
 * Esta clase la utilizaremos para hacer la conexion con otro dispositivo
 */
public class Interconexion {
    public static final int PUERTO = 33333;
    //El handler lo utilizaremos para hacer cambios en la interfaz
    private Handler handler;
    private Thread hiloExportar;
    private Thread hiloImportar;
    private Informacion txtMSG;

    private ServerSocket serverSocket;
    private boolean exitServer;

    public Interconexion(){
        handler = new Handler();
        exitServer =true;
    }

    /**
     * Este método lo usaremos para cuando queramos enviar los mensajes a al otro usuario.
     * @param datos
     * @param ip
     */
    public void exportarMSG(Informacion datos, String ip){
        hiloExportar = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Crearmos el socket con el puerto que usaremos y la IP introducida por parametro
                    Socket socketEnviar = new Socket(ip, PUERTO);
                    //Crearemos una conexion de salida con el socket
                    ObjectOutputStream paqueteria = new ObjectOutputStream(socketEnviar.getOutputStream());
                    //Enviaremos la información introducida por parametros
                    paqueteria.writeObject(datos);
                    //Cerraremos el socket
                    socketEnviar.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        hiloExportar.start();
    }

    /**
     *  Este método lo usaremos para recoger y mostrar los mensajes en el runnable introducido por parámetro
     * @param run
     */
    public void importarMSG(Runnable run){
        hiloImportar = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Crearemos el server socket para leer la información
                    serverSocket = new ServerSocket(PUERTO);
                    //Tendremos un bucle infinito para que siempre esté recogiendo información
                    while (exitServer){
                        //Preguntaré si el puerto esta en uso
                        Socket cliente = serverSocket.accept();
                        //Crearemos una conexion de entrada con el socket
                        ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
                        //Leeremos la información del la conexion
                        txtMSG = (Informacion) flujoEntrada.readObject();
                        txtMSG.setUsuario(false);
                        //Representaremos por la actividad la información
                        handler.post(run);
                        //Cerraremos la información
                        cliente.close();
                        flujoEntrada.close();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        hiloImportar.start();
    }

    /**
     * Recogeremos la informacion recogida del flujo de entrada
     * @return
     */
    public Informacion getTxtMSG() {
        return txtMSG;
    }

    /**
     * Cerraremos el Bucle del socket server cuando usemos este metodo
     */
    public void cerrarServer(){
        this.exitServer = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abrir el socket server
     */
    public void openServer(){
        this.exitServer = true;
    }
}
