package com.example.evalandroidstudio.model;

public class PieceCirculaire extends Piece {
    private double rayon;

    public PieceCirculaire(TypePiece typePiece, String niveau, double rayon) {
        super(typePiece, niveau);
        this.rayon = rayon;
    }

    @Override
    public double surface() {
        return Math.PI * rayon * rayon;
    }

    public double getRayon() {
        return rayon;
    }
}
