
package com.curso.turismobtu;

import java.util.List;

public interface DataLoadListener {
    void onDataLoaded(List<PontoTuristico> pontos);

    void onFailure(String errorMessage);
}