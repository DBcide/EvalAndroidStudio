package com.example.evalandroidstudio.model;

public class PieceTriangulaire extends Piece {
    private double base;
    private double hauteur;

    public PieceTriangulaire(TypePiece typePiece, String niveau, double base, double hauteur) {
        super(typePiece, niveau);
        this.base = base;
        this.hauteur = hauteur;
    }

    @Override
    public double surface() {
        return (base * hauteur) / 2;
    }

    public double getBase() {
        return base;
    }

    public double getHauteur() {
        return hauteur;
    }
}
