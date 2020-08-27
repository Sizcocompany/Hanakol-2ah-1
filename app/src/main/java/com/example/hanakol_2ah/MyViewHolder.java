package com.example.hanakol_2ah;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView ;
   public TextView textView ;
    public MyViewHolder(@NonNull View itemView) {
        super( itemView );

        imageView = itemView.findViewById( R.id.image_sigleview );
        textView = itemView.findViewById( R.id.tv_single_view );

    }
}
