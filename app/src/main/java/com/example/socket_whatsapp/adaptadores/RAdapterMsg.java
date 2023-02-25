package com.example.socket_whatsapp.adaptadores;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socket_whatsapp.R;
import com.example.socket_whatsapp.modelos.Informacion;

import java.util.List;

/**
 * Este es el adaptador que insertará los mensajes en un reciclerview
 */
public class RAdapterMsg extends RecyclerView.Adapter<RAdapterMsg.RHolderMsg>{
    //Lista de los mensajes
    List<Informacion> listInformacion;

    /**
     * Setter de la lista pero con el metodo que notifica el que se han modificado mensajes
     * @param listInformacion
     */
    public void setListInformacion(List<Informacion> listInformacion) {
        this.listInformacion = listInformacion;
        notifyDataSetChanged();
    }
    public RAdapterMsg(List<Informacion> listInformacion){
        this.listInformacion = listInformacion;

    }

    @NonNull
    @Override
    public RHolderMsg onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_item_list,parent, false);
        RHolderMsg recyclerHolder = new RHolderMsg(view);

        return recyclerHolder;
    }

    /**
     * Metodo que representará los mensajes en el recicler view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RHolderMsg holder, int position) {
        Informacion mensaje = listInformacion.get(position);
        //Si la informacion es del receptor se escribira en un view con sus textview y si es del receptor
        //en otro
        if (mensaje.isUsuario()){
            holder.hideMD();
            holder.txtNomDes.setText(mensaje.getNombre());
            holder.txtMSGDes.setText(mensaje.getTexto());
        }else{
            holder.hideMR();
            holder.txtNomRec.setText(mensaje.getNombre());
            holder.txtMSGRec.setText(mensaje.getTexto());
        }
    }

    @Override
    public int getItemCount() {
        return listInformacion.size();
    }

    public class RHolderMsg extends RecyclerView.ViewHolder{
        public TextView txtNomDes;
        public TextView txtMSGDes;
        public TextView txtNomRec;
        public TextView txtMSGRec;

        public View viewMD;
        public View viewMR;

        public RHolderMsg(@NonNull View itemView) {
            super(itemView);
            txtNomDes = (TextView) itemView.findViewById(R.id.textNombreMR);
            txtMSGDes = (TextView) itemView.findViewById(R.id.textMensajeMR);
            txtNomRec = (TextView) itemView.findViewById(R.id.textNombreMD);
            txtMSGRec = (TextView) itemView.findViewById(R.id.textMensajeMD);

            viewMD = (View) itemView.findViewById(R.id.viewMD);
            viewMR = (View) itemView.findViewById(R.id.viewMR);
        }

        /**
         * Tendremos 2 metodos para hacer invisibles partes de los mensajes
         */
        public void hideMR(){
            viewMR.setVisibility(View.INVISIBLE);
            txtMSGDes.setVisibility(View.INVISIBLE);
            txtNomDes.setVisibility(View.INVISIBLE);
            viewMD.setVisibility(View.VISIBLE);
            txtMSGRec.setVisibility(View.VISIBLE);
            txtNomRec.setVisibility(View.VISIBLE);
        }
        public void hideMD(){
            viewMD.setVisibility(View.INVISIBLE);
            txtMSGRec.setVisibility(View.INVISIBLE);
            txtNomRec.setVisibility(View.INVISIBLE);
            viewMR.setVisibility(View.VISIBLE);
            txtMSGDes.setVisibility(View.VISIBLE);
            txtNomDes.setVisibility(View.VISIBLE);
        }
    }
}