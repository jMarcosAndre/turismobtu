
package com.curso.turismobtu;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FirebaseDataManager {

    private static final String PONTOS_NODE = "pontos_turisticos";
    private final DatabaseReference databaseReference;

    public FirebaseDataManager() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference(PONTOS_NODE);
    }


    public void fetchAllPontos(final DataLoadListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PontoTuristico> pontosList = new ArrayList<>();
                for (DataSnapshot pontoSnapshot : snapshot.getChildren()) {
                    PontoTuristico ponto = pontoSnapshot.getValue(PontoTuristico.class);
                    if (ponto != null) {
                        pontosList.add(ponto);
                    }
                }
                listener.onDataLoaded(pontosList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure("Falha ao carregar dados: " + error.getMessage());
            }
        });
    }


    public List<PontoTuristico> filterByCategory(List<PontoTuristico> allPontos, String category) {
        if (category == null || category.equalsIgnoreCase("todos")) return allPontos;

        return allPontos.stream()
                .filter(p -> p.category != null && p.category.contains(category))
                .collect(Collectors.toList());
    }
}
