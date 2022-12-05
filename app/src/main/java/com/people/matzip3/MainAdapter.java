package com.people.matzip3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList;

    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    // 뷰홀더 만들어질 때 생명 주기
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    // 뷰홀더 추가될 때 생명 주기
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        //holder.img.setImageResource(arrayList.get(position).getImg());
//        MyAppGlideModule.with(holder.itemView).load(arrayList.get(position).getImg())
//                .override(300,400)
//                .into(holder.img);
        GlideApp.with(holder.itemView).load(arrayList.get(position).getImg())
                .override(500,400)
                .into(holder.img);
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_menu.setText(arrayList.get(position).getTv_menu());
        holder.tv_addr.setText(arrayList.get(position).getTv_addr());

        // 리스트뷰 클릭 시
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.tv_name.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView img;
        protected TextView tv_name;
        protected TextView tv_menu;
        protected TextView tv_addr;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.img);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_menu = (TextView) itemView.findViewById(R.id.tv_menu);
            this.tv_addr = (TextView) itemView.findViewById(R.id.tv_addr);
        }
    }
}
