package iamprogrammer.brian.com.mygym;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ADMIN on 7/5/2018.
 */

public class CustomInstructors extends RecyclerView.Adapter<CustomInstructors.MyViewHolder> {

    ArrayList<Instructor> instructors;
    ArrayList<Integer> images;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView iName, iEmail, iPhone;
        public ImageView iImage;

        public MyViewHolder(View view) {
            super(view);
            iImage = view.findViewById(R.id.trainer_image);
            iName = view.findViewById(R.id.textview_trainer_name);
            iEmail = view.findViewById(R.id.textview_trainer_email);
            iPhone = view.findViewById(R.id.textview_trainer_number);
        }
    }

    public CustomInstructors(ArrayList<Instructor> instructors, ArrayList<Integer> images) {
        this.instructors = instructors;
        this.images = images;
    }

    @Override
    public CustomInstructors.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.custom_instructors, parent, false );
        return new MyViewHolder( v );
    }

    @Override
    public void onBindViewHolder(CustomInstructors.MyViewHolder holder, int position) {
        //holder.iImage.setImageResource( images.get(position) );
        //holder.iName.setText( instructors.get(position).getName() + ", " + instructors.get(position).getGender() );
        //holder.iEmail.setText( instructors.get(position).getEmail() );
        //holder.iPhone.setText( instructors.get(position).getPhonenumber() );
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }
}
