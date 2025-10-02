package com.example.evalandroidstudio.model;

public class PieceQuadrilatere extends Piece {
    private double largeur;
    private double longueur;

    public PieceQuadrilatere(TypePiece typePiece, String niveau, double largeur, double longueur) {
        super(typePiece, niveau);
        this.largeur = largeur;
        this.longueur = longueur;
    }

    @Override
    public double surface() {
        return largeur * longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getLongueur() {
        return longueur;
    }
}
