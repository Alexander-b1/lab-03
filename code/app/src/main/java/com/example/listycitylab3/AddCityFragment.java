package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListner{
        void addCity(City city);
    }

    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private AddCityDialogListner listner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListner) {
            listner = (AddCityDialogListner) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListner");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityText = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceText = view.findViewById(R.id.edit_text_province_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Edit/Add a City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", (dialog,witch) -> {
                    String cityName = editCityText.getText().toString();
                    String provinceName = editProvinceText.getText().toString();

                    City city = (City) this.getArguments().get("city");

                    city.setName(cityName);
                    city.setProvince(provinceName);

                    listner.addCity(city);
                })
                .create();
    }
}
