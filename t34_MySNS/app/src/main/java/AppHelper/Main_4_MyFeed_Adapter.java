package AppHelper;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import java.util.ArrayList;


public class Main_4_MyFeed_Adapter extends RecyclerView.Adapter<Main_4_MyFeed_Adapter.CustomViewHolder> {

    private ArrayList<Main_Feed_Info> arrayList;
    private Main_Setting_Dialog dialog;
    private MainActivity mainActivity;
    public Main_4_MyFeed_Adapter(Activity activity, ArrayList<Main_Feed_Info> arrayList) {
        this.arrayList = arrayList;
        this.mainActivity = (MainActivity) activity;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_4_myfeed_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.content.setText(arrayList.get(position).content);
    }

    @Override
    public int getItemCount() {
        Log.d("mood","size: "+arrayList.size());
        return (null != arrayList ? arrayList.size():0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView content;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.content = itemView.findViewById(R.id.my_content);
            if(mainActivity.custom_idx<0){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        mainActivity.showToast("pos: "+pos);
                        dialog = new Main_Setting_Dialog(mainActivity,pos);
                        dialog.show();
                    }
                });
            }
        }
    }



}
