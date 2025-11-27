package com.curso.turismobtu;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FirebaseDataManager {


    private static final String PONTOS_NODE = "pontos_turisticos/pontos_turisticos";
    private static final String TAG = "FirebaseData";
    private final DatabaseReference databaseReference;

    public FirebaseDataManager() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference(PONTOS_NODE);
    }


    public void fetchAllPontos(final DataLoadListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d(TAG, "Conexão OK. Total de itens no nó: " + snapshot.getChildrenCount());

                // Leitura do mapa para evitar erros de desserialização (melhor compatibilidade)
                GenericTypeIndicator<Map<String, PontoTuristico>> t = new GenericTypeIndicator<Map<String, PontoTuristico>>() {};
                Map<String, PontoTuristico> pontosMap = snapshot.getValue(t);

                List<PontoTuristico> pontosList = new ArrayList<>();

                if (pontosMap != null) {
                    // Adiciona todos os valores (objetos PontoTuristico) à lista
                    pontosList.addAll(pontosMap.values());
                } else {
                    Log.d(TAG, "Conexão OK, mas o mapa de pontos está vazio.");
                }

                Log.d(TAG, "Pontos carregados na lista do app: " + pontosList.size());

                listener.onDataLoaded(pontosList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "FALHA DE FIREBASE - Conexão: " + error.getMessage());
                listener.onFailure("Falha ao carregar dados: " + error.getMessage());
            }
        });
    }

    public List<PontoTuristico> filterByCategory(List<PontoTuristico> allPontos, String category) {
        if (category == null || category.equalsIgnoreCase("Todos")) return allPontos;

        return allPontos.stream()
                .filter(p -> p.getCategory() != null && p.getCategory().contains(category))
                .collect(Collectors.toList());
    }
}