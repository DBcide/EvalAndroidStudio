package com.example.evalandroidstudio.ui.bienImmo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.evalandroidstudio.R;
import com.example.evalandroidstudio.model.BienImmobilier;
import com.example.evalandroidstudio.model.Piece;
import com.example.evalandroidstudio.model.PieceCirculaire;
import com.example.evalandroidstudio.model.PieceQuadrilatere;
import com.example.evalandroidstudio.model.PieceTriangulaire;
import com.example.evalandroidstudio.model.TypePiece;
import com.example.evalandroidstudio.viewmodel.BienImmoViewModel;

import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PieceAddQuadrilatereFragment extends Fragment {

    private BienImmoViewModel viewModel;
    private BienImmobilier bien;

    private Spinner spinnerTypePiece;
    private LinearLayout layoutQuadrilatere, layoutTriangulaire, layoutCirculaire;
    private EditText etLongueur, etLargeur, etBase, etHauteur, etRayon;
    private Button btnAddPiece;

    private String selectedType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_piece_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BienImmoViewModel.class);

        // Récupérer l'id du bien
        String bienId = getArguments() != null ? getArguments().getString("bienId") : null;
        if (bienId != null) {
            bien = viewModel.getBienById(bienId);
        }
        if (bien == null) return;

        // Récupérer les vues
        spinnerTypePiece = view.findViewById(R.id.spinnerTypePiece);
        layoutQuadrilatere = view.findViewById(R.id.layoutQuadrilatere);
        layoutTriangulaire = view.findViewById(R.id.layoutTriangulaire);
        layoutCirculaire = view.findViewById(R.id.layoutCirculaire);
        etLongueur = view.findViewById(R.id.etLongueur);
        etLargeur = view.findViewById(R.id.etLargeur);
        etBase = view.findViewById(R.id.etBase);
        etHauteur = view.findViewById(R.id.etHauteur);
        etRayon = view.findViewById(R.id.etRayon);
        btnAddPiece = view.findViewById(R.id.btnAddPiece);

        // Spinner avec types de pièce
        List<String> types = Arrays.asList("Quadrilatère", "Triangulaire", "Circulaire");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypePiece.setAdapter(adapter);

        spinnerTypePiece.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = types.get(position);
                layoutQuadrilatere.setVisibility(selectedType.equals("Quadrilatère") ? View.VISIBLE : View.GONE);
                layoutTriangulaire.setVisibility(selectedType.equals("Triangulaire") ? View.VISIBLE : View.GONE);
                layoutCirculaire.setVisibility(selectedType.equals("Circulaire") ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedType = "Quadrilatère";
                layoutQuadrilatere.setVisibility(View.VISIBLE);
                layoutTriangulaire.setVisibility(View.GONE);
                layoutCirculaire.setVisibility(View.GONE);
            }
        });

        btnAddPiece.setOnClickListener(v -> ajouterPiece());
    }

    private void ajouterPiece() {
        Piece piece = null;

        try {
            switch (selectedType) {
                case "Quadrilatère":
                    double longueur = Double.parseDouble(etLongueur.getText().toString());
                    double largeur = Double.parseDouble(etLargeur.getText().toString());
                    TypePiece typeQ = new TypePiece(TypePiece.SALON, true, true); // tu peux adapter selon UI
                    piece = new PieceQuadrilatere(typeQ, "0", longueur, largeur);
                    break;
                case "Triangulaire":
                    double base = Double.parseDouble(etBase.getText().toString());
                    double hauteur = Double.parseDouble(etHauteur.getText().toString());
                    TypePiece typeT = new TypePiece(TypePiece.CHAMBRE, true, true);
                    piece = new PieceTriangulaire(typeT, "0", base, hauteur);
                    break;
                case "Circulaire":
                    double rayon = Double.parseDouble(etRayon.getText().toString());
                    TypePiece typeC = new TypePiece(TypePiece.CUISINE, true, true);
                    piece = new PieceCirculaire(typeC, "0", rayon);
                    break;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Veuillez remplir tous les champs correctement", Toast.LENGTH_SHORT).show();
            return;
        }

        if (piece != null) {
            viewModel.ajouterPieceAuBien(bien, piece);
            // Retour automatique vers le détail
            Navigation.findNavController(requireView()).popBackStack();
        }
    }
}