package com.example.evalandroidstudio.data.repository;

import com.example.evalandroidstudio.model.BienImmobilier;
import com.example.evalandroidstudio.model.Piece;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BienImmoRepo {

    private final MutableLiveData<List<BienImmobilier>> biens = new MutableLiveData<>(new ArrayList<>());

    // 🔹 Constructeur injectable pour Hilt
    @Inject
    public BienImmoRepo() {
        // Optionnel : initialiser avec quelques biens pour test
    }

    // 🔹 Retourne tous les biens
    public LiveData<List<BienImmobilier>> getBiens() {
        return biens;
    }

    // 🔹 Ajoute un nouveau bien
    public void ajouterBien(BienImmobilier bien) {
        List<BienImmobilier> current = biens.getValue();
        if (current != null) {
            current.add(bien);
            biens.setValue(current);
        }
    }

    // 🔹 Ajoute une pièce à un bien existant
    public void ajouterPieceAuBien(BienImmobilier bien, Piece piece) {
        if (bien != null && piece != null) {
            bien.ajouterPiece(piece);

            // Met à jour la LiveData pour notifier les observers
            List<BienImmobilier> current = biens.getValue();
            if (current != null) {
                biens.setValue(current);
            }
        }
    }

    // 🔹 Récupère un bien par son id (optionnel, utile pour le détail)
    public BienImmobilier getBienById(String id) {
        if (id == null) return null;
        List<BienImmobilier> current = biens.getValue();
        if (current != null) {
            for (BienImmobilier bien : current) {
                if (bien.getId().equals(id)) {
                    return bien;
                }
            }
        }
        return null;
    }
}
