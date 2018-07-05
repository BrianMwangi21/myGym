package iamprogrammer.brian.com.mygym;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ADMIN on 7/5/2018.
 */

public class CustomInstructors extends RecyclerView.Adapter<CustomInstructors.MyViewHolder> {

    ArrayList<String> iNames, iGender, iEmail, iPhone;
    int imagesRes[];

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

    public CustomInstructors( int imagesRes[], ArrayList<String> names, ArrayList<String> genders, ArrayList<String> emails,
                              ArrayList<String> numbers) {
        this.imagesRes = imagesRes;
        this.iNames = names;
        this.iGender = genders;
        this.iEmail = emails;
        this.iPhone = numbers;
    }

    @Override
    public CustomInstructors.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.custom_instructors, parent, false );
        return new CustomInstructors.MyViewHolder( v );
    }

    @Override
    public void onBindViewHolder(CustomInstructors.MyViewHolder holder, int position) {
        holder.iImage.setImageResource( imagesRes[position] );
        holder.iName.setText( iNames.get(position) + ", " + iGender.get(position).substring(0,1) );
        holder.iEmail.setText( "Email : " + iEmail.get(position) );
        holder.iPhone.setText( "Tel : " + iPhone.get(position) );
    }

    @Override
    public int getItemCount() {
        return iNames.size();
    }
}
