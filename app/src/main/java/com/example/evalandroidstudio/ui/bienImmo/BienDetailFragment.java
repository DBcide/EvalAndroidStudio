package com.example.evalandroidstudio.ui.bienImmo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evalandroidstudio.R;
import com.example.evalandroidstudio.model.BienImmobilier;
import com.example.evalandroidstudio.model.Piece;
import com.example.evalandroidstudio.viewmodel.BienImmoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BienDetailFragment extends Fragment {

    private BienImmoViewModel viewModel;
    private PieceAdapter adapter;
    private BienImmobilier bien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bien_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BienImmoViewModel.class);

        // Récupération du bienId passé dans le bundle
        String bienId = getArguments() != null ? getArguments().getString("bienId") : null;
        if (bienId != null) {
            bien = viewModel.getBienById(bienId);
        }

        if (bien == null) return;

        // TextView pour afficher info du bien
        TextView tvDetails = view.findViewById(R.id.tvBienDetails);
        tvDetails.setText(getBienDescription(bien));

        // RecyclerView pour les pièces
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewPieces);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PieceAdapter(new ArrayList<>(bien.getPieces()));
        recyclerView.setAdapter(adapter);

        // FloatingActionButton pour ajouter une pièce
        FloatingActionButton fabAddPiece = view.findViewById(R.id.fabAddPiece);
        fabAddPiece.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("bienId", bien.getId());
            Navigation.findNavController(view).navigate(R.id.action_to_pieceAddFragment, bundle);
        });
    }

    private String getBienDescription(BienImmobilier bien) {
        return "ID: " + bien.getId() +
                "\nType: " + bien.getType() +
                "\nNombre de pièces: " + (bien.getPieces() != null ? bien.getPieces().size() : 0);
    }

    // Adapter interne pour les pièces
    private static class PieceAdapter extends RecyclerView.Adapter<PieceViewHolder> {
        private final ArrayList<Piece> pieces;

        public PieceAdapter(ArrayList<Piece> pieces) {
            this.pieces = pieces;
        }

        @NonNull
        @Override
        public PieceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new PieceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PieceViewHolder holder, int position) {
            Piece piece = pieces.get(position);
            holder.bind(piece);
        }

        @Override
        public int getItemCount() {
            return pieces.size();
        }
    }

    private static class PieceViewHolder extends RecyclerView.ViewHolder {
        public PieceViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Piece piece) {
            ((TextView) itemView).setText(piece.getTypePiece().getNom() +
                    " - Surface: " + piece.surface() + " m²");
        }
    }
}