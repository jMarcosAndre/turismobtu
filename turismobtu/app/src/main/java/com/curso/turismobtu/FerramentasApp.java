package com.curso.turismobtu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class FerramentasApp {


    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static void openWaze(Context context, double lat, double lng) {
        try {

            String uri = "waze://?ll=" + lat + "," + lng + "&navigate=yes";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.waze");
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(context, "Waze não encontrado. Abrindo Google Maps.", Toast.LENGTH_LONG).show();

            String uri = "geo:0,0?q=" + lat + "," + lng + "(" + "Local Turístico" + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps"); // Força Google Maps


            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Nenhum aplicativo de mapa encontrado.", Toast.LENGTH_LONG).show();
            }
        }
    }
}