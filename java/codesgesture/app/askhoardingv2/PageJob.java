package codesgesture.app.askhoardingv2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.askhoardingv2.Adapter.JobAdapter;
import codesgesture.app.askhoardingv2.Model.CategoryModel;
import codesgesture.app.askhoardingv2.Model.CityModel;
import codesgesture.app.askhoardingv2.Model.JobModel;
import codesgesture.app.askhoardingv2.Services.CallJson;
import codesgesture.app.askhoardingv2.Services.JsonCallbacks;
import codesgesture.app.askhoardingv2.Services.NetParam;
import codesgesture.app.askhoardingv2.Services.UserUtil;
import codesgesture.app.askhoardingv2.Utils.CallJsonWithoutProgress;


public class PageJob extends AppCompatActivity {
    CategoryModel categoryModel;
    RecyclerView recyclerview;
    LinearLayout norecord;
    JobAdapter jobAdapter;
    ArrayList<JobModel> jobModels=new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 200;
    Spinner spnr;
    ArrayList<CityModel> cityModels=new ArrayList<>();
    ArrayAdapter<CityModel> cityModelArrayAdapter;
    String idc;
    String topic;

    ShimmerFrameLayout shimmerLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobs);
        categoryModel=(CategoryModel) getIntent().getSerializableExtra("data");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText(categoryModel.getCat_name());

        topic=categoryModel.getCat_name();
        recyclerview=findViewById(R.id.recyclerview);
        norecord=findViewById(R.id.norecord);
        spnr=findViewById(R.id.spnr);
        shimmerLayout=findViewById(R.id.shimmerLayout);
        shimmerLayout.startShimmer();

        cityModels = new ArrayList<>();
        cityModelArrayAdapter = new ArrayAdapter<CityModel>(PageJob.this, android.R.layout.simple_spinner_item, cityModels);
        cityModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr.setAdapter(cityModelArrayAdapter);
        AreaJsonCall();

        spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnr.getSelectedItemPosition();
                idc=String.valueOf(cityModels.get(pos).getId());
                BindData(idc);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        GridLayoutManager mLayoutManager = new GridLayoutManager(PageJob.this, 1);
        recyclerview.setLayoutManager(mLayoutManager);
        jobAdapter = new JobAdapter(PageJob.this, jobModels, R.layout.item_job);
        recyclerview.setAdapter(jobAdapter);
        recyclerview.setItemViewCacheSize(jobModels.size());

        callAtRuntime();

    }

    private void callAtRuntime() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

                && checkSelfPermission(Manifest.permission.CALL_PHONE)

                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);

        }

        else {

        }
    }

    private void BindData(String idc) {
        jobModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageJob.this);
        param.add(new NetParam("jobcat",categoryModel.getId()));
        param.add(new NetParam("city",idc));
        jc.SendRequest("get_job", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                shimmerLayout.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    JobModel product = new JobModel();
                    product.setCompany_nm(obj.getString("company_nm"));
                    product.setId(obj.getString("id"));
                    product.setJob_name(obj.getString("job_name"));
                    product.setJobdesp(obj.getString("jobdesp"));
                    product.setState_nm(obj.getString("state_nm"));
                    product.setCityname(obj.getString("cityname"));
                    product.setQualification(obj.getString("qualification"));
                    product.setAd_img(obj.getString("ad_img"));
                    product.setContactno(obj.getString("contactno"));
                    product.setState(obj.getString("state"));
                    jobModels.add(product);
                }
                jobAdapter.notifyDataSetChanged();
                BindDataView();
            }

            @Override
            public void onPostError(String msg) {
                BindDataView();
            }
        }, "", "Loading..");
    }

    private void BindDataView() {
        if(jobModels.size()>0){
            norecord.setVisibility(View.GONE);
        }
        else{
            norecord.setVisibility(View.VISIBLE);
        }
    }

    private void AreaJsonCall() {
        cityModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(PageJob.this);
        jc.SendRequest("get_city", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    CityModel mod = new CityModel();
                    mod.setId(obj.getString("id"));
                    mod.setCityname(obj.getString("cityname"));
                    cityModels.add(mod);
                    cityModelArrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "LOGIN", "Please wait while getting..");
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callAtRuntime();
            } else {
                UserUtil.ShowMsg("Call Permission Denied. Try Again!",PageJob.this);
            }

        }

    }
}
