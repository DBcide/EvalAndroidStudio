package com.example.evalandroidstudio;

import static org.junit.Assert.*;

import com.example.evalandroidstudio.model.BienImmobilier;
import com.example.evalandroidstudio.model.PieceCirculaire;
import com.example.evalandroidstudio.model.PieceQuadrilatere;
import com.example.evalandroidstudio.model.PieceTriangulaire;
import com.example.evalandroidstudio.model.TypePiece;

import org.junit.Test;

public class BienImmobilierTest {

    @Test
    public void bienImmobilierIsOk(){
        TypePiece cuisine = new TypePiece(TypePiece.CUISINE, true, true);
        TypePiece chambre = new TypePiece(TypePiece.CHAMBRE, true, true);
        TypePiece sdb = new TypePiece(TypePiece.SALLE_DE_BAIN, true, false);

        BienImmobilier bienImmobilier = new BienImmobilier(BienImmobilier.TYPE_MAISON, "rue de la gare", "annecy", "74000");
        bienImmobilier.ajouterPiece(new PieceQuadrilatere(cuisine, "0", 4.58, 3));
        bienImmobilier.ajouterPiece(new PieceTriangulaire(chambre, "0", 5, 2));
        bienImmobilier.ajouterPiece(new PieceCirculaire(sdb, "0", 3));
        String id = bienImmobilier.getId();

        assertEquals(BienImmobilier.TYPE_MAISON + " " + id + "\n" +
                "Localisation : rue de la gare 74000 annecy\n" +
                " \n" +
                " Description du bien : \n" +
                "- Cuisine surface : 13,74 m2\n" +
                "- Chambre surface : 5,00 m2\n" +
                "- Salle de bain surface : 28,27 m2\n" +
                "\n" +
                "Pour une surface habitable de : 47,01 m2 et une surface non habitable de : ,00 m2.", bienImmobilier.toString());
    }

}
