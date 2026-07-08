package codesgesture.app.askhoardingv2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import codesgesture.app.askhoardingv2.Model.CategoryModel;
import codesgesture.app.askhoardingv2.PageJob;
import codesgesture.app.askhoardingv2.R;
import codesgesture.app.askhoardingv2.Utils.Constants;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<CategoryModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> arrayList, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
     //   this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getStudent_id();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final CategoryModel data = arrayList.get(i);

        holder.name.setText(data.getCat_name());

        Uri uri = Uri.parse(Constants.WEBURI+data.getIcon());
        Glide.with(context).load(uri).into(holder.img);

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PageJob.class);
                intent.putExtra("data",data);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        LinearLayout click;

        ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.name);
            click = view.findViewById(R.id.click);
        }
    }

    public void updateList(ArrayList<CategoryModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }
}