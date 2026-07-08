package codesgesture.app.askhoardingv2;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.askhoardingv2.Adapter.CategoryAdapter;
import codesgesture.app.askhoardingv2.Model.CategoryModel;
import codesgesture.app.askhoardingv2.Model.MainCategoryModel;
import codesgesture.app.askhoardingv2.Services.CallJson;
import codesgesture.app.askhoardingv2.Services.JsonCallbacks;
import codesgesture.app.askhoardingv2.Services.NetParam;


public class JobCategory extends AppCompatActivity  {
    RecyclerView recyclerview;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryModels=new ArrayList<>();
    LinearLayout norecord;
    Spinner spnr;
    ArrayList<MainCategoryModel> mainCategoryModels=new ArrayList<>();
    ArrayAdapter<MainCategoryModel> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_category);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Job Category");

        recyclerview=findViewById(R.id.recyclerview);
        norecord=findViewById(R.id.norecord);
        spnr=findViewById(R.id.spnr);

        mainCategoryModels = new ArrayList<>();
        adapter = new ArrayAdapter<MainCategoryModel>(JobCategory.this, android.R.layout.simple_spinner_item, mainCategoryModels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr.setAdapter(adapter);
        BindCategory();
        spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnr.getSelectedItemPosition();
                String nm =String.valueOf(mainCategoryModels.get(pos).getCatname());
                BindData(nm);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        GridLayoutManager mLayoutManager = new GridLayoutManager(JobCategory.this, 2);
        recyclerview.setLayoutManager(mLayoutManager);
        categoryAdapter = new CategoryAdapter(JobCategory.this, categoryModels, R.layout.item_category);
        recyclerview.setAdapter(categoryAdapter);
        recyclerview.setItemViewCacheSize(categoryModels.size());

    }

    private void BindCategory() {
        mainCategoryModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(JobCategory.this);
        jc.SendRequest("get_jobmaincategory", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    MainCategoryModel mod = new MainCategoryModel();
                    mod.setId(Float.parseFloat(obj.getString("id")));
                    mod.setCatname(obj.getString("catname"));
                    mainCategoryModels.add(mod);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "", "Please wait while getting..");
    }


    private void BindData(String nm) {
        categoryModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(JobCategory.this);
        param.add(new NetParam("catname",nm));
        jc.SendRequest("get_jobcategory", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    CategoryModel product = new CategoryModel();
                    product.setCat_name(obj.getString("cat_name"));
                    product.setId(obj.getString("id"));
                    product.setIcon(obj.getString("icon"));
                    product.setIsactive(obj.getString("isactive"));
                    categoryModels.add(product);
                }
                categoryAdapter.notifyDataSetChanged();
                BindDataView();
            }

            @Override
            public void onPostError(String msg) {
                BindDataView();
            }
        }, "", "Loading..");
    }

    private void BindDataView() {
        if(categoryModels.size()>0){
            norecord.setVisibility(View.GONE);
        }
        else{
            norecord.setVisibility(View.VISIBLE);
        }
    }



}
