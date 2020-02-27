package com.ritik.ecell.FPL;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ritik.ecell.BQuiz.bquiz_results;
import com.ritik.ecell.R;

public class fr_fpl_timeup extends Fragment {

    Button next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.fr_fpl_timeup,container,false);

        next = view.findViewById(R.id.gotoresults);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Fpl_results.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
