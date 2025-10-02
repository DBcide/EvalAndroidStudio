package com.example.evalandroidstudio.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evalandroidstudio.data.repository.BienImmoRepo;
import com.example.evalandroidstudio.model.BienImmobilier;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BienImmoViewModel extends ViewModel {
    private final BienImmoRepo repository;
    private final MutableLiveData<List<BienImmobilier>> biens = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public BienImmoViewModel(BienImmoRepo repository) {
        this.repository = repository;
        biens.setValue(repository.getBiens().getValue());
    }

    public LiveData<List<BienImmobilier>> getBiens() {
        return biens;
    }

    public void ajouterBien(BienImmobilier bien) {
        List<BienImmobilier> current = biens.getValue();
        current.add(bien);
        biens.setValue(current);
    }

    // ⚡ Ici on gère aussi l'adapter
    private final RecyclerView.Adapter<BienViewHolder> adapter = new RecyclerView.Adapter<BienViewHolder>() {
        private List<BienImmobilier> data = new ArrayList<>();

        public void setBiens(List<BienImmobilier> biens) {
            this.data = biens;
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
            BienImmobilier bien = data.get(position);
            ((TextView) holder.itemView).setText(bien.toString());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    };

    public RecyclerView.Adapter<BienViewHolder> getAdapter() {
        return adapter;
    }

    // Le ViewHolder interne (lié à l’Adapter ci-dessus)
    static class BienViewHolder extends RecyclerView.ViewHolder {
        public BienViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
