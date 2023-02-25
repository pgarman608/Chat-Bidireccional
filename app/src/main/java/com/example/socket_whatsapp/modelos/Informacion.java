package com.example.socket_whatsapp.modelos;

import java.io.Serializable;

/**
 * Este clase la utilizaremos para los usuarios y los mensajes
 */
public class Informacion implements Serializable {
    String nombre;
    String ip;
    String texto;
    boolean usuario;

    public Informacion(){

    }

    public Informacion(String nombre, String ip){
        this.ip = ip;
        this.nombre = nombre;
        this.texto = "";
        this.usuario = true;
    }
    public Informacion(String ip){
        this.ip = ip;
        this.nombre = "";
        this.texto = "";
        this.usuario = false;
    }

    public Informacion(String nombre, String ip, String texto) {
        this.nombre = nombre;
        this.ip = ip;
        this.texto = texto;
        this.usuario = true;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isUsuario() {
        return usuario;
    }

    public void setUsuario(boolean usuario) {
        this.usuario = usuario;
    }
}
