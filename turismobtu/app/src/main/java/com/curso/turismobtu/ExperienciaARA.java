package com.curso.turismobtu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ExperienciaARA extends AppCompatActivity {

    public static Intent intent(Context c, String placeName) {
        Intent i = new Intent(c, ExperienciaARA.class);
        i.putExtra("placeName", placeName);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_experience);

        String name = getIntent().getStringExtra("placeName");
        ((TextView)findViewById(R.id.tv_ar_title)).setText("AR • " + (name != null ? name : "Local"));
    }
}
