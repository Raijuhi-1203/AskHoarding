package codesgesture.app.askhoardingv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.askhoardingv2.R;
import codesgesture.app.askhoardingv2.Adapter.CategoryAdapter;
import codesgesture.app.askhoardingv2.Model.AdsModel;
import codesgesture.app.askhoardingv2.Model.CategoryModel;
import codesgesture.app.askhoardingv2.Services.JsonCallbacks;
import codesgesture.app.askhoardingv2.Services.NetParam;
import codesgesture.app.askhoardingv2.Utils.CallJsonWithoutProgress;
import codesgesture.app.askhoardingv2.Utils.Constants;

public class DashBoard extends AppCompatActivity  {
    RecyclerView recyclerview1,recyclerview2,recyclerview3,recyclerview4,recyclerview5,recyclerview6,recyclerview7,recyclerview8,recyclerview9,recyclerview10,recyclerview11;
    CategoryAdapter categoryAdapter,categoryAdapter2,categoryAdapter3,categoryAdapter4,categoryAdapter5,categoryAdapter6,categoryAdapter7,categoryAdapter8,categoryAdapter9,categoryAdapter10,categoryAdapter11;
    ArrayList<CategoryModel> categoryModels=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels2=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels3=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels4=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels5=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels6=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels7=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels8=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels9=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels10=new ArrayList<>();
    ArrayList<CategoryModel> categoryModels11=new ArrayList<>();
    SliderLayout slider;
    ArrayList<AdsModel> adsModels=new ArrayList<>();
    LinearLayout menu,cat,home,jpost;  //,notify
    private ShimmerFrameLayout shimmerslider;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ShimmerFrameLayout shimmerFrameLayout2;
    private ShimmerFrameLayout shimmerFrameLayout3;
    private ShimmerFrameLayout shimmerFrameLayout4;
    private ShimmerFrameLayout shimmerFrameLayout5;
    private ShimmerFrameLayout shimmerFrameLayout6;
    private ShimmerFrameLayout shimmerFrameLayout7;
    private ShimmerFrameLayout shimmerFrameLayout8;
    TextView titlecat,titlecat2,titlecat3,titlecat4,titlecat5,titlecat6,titlecat7,titlecat8,titlecat9,titlecat10,titlecat11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        recyclerview1=findViewById(R.id.recyclerview1);
        recyclerview2=findViewById(R.id.recyclerview2);
        recyclerview3=findViewById(R.id.recyclerview3);
        recyclerview4=findViewById(R.id.recyclerview4);
        recyclerview5=findViewById(R.id.recyclerview5);
        recyclerview6=findViewById(R.id.recyclerview6);
        recyclerview7=findViewById(R.id.recyclerview7);
        recyclerview8=findViewById(R.id.recyclerview8);
        recyclerview9=findViewById(R.id.recyclerview9);
        recyclerview10=findViewById(R.id.recyclerview10);
        recyclerview11=findViewById(R.id.recyclerview11);

        shimmerslider = findViewById(R.id.shimmerslider);
        shimmerslider.startShimmer();

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();

        shimmerFrameLayout2 = findViewById(R.id.shimmerLayout2);
        shimmerFrameLayout2.startShimmer();

        shimmerFrameLayout3 = findViewById(R.id.shimmerLayout3);
        shimmerFrameLayout3.startShimmer();

        shimmerFrameLayout4 = findViewById(R.id.shimmerLayout4);
        shimmerFrameLayout4.startShimmer();

        shimmerFrameLayout5 = findViewById(R.id.shimmerLayout5);
        shimmerFrameLayout5.startShimmer();

        shimmerFrameLayout6 = findViewById(R.id.shimmerLayout6);
        shimmerFrameLayout6.startShimmer();

        shimmerFrameLayout7 = findViewById(R.id.shimmerLayout7);
        shimmerFrameLayout7.startShimmer();

        shimmerFrameLayout8 = findViewById(R.id.shimmerLayout8);
        shimmerFrameLayout8.startShimmer();


        cat=findViewById(R.id.cat);
        menu=findViewById(R.id.menu);
        home=findViewById(R.id.home);
        jpost=findViewById(R.id.jpost);

        slider=findViewById(R.id.slider);
        titlecat=findViewById(R.id.titlecat);
        titlecat2=findViewById(R.id.titlecat2);
        titlecat3=findViewById(R.id.titlecat3);
        titlecat4=findViewById(R.id.titlecat4);
        titlecat5=findViewById(R.id.titlecat5);
        titlecat6=findViewById(R.id.titlecat6);
        titlecat7=findViewById(R.id.titlecat7);
        titlecat8=findViewById(R.id.titlecat8);
        titlecat9=findViewById(R.id.titlecat9);
        titlecat10=findViewById(R.id.titlecat10);
        titlecat11=findViewById(R.id.titlecat11);

        GridLayoutManager mLayoutManager = new GridLayoutManager(DashBoard.this, 3);
        recyclerview1.setLayoutManager(mLayoutManager);
        categoryAdapter = new CategoryAdapter(DashBoard.this, categoryModels, R.layout.item_category);
        recyclerview1.setAdapter(categoryAdapter);
        recyclerview1.setItemViewCacheSize(categoryModels.size());
        BindData(titlecat.getText().toString(), categoryAdapter, categoryModels);

        GridLayoutManager mLayoutManager2 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview2.setLayoutManager(mLayoutManager2);
        categoryAdapter2 = new CategoryAdapter(DashBoard.this, categoryModels2, R.layout.item_category);
        recyclerview2.setAdapter(categoryAdapter2);
        recyclerview2.setItemViewCacheSize(categoryModels2.size());
        BindData(titlecat2.getText().toString(), categoryAdapter2, categoryModels2);

        GridLayoutManager mLayoutManager3 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview3.setLayoutManager(mLayoutManager3);
        categoryAdapter3 = new CategoryAdapter(DashBoard.this, categoryModels3, R.layout.item_category);
        recyclerview3.setAdapter(categoryAdapter3);
        recyclerview3.setItemViewCacheSize(categoryModels3.size());
        BindData(titlecat3.getText().toString(), categoryAdapter3, categoryModels3);

        GridLayoutManager mLayoutManager4 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview4.setLayoutManager(mLayoutManager4);
        categoryAdapter4 = new CategoryAdapter(DashBoard.this, categoryModels4, R.layout.item_category);
        recyclerview4.setAdapter(categoryAdapter4);
        recyclerview4.setItemViewCacheSize(categoryModels4.size());
        BindData(titlecat4.getText().toString(), categoryAdapter4, categoryModels4);

        GridLayoutManager mLayoutManager5 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview5.setLayoutManager(mLayoutManager5);
        categoryAdapter5 = new CategoryAdapter(DashBoard.this, categoryModels5, R.layout.item_category);
        recyclerview5.setAdapter(categoryAdapter5);
        recyclerview5.setItemViewCacheSize(categoryModels5.size());
        BindData(titlecat5.getText().toString(), categoryAdapter5, categoryModels5);

        GridLayoutManager mLayoutManager6 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview6.setLayoutManager(mLayoutManager6);
        categoryAdapter6 = new CategoryAdapter(DashBoard.this, categoryModels6, R.layout.item_category);
        recyclerview6.setAdapter(categoryAdapter6);
        recyclerview6.setItemViewCacheSize(categoryModels6.size());
        BindData(titlecat6.getText().toString(), categoryAdapter6, categoryModels6);

        GridLayoutManager mLayoutManager7 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview7.setLayoutManager(mLayoutManager7);
        categoryAdapter7 = new CategoryAdapter(DashBoard.this, categoryModels7, R.layout.item_category);
        recyclerview7.setAdapter(categoryAdapter7);
        recyclerview7.setItemViewCacheSize(categoryModels7.size());
        BindData(titlecat7.getText().toString(),categoryAdapter7,categoryModels7);

        GridLayoutManager mLayoutManager8 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview8.setLayoutManager(mLayoutManager8);
        categoryAdapter8 = new CategoryAdapter(DashBoard.this, categoryModels8, R.layout.item_category);
        recyclerview8.setAdapter(categoryAdapter8);
        recyclerview8.setItemViewCacheSize(categoryModels8.size());
        BindData(titlecat8.getText().toString(),categoryAdapter8,categoryModels8);


        GridLayoutManager mLayoutManager9 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview9.setLayoutManager(mLayoutManager9);
        categoryAdapter9 = new CategoryAdapter(DashBoard.this, categoryModels9, R.layout.item_category);
        recyclerview9.setAdapter(categoryAdapter9);
        recyclerview9.setItemViewCacheSize(categoryModels9.size());
        BindData(titlecat9.getText().toString(),categoryAdapter9,categoryModels9);


        GridLayoutManager mLayoutManager10 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview10.setLayoutManager(mLayoutManager10);
        categoryAdapter10 = new CategoryAdapter(DashBoard.this, categoryModels10, R.layout.item_category);
        recyclerview10.setAdapter(categoryAdapter10);
        recyclerview10.setItemViewCacheSize(categoryModels10.size());
        BindData(titlecat10.getText().toString(),categoryAdapter10,categoryModels10);


        GridLayoutManager mLayoutManager11 = new GridLayoutManager(DashBoard.this, 3);
        recyclerview11.setLayoutManager(mLayoutManager11);
        categoryAdapter11 = new CategoryAdapter(DashBoard.this, categoryModels11, R.layout.item_category);
        recyclerview11.setAdapter(categoryAdapter11);
        recyclerview11.setItemViewCacheSize(categoryModels11.size());
        BindData(titlecat11.getText().toString(),categoryAdapter11,categoryModels11);



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,DashBoard.class));
            }
        });
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,JobCategory.class));
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,PageMenu.class));
            }
        });
        jpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,PageJobPost.class));
            }
        });
        FetchBanner();

    }


    private void BindData(String title, CategoryAdapter adapter, ArrayList<CategoryModel> models) {
        models.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DashBoard.this);
        param.add(new NetParam("catname",title));
        jc.SendRequest("get_jobcategory", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                ShimmerOff();
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    CategoryModel product = new CategoryModel();
                    product.setCat_name(obj.getString("cat_name"));
                    product.setId(obj.getString("id"));
                    product.setIcon(obj.getString("icon"));
                    product.setIsactive(obj.getString("isactive"));
                    models.add(product);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    private void ShimmerOff() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout2.stopShimmer();
        shimmerFrameLayout2.setVisibility(View.GONE);
        shimmerFrameLayout3.stopShimmer();
        shimmerFrameLayout3.setVisibility(View.GONE);
        shimmerFrameLayout4.stopShimmer();
        shimmerFrameLayout4.setVisibility(View.GONE);
        shimmerFrameLayout5.stopShimmer();
        shimmerFrameLayout5.setVisibility(View.GONE);
        shimmerFrameLayout6.stopShimmer();
        shimmerFrameLayout6.setVisibility(View.GONE);
        shimmerFrameLayout7.stopShimmer();
        shimmerFrameLayout7.setVisibility(View.GONE);
        shimmerFrameLayout8.stopShimmer();
        shimmerFrameLayout8.setVisibility(View.GONE);
    }

    private void FetchBanner() {
        adsModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DashBoard.this);
        jc.SendRequest("get_slider", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                if (json.length() > 0) {
                    shimmerslider.stopShimmer();
                    shimmerslider.setVisibility(View.GONE);
                    slider.setVisibility(View.VISIBLE);
                    JSONArray array = new JSONArray(json);
                    for (int s = 0; s < array.length(); s++) {
                        JSONObject obj = array.getJSONObject(s);
                        AdsModel model = new AdsModel();
                        model.setSlider(obj.getString("slider"));
                        model.setSlider_path(obj.getString("slider_path"));
                        adsModels.add(model);
                        DefaultSliderView defaultSliderView = new DefaultSliderView(DashBoard.this);
                        defaultSliderView.image(Constants.WEBURI+model.getSlider_path());
                        slider.addSlider(defaultSliderView);
                        defaultSliderView.setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
                        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        slider.setCustomAnimation(new DescriptionAnimation());
                        slider.setDuration(5000);

                    }
                }
            }

            @Override
            public void onPostError(String msg) {
            }
        }, "", "Wait Loading...");
    }



    @Override
    protected void onRestart() {
        super.onRestart();
//        Intent intent = new Intent(DashBoard.this, DashBoard.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
    }
}