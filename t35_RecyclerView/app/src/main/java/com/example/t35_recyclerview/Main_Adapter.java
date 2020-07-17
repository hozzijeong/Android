package com.example.t35_recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_Adapter extends RecyclerView.Adapter<Main_Adapter.CustomViewHolder> {

    private ArrayList<Main_Data> arrayList;

    public Main_Adapter(ArrayList<Main_Data> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Main_Adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ListView 가 처음으로 생성될 때 생명 주기

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Main_Adapter.CustomViewHolder holder, int position) {
        //추가 될 때의 홀더
        holder.profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.name.setText(arrayList.get(position).getName());
        holder.content.setText(arrayList.get(position).getContent());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.name.getText().toString();
                Toast.makeText(v.getContext(),curName,Toast.LENGTH_LONG).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
       public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }

    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected CircleImageView profile;
        protected TextView name,content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.profile = itemView.findViewById(R.id.iv_profile);
            this.name = itemView.findViewById(R.id.name);
            this.content = itemView.findViewById(R.id.content);

        }
    }
}
