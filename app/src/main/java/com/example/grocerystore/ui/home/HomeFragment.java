package com.example.grocerystore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.grocerystore.R;
import com.example.grocerystore.adapters.PopularAdapters;
import com.example.grocerystore.model.PopularModel;

import java.util.List;


public class HomeFragment extends Fragment {


    //popular items
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container,false);
        return root;
    }

}