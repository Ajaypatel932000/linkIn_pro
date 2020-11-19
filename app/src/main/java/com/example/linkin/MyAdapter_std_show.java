package com.example.linkin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter_std_show extends RecyclerView.Adapter<MyAdapter_std_show.ViewHolder> {

    List<ListItem_std_show> listItem;
    Context context;

    public MyAdapter_std_show(List<ListItem_std_show> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.std_card,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       ListItem_std_show item=listItem.get(position);
        holder.job_type.setText(item.getJob_type());
       // holder.message.setText(item.getMessage());
      //  holder.seat.setText(item.getVacant_seat());
        //holder.num.setText(item.getNumber());
        //holder.email.setText(item.getEmail());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,student_details.class);
                intent.putExtra("msg",item.getMessage());
                intent.putExtra("num",item.getNumber());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("salary",item.getSalary());
                intent.putExtra("vs",item.getVacant_seat());
                intent.putExtra("jt",item.getJob_type());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        LinearLayout linearLayout;
      TextView num,email,job_type,message,seat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           job_type= itemView.findViewById(R.id.tvjob_id);
          // message=itemView.findViewById(R.id.tv_msg_id);
           //email=itemView.findViewById(R.id.tvemail_id);
           //num=itemView.findViewById(R.id.tvnum_id);
           //seat=itemView.findViewById(R.id.tvseat_id);
           linearLayout=itemView.findViewById(R.id.liner_id);
        }
    }
}
