package codesgesture.app.askhoardingv2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import codesgesture.app.askhoardingv2.Model.CategoryModel;
import codesgesture.app.askhoardingv2.Model.CityModel;
import codesgesture.app.askhoardingv2.Services.CallJson;
import codesgesture.app.askhoardingv2.Services.JsonCallbacks;
import codesgesture.app.askhoardingv2.Services.NetParam;
import codesgesture.app.askhoardingv2.Services.UserUtil;
import codesgesture.app.askhoardingv2.Services.Utility;
import codesgesture.app.askhoardingv2.Utils.CallJsonWithoutProgress;


public class PageJobPost extends AppCompatActivity  {

    Context context;
    EditText mob1,mob2,mob3;
    Spinner spnrcat;
    String spnrid;

    TextView filename;
    String base64;
    Button btnpost,choosefile;

    ArrayList<CategoryModel> categoryModels=new ArrayList<>();
    ArrayAdapter<CategoryModel> category_array;

    Spinner spnr;
    ArrayList<CityModel> cityModels=new ArrayList<>();
    ArrayAdapter<CityModel> cityModelArrayAdapter;
    String idc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_post);
        context=this;
        requestMultiplePermissions();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        btnpost=findViewById(R.id.btnpost);
        choosefile=findViewById(R.id.choosefile);
        filename=findViewById(R.id.filename);
        mob1=findViewById(R.id.mob1);
        mob2=findViewById(R.id.mob2);
        mob3=findViewById(R.id.mob3);
        spnrcat=findViewById(R.id.spnrcat);
        spnr=findViewById(R.id.spnr);

        categoryModels = new ArrayList<>();
        category_array = new ArrayAdapter<CategoryModel>(this, android.R.layout.simple_spinner_item, categoryModels);
        category_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrcat.setAdapter(category_array);
        AreaJsonCall();

        cityModels = new ArrayList<>();
        cityModelArrayAdapter = new ArrayAdapter<CityModel>(PageJobPost.this, android.R.layout.simple_spinner_item, cityModels);
        cityModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr.setAdapter(cityModelArrayAdapter);
        AreaCityCall();

        spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spnr.getSelectedItemPosition();
                idc=String.valueOf(cityModels.get(pos).getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnrcat.setAdapter(adapter);

        spnrcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int pos = spnrcat.getSelectedItemPosition();
                spnrid=String.valueOf(categoryModels.get(pos).getId());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mob1.getText().length()==0){
                    mob1.setError("Please enter mobile no.");
                }else {
                    JobPost();
                }
            }
        });

        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

    }

    private void AreaCityCall() {
        cityModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(PageJobPost.this);
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

    private void AreaJsonCall() {
        categoryModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(this);
        jc.SendRequest("get_category", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    CategoryModel mod = new CategoryModel();
                    mod.setId(obj.getString("id"));
                    mod.setCat_name(obj.getString("cat_name"));
                    categoryModels.add(mod);
                    category_array.notifyDataSetChanged();
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "", "Please wait while getting..");
    }

    private void JobPost() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson((Activity) context);
        param.add(new NetParam("mobile1",mob1.getText().toString()));
        param.add(new NetParam("mobile2",mob2.getText().toString()));
        param.add(new NetParam("mobile3",mob3.getText().toString()));
        param.add(new NetParam("jobcat",spnrid));
        param.add(new NetParam("base64",base64));
        param.add(new NetParam("city",idc));
        param.add(new NetParam("filenm",filename.getText().toString()));
        jc.SendRequest("post_job", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Thank you! Job is posting succesfully after approval seeing this !!",context);
                finish();
            }
            @Override
            public void onPostError(String msg) {
            }
        }, " ", "Loading..");
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String fileName = getFileName(uri);
            try {
                base64 = Base64.encodeToString(getBytesFromUri(uri, context), Base64.DEFAULT);
                String cls=spnrid;
                filename.setText(Utility.ImageName(cls));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static byte[] getBytesFromUri(Uri uri, Context context) throws IOException {
        InputStream iStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = iStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }



}