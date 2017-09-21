package mochamad.ulin.nuha.ta;

/**
 * Created by Juned on 1/20/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewCardViewAdapter extends RecyclerView.Adapter<RecyclerViewCardViewAdapter.ViewHolder> {

    Context context;

    List<subjects> subjects;

    public RecyclerViewCardViewAdapter(List<subjects> getDataAdapter, Context context){

        super();

        this.subjects = getDataAdapter;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_det, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        subjects getDataAdapter1 =  subjects.get(position);

        holder.SubjectName.setText(getDataAdapter1.getName());
        holder.SubjectID.setText(getDataAdapter1.getID());
        holder.SubjectJeneng.setText(getDataAdapter1.getJeneng());
        holder.Subjecttgl.setText(getDataAdapter1.gettgl());

    }

    @Override
    public int getItemCount() {

        return subjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView SubjectName,SubjectID,SubjectJeneng,Subjecttgl;


        public ViewHolder(View itemView) {

            super(itemView);

            SubjectName = (TextView) itemView.findViewById(R.id.TextViewCard) ;
            SubjectID = (TextView) itemView.findViewById(R.id.tvt_id) ;
            SubjectJeneng = (TextView) itemView.findViewById(R.id.tvt_nama) ;
            Subjecttgl = (TextView) itemView.findViewById(R.id.tvt_tggl) ;


        }
    }
}