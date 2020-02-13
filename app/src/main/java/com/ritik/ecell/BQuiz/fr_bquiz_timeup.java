package com.ritik.ecell.BQuiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ritik.ecell.R;

public class fr_bquiz_timeup extends Fragment {

    Button next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.fragment_fr_bquiz_timeup,container,false);

        next = view.findViewById(R.id.gotoresults);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),bquiz_results.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
