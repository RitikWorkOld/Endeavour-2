package com.ritik.ecell.BQuiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ritik.ecell.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fr_bquiz_ques12 extends Fragment {

    private RadioGroup radioGroup;

    private TextView question_text;
    private RadioButton option1,option2,option3,option4;
    private LinearLayout next_ques,previous_ques;
    private String correctanswer;
    private String quesid = "Question12";
    private String quesno = "11";
    private CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.fr_bquiz_ques12,container,false);

        option1 = view.findViewById( R.id.option_1 );
        option2 = view.findViewById( R.id.option_2 );
        option3 = view.findViewById( R.id.option_3 );
        option4 = view.findViewById( R.id.option_4 );
        question_text = view.findViewById( R.id.ques_text );
        next_ques = view.findViewById( R.id.next_btn );
        previous_ques = view.findViewById( R.id.prev_btn );
        radioGroup = view.findViewById( R.id.options );
        checkBox = view.findViewById( R.id.skipopt );

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "Questions" ).child( quesno );
        databaseReference.keepSynced( true );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Questions questions = dataSnapshot.getValue(Questions.class);

                question_text.setText( questions.getQuestions() );
                option1.setText( questions.getoption1() );
                option2.setText( questions.getoption2() );
                option3.setText( questions.getoption3() );
                option4.setText( questions.getoption4() );
                correctanswer = questions.getanswer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child( "ResultsBquiz" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        databaseReference1.keepSynced( true );
        databaseReference1.child( "Bquiz" ).child( quesid ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Report report = dataSnapshot.getValue(Report.class);

                if (report != null){
                    String option = report.getMyanswer();
                    //Toast.makeText( getActivity().getApplicationContext(),"Hello "+option,Toast.LENGTH_SHORT ).show();
                    if (option.equals( option1.getText().toString() )){
                        option1.setChecked( true );
                    }
                    if (option.equals( option2.getText().toString() )){
                        option2.setChecked( true );
                    }
                    if (option.equals( option3.getText().toString() )){
                        option3.setChecked( true );
                    }
                    if (option.equals( option4.getText().toString() )){
                        option4.setChecked( true );
                    }
                    if (option.equals( "skipped" )){
                        checkBox.setChecked( true );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        next_ques.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int option = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = view.findViewById( option );

                if (option != -1 || checkBox.isChecked()){

                    if (checkBox.isChecked()){

                        Report report = new Report( "skipped" ,"skipped",0);
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child( "ResultsBquiz" ).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference1.keepSynced( true );
                        databaseReference1.child( "Bquiz" ).child( quesid ).setValue( report );

                        //Toast.makeText( getActivity().getApplicationContext(),"Skipped",Toast.LENGTH_SHORT ).show();

                        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_bquiz,new fr_bquiz_ques13());
                        fragmentTransaction.commit();
                    }
                    else {
                        String str = radioButton.getText().toString();
                        if (str.equals( correctanswer )){

                            Report report = new Report( "correct" ,str,1);
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child( "ResultsBquiz" ).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            databaseReference1.keepSynced( true );
                            databaseReference1.child( "Bquiz" ).child( quesid ).setValue( report );


                            //Toast.makeText( getActivity(),"Correct answer " + str,Toast.LENGTH_SHORT).show();

                            final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container_bquiz,new fr_bquiz_ques13());
                            fragmentTransaction.commit();
                        }
                        else {

                            Report report = new Report( "wrong" ,str,0);
                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child( "ResultsBquiz" ).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            databaseReference2.keepSynced( true );
                            databaseReference2.child( "Bquiz" ).child( quesid ).setValue( report );

                            //Toast.makeText( getActivity(),"Wrong answer",Toast.LENGTH_SHORT ).show();

                            final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container_bquiz,new fr_bquiz_ques13());
                            fragmentTransaction.commit();
                        }
                    }
                }
                else {
                    Toast.makeText( getActivity(),"Please Select an option",Toast.LENGTH_SHORT).show();
                }
            }
        } );

        previous_ques.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText( getActivity(),"prev ques" ,Toast.LENGTH_SHORT).show();
                final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_bquiz,new fr_bquiz_ques11());
                fragmentTransaction.commit();

            }
        } );

        return view;
    }
}
