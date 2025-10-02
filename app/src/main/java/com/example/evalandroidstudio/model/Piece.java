package com.example.evalandroidstudio.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public abstract class Piece {
    private TypePiece typePiece;
    private String niveau;

    public Piece(TypePiece typePiece, String niveau) {
        this.typePiece = typePiece;
        this.niveau = niveau;
    }

    public TypePiece getTypePiece() {
        return typePiece;
    }

    public String getNiveau() {
        return niveau;
    }

    // Méthode abstraite à implémenter dans les classes filles
    public abstract double surface();

    // Détermine si la surface est habitable
    public boolean isSurfaceHabitable() {
        return typePiece.isSurfaceHabitable();
    }

    @Override
    public String toString() {
        // Formater avec 2 décimales et virgule
        DecimalFormat df = new DecimalFormat("#.00");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        return "- " + getTypePiece().getNom() + " surface : " + df.format(surface()) + " m2\n";
    }
}
