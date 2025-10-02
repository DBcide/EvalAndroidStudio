package com.example.evalandroidstudio.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.evalandroidstudio.model.BienImmobilier;

import java.util.List;

import javax.inject.Singleton;

@Singleton
public class BienImmoRepo {
    private final MutableLiveData<List<BienImmobilier>> bienImmoLiveData;
}
