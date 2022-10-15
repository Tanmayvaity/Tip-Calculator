package com.example.tipcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {

    ArrayList<TipsList> tips;
    Context context;
    OnItemClickListener listener;
    public TipsAdapter(Context context, ArrayList<TipsList> tips) {
        this.tips = tips;
        this.context = context;

    }

    public interface OnItemClickListener{
        void onItemClick(View itemView , int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TipsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tips_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TipsAdapter.ViewHolder holder, int position) {
        TipsList tip  = tips.get(position);
        holder.tipId.setText(tip.getTip() + "%");
        holder.tipTotal.setText(String.valueOf(tip.getTotal()));
        holder.tipPerPerson.setText(String.valueOf(tip.getTotalPerPerson()));
        holder.splitNo.setText("split by:"+ tip.getSplitNo());


    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        private TextView tipId,tipTotal,tipPerPerson,splitNo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            

            tipId = itemView.findViewById(R.id.tipId);
            tipTotal = itemView.findViewById(R.id.tipTotal);
            tipPerPerson = itemView.findViewById(R.id.tipPerPerson);
            splitNo = itemView.findViewById(R.id.splitNo);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}
