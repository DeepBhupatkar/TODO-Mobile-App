package com.example.todoapp;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    List<ToDoModel> modelList;
    HomePage homePage;
    DataBaseHelper dataBaseHelper;

    ToDoAdapter(DataBaseHelper dataBaseHelper, HomePage homePage){
        this.homePage = homePage;
        this.dataBaseHelper = dataBaseHelper;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ToDoModel item = modelList.get(position);
        holder.mcheckbox.setText(item.getTask());
        holder.mcheckbox.setChecked(toBoolean(item.getStatus()));
        holder.mcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dataBaseHelper.updateStatus(item.getId(), 1);
                }
                else {
                    dataBaseHelper.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public boolean toBoolean(int number){
        return number!=0;
    }

    public Context getContext(){
        return homePage;
    }

    public void setTasks(List<ToDoModel> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        ToDoModel item = modelList.get(position);
        dataBaseHelper.deleteTask(item.getId());
        modelList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel item = modelList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(homePage.getSupportFragmentManager(), task.getTag());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mcheckbox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mcheckbox = itemView.findViewById(R.id.mcheckbox);
        }
    }

}