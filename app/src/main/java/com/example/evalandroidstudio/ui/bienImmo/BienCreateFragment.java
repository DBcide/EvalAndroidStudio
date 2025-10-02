package com.example.evalandroidstudio.ui.bienImmo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.evalandroidstudio.R;
import com.example.evalandroidstudio.model.BienImmobilier;
import com.example.evalandroidstudio.viewmodel.BienImmoViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BienCreateFragment extends Fragment {

    private BienImmoViewModel viewModel;
    private Spinner spinnerTypeBien;
    private EditText editTextRue, editTextVille, editTextCodePostal;
    private Button buttonCreate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bien_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Récupération des vues
        spinnerTypeBien = view.findViewById(R.id.spinnerTypeBien);
        editTextRue = view.findViewById(R.id.editTextRue);
        editTextVille = view.findViewById(R.id.editTextVille);
        editTextCodePostal = view.findViewById(R.id.editTextCodePostal);
        buttonCreate = view.findViewById(R.id.buttonCreateBien);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(BienImmoViewModel.class);

        // Gestion du clic sur le bouton
        buttonCreate.setOnClickListener(v -> {
            String type = spinnerTypeBien.getSelectedItem().toString();
            String rue = editTextRue.getText().toString().trim();
            String ville = editTextVille.getText().toString().trim();
            String codePostal = editTextCodePostal.getText().toString().trim();

            // Vérifications simples
            if (rue.isEmpty() || ville.isEmpty() || codePostal.isEmpty()) {
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Création du bien
            BienImmobilier nouveauBien = new BienImmobilier(type, rue, ville, codePostal);

            buttonCreate.setOnClickListener(vv -> {
                // création du bien...
                viewModel.ajouterBien(nouveauBien);

                // revient au fragment précédent (bienListFragment)
                Navigation.findNavController(view).popBackStack();
            });
        });
    }
}