package com.smb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smb.R;
import com.smb.listener.OnItemClickListener;
import com.smb.model.Viagem;

import java.util.List;

public class ViagensRecyclerViewAdapter extends RecyclerView.Adapter<ViagensRecyclerViewAdapter.CustomViewHolder>{

    private List<Viagem> mViagens;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public ViagensRecyclerViewAdapter(Context context, List<Viagem> viagens){
        this.mContext = context;
        this.mViagens = viagens;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Viagem viagem = mViagens.get(position);
        holder.textView.setText(viagem.getNome());

        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(viagem);
            }
        };

        holder.textView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != mViagens ? mViagens.size(): 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        protected TextView textView;

        public CustomViewHolder(View view){
            super(view);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
