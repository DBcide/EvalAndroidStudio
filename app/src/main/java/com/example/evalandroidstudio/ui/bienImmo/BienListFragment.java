package com.example.evalandroidstudio.ui.bienImmo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evalandroidstudio.R;
import com.example.evalandroidstudio.model.BienImmobilier;
import com.example.evalandroidstudio.viewmodel.BienImmoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BienListFragment extends Fragment {

    private BienImmoViewModel viewModel;
    private RecyclerView recyclerView;
    private BienAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bien_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewBiens);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Adapter
        adapter = new BienAdapter(bien -> {
            // Clic sur un bien → navigation vers le détail
            Bundle bundle = new Bundle();
            bundle.putString("bienId", bien.getId());
            Navigation.findNavController(view).navigate(R.id.action_to_detail, bundle);
        });
        recyclerView.setAdapter(adapter);

        // ViewModel via Hilt
        viewModel = new ViewModelProvider(this).get(BienImmoViewModel.class);

        // Observation LiveData pour mettre à jour l’UI
        viewModel.getBiens().observe(getViewLifecycleOwner(), biens -> {
            adapter.setBiens(biens);
        });

        // FloatingActionButton → création d’un nouveau bien
        FloatingActionButton fab = view.findViewById(R.id.fabAddBien);
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_to_create));
    }

    // Adapter interne pour le RecyclerView
    private static class BienAdapter extends RecyclerView.Adapter<BienViewHolder> {
        private List<BienImmobilier> biens = new ArrayList<>();
        private final OnItemClickListener listener;

        interface OnItemClickListener {
            void onItemClick(BienImmobilier bien);
        }

        public BienAdapter(OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setBiens(List<BienImmobilier> biens) {
            this.biens = biens;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public BienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new BienViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BienViewHolder holder, int position) {
            BienImmobilier bien = biens.get(position);
            holder.bind(bien, listener);
        }

        @Override
        public int getItemCount() {
            return biens.size();
        }
    }

    // ViewHolder pour chaque item
    private static class BienViewHolder extends RecyclerView.ViewHolder {
        private final android.widget.TextView textView;

        public BienViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (android.widget.TextView) itemView;
        }

        public void bind(BienImmobilier bien, BienAdapter.OnItemClickListener listener) {
            textView.setText(bien.toString());
            itemView.setOnClickListener(v -> listener.onItemClick(bien));
        }
    }
}