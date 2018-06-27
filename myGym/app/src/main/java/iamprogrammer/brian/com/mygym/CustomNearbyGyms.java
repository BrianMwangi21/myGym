package iamprogrammer.brian.com.mygym;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ADMIN on 6/26/2018.
 */

public class CustomNearbyGyms extends RecyclerView.Adapter<CustomNearbyGyms.MyViewHolder> {

    Context context;
    ArrayList<String> npNames, npCords, npLocation, npVicinity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gymName, gymCoords;

        public MyViewHolder(View view) {
            super(view);
            gymName = view.findViewById(R.id.gym_name);
            gymCoords = view.findViewById(R.id.gym_coordinates);
        }
    }

    public CustomNearbyGyms(Context context, ArrayList<String> npNames, ArrayList<String> npCords, ArrayList<String> npLocation,
                            ArrayList<String> npVicinity ) {
        this.context = context;
        this.npNames = npNames;
        this.npCords = npCords;
        this.npLocation = npLocation;
        this.npVicinity = npVicinity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.custom_nearbygyms, parent, false );
        return new MyViewHolder( v );
    }

    @Override
    public void onBindViewHolder(CustomNearbyGyms.MyViewHolder holder, int position) {
        holder.gymName.setText( npNames.get(position) );
        holder.gymCoords.setText( npCords.get(position) );
    }

    @Override
    public int getItemCount() {
        return npNames.size();
    }
}
