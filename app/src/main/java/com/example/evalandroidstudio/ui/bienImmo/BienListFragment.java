package com.example.evalandroidstudio.ui.bienImmo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evalandroidstudio.R;
import com.example.evalandroidstudio.viewmodel.BienImmoViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BienListFragment extends Fragment {

    private BienImmoViewModel viewModel;

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
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBiens);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // ViewModel via Hilt
        viewModel = new ViewModelProvider(this).get(BienImmoViewModel.class);

        // Set adapter
        recyclerView.setAdapter(viewModel.getAdapter());

        // Observer LiveData pour mettre à jour l’adapter
        viewModel.getBiens().observe(getViewLifecycleOwner(), biens -> {
            // Met à jour la liste dans l’adapter
            viewModel.getAdapter().notifyDataSetChanged();
        });
    }
}