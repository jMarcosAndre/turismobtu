package com.curso.turismobtu;

import android.content.Context;
import android.widget.Toast;

public class FerramentasApp {
    public static void toast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }
}
