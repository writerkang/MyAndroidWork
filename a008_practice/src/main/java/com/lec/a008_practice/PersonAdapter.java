package com.lec.a008_practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    ArrayList<Person> items = new ArrayList<Person>();
    static PersonAdapter adapter;

    public PersonAdapter() {
        this.adapter = this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View itemView = inf.inflate(R.layout.item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView cvName, cvAge, cvAdr;
        ImageButton delBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvName = itemView.findViewById(R.id.cvName);
            cvAge = itemView.findViewById(R.id.cvAge);
            cvAdr = itemView.findViewById(R.id.cvAdr);
            delBtn = itemView.findViewById(R.id.delBtn);

            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    adapter.removeItem(position);
                    adapter.notifyDataSetChanged();
                }
            });

        } // end generator();

        public void setItem(Person item) {
            cvName.setText(item.getName());
            cvAge.setText(item.getAge() + "");
            cvAdr.setText(item.getAdr());
        } // end setItem();
    } // end ViewHolder

    public void addItem(Person item) {
        items.add(item);
    }

    public void addItem(int position, Person item) {
        items.add(position, item);
    }

    public void setItems(ArrayList<Person> items) {
        this.items = items;
    }

    public Person getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Person item) {
        items.set(position, item);
    }

    public void removeItem(int position) {
        items.remove(position);
    }

} //end PersonAdapter
