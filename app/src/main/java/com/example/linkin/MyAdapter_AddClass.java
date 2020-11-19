package com.example.linkin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter_AddClass  extends RecyclerView.Adapter<MyAdapter_AddClass.ViewHolder> {
    List<Listitem_class_show> listItem;
    private Context context;

    public MyAdapter_AddClass(List<Listitem_class_show> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cmp_show,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Listitem_class_show list=listItem.get(position);
       holder.tv_u_id.setText(list.getU_id());
       holder.tv_job_type.setText(list.getJob_type());
       holder.tv_seat.setText(list.getSeat());
       holder.tv_msg.setText(list.getMsg());
       holder.tv_salary.setText(list.getSalary());



    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // define the textView
        public TextView tv_u_id,tv_msg,tv_job_type,tv_seat,tv_salary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          tv_u_id=itemView.findViewById(R.id.tvu_id);
          tv_job_type=itemView.findViewById(R.id.tvjob_id);
          tv_msg=itemView.findViewById(R.id.tv_msg_id);
          tv_seat=itemView.findViewById(R.id.tvseat_id);
          tv_salary=itemView.findViewById(R.id.sal_id);

        }
    }

}
