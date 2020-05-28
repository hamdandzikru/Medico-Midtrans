package com.example.medico.Dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.medico.R;

public class SpecialistTypeSearchRSDialog extends BottomSheetDialogFragment {
    private ButtomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pop_up_specialist,container,false);

        ImageView btn_dismiss = v.findViewById(R.id.btn_dismiss);

        RelativeLayout RLSpecialistUmum = v.findViewById(R.id.RLSpecialistUmum);
        RelativeLayout RLSpecialistAnak = v.findViewById(R.id.RLSpecialistAnak);
        RelativeLayout RLSpecialistGigi = v.findViewById(R.id.RLSpecialistGigi);
        RelativeLayout RLSpecialistMata = v.findViewById(R.id.RLSpecialistMata);
        RelativeLayout RLSpecialistKandungan = v.findViewById(R.id.RLSpecialistKandungan);
        RelativeLayout RLSpecialistTulang = v.findViewById(R.id.RLSpecialistTulang);
        RelativeLayout RLSpecialistOtak = v.findViewById(R.id.RLSpecialistOtak);
        RelativeLayout RLSpecialistTelinga = v.findViewById(R.id.RLSpecialistTelinga);

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        RLSpecialistUmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Umum");
                dismiss();
            }
        });

        RLSpecialistAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Anak");
                dismiss();
            }
        });

        RLSpecialistGigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Gigi");
                dismiss();
            }
        });

        RLSpecialistMata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Mata");
                dismiss();
            }
        });

        RLSpecialistKandungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Kandungan");
                dismiss();
            }
        });

        RLSpecialistTulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Tulang");
                dismiss();
            }
        });

        RLSpecialistOtak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Otak");
                dismiss();
            }
        });

        RLSpecialistTelinga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Telinga");
                dismiss();
            }
        });

        return v;
    }

    public interface ButtomSheetListener{
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ButtomSheetListener) context;
        }catch (ClassCastException e){
            Log.d("TAG",context.toString() + " must implement BottomSheetListener");
            throw new ClassCastException(context.toString() + " must implement BottomSheetListener");
        }
    }
}
