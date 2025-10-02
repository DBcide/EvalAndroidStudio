package com.example.evalandroidstudio.model;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

    public class BienImmobilier {
        public static final String TYPE_APPARTEMENT = "Appartement";
        public static final String TYPE_MAISON = "Maison";

        private String id;
        private String type;
        private String rue;
        private String ville;
        private String codePostal;
        private List<Piece> pieces;

        /**
         * Constructeur de BienImmobilier
         */
        public BienImmobilier(String type, String rue, String ville, String codePostal) {
            this.id = UUID.randomUUID().toString();
            this.type = type;
            this.rue = rue;
            this.ville = ville;
            this.codePostal = codePostal;
            this.pieces = new ArrayList<>();
        }

        /**
         * Ajoute une pièce au bien immobilier
         */
        public void ajouterPiece(Piece piece) {
            if (piece != null) {
                pieces.add(piece);
            }
        }

        /**
         * Calcule la surface habitable totale
         */
        public double surfaceHabitable() {
            double total = 0.0;
            for (Piece p : pieces) {
                if (p.isSurfaceHabitable()) {
                    total += p.surface();
                }
            }
            return total;
        }

        /**
         * Calcule la surface non habitable totale
         */
        public double surfaceNonHabitable() {
            double total = 0.0;
            for (Piece p : pieces) {
                if (!p.isSurfaceHabitable()) {
                    total += p.surface();
                }
            }
            return total;
        }

        /**
         * Concatène les infos de chaque pièce
         */
        public String toStringPieces() {        String resultat = "";
            for (Piece unePiece : pieces) {
                resultat += unePiece.toString();
            }
            return resultat;
        }

        @Override
        public String toString() {
            DecimalFormat df = new DecimalFormat("#.00");
            df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));

            return type + " " + id + "\n" +
                    "Localisation : " + rue + " " + codePostal + " " + ville + "\n" +
                    " \n" +
                    " Description du bien : \n" +
                    toStringPieces() +
                    "\n" +
                    "Pour une surface habitable de : " + df.format(surfaceHabitable()) +
                    " m2 et une surface non habitable de : " + df.format(surfaceNonHabitable()) + " m2.";
        }

        public String getId() {
            return this.id;
        }
    }
