package com.example.jsonapijava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.jsonapijava.model.Pemrograman;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter {
    Context context;
    ArrayList<Pemrograman> datas;
    LayoutInflater layoutInflater;

    ImageView logo;
    TextView title, deskripsi, helo_word, detail_web;

    public Adapter(Context context, ArrayList<Pemrograman> data) {
        super(context, R.layout.list_algoritma, data);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.datas = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_algoritma, parent, false);
        Pemrograman data = datas.get(position);

        logo = convertView.findViewById(R.id.logo);
        title = convertView.findViewById(R.id.NamePerograman);
        helo_word = convertView.findViewById(R.id.heloWord);
        deskripsi = convertView.findViewById(R.id.deskripsi);
        detail_web = convertView.findViewById(R.id.detailJava);

        Glide
                .with(context)
                .load(data.getLogo())
                .placeholder(R.drawable.ic_refres_foreground)
                .into(logo);
        title.setText(data.getNameAlgoritma());
        deskripsi.setText(data.getDescription());
        helo_word.setText(data.getHello_world());
        detail_web.setText(data.getBaca_lebih_lanjut());

        return convertView;
    }
}
