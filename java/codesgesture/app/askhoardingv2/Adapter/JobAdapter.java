package codesgesture.app.askhoardingv2.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import codesgesture.app.askhoardingv2.BuildConfig;
import codesgesture.app.askhoardingv2.Model.JobModel;
import codesgesture.app.askhoardingv2.R;
import codesgesture.app.askhoardingv2.Services.JsonCallbacks;
import codesgesture.app.askhoardingv2.Services.NetParam;
import codesgesture.app.askhoardingv2.Services.UserUtil;
import codesgesture.app.askhoardingv2.Utils.CallJsonWithoutProgress;
import codesgesture.app.askhoardingv2.Utils.Constants;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private ArrayList<JobModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    ProgressDialog mProgressDialog;
    AsyncTask mMyTask;

    public JobAdapter(Context context, ArrayList<JobModel> arrayList, int layout) {
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
        final JobModel data = arrayList.get(i);

        holder.jobdesp.setText(data.getJobdesp());
        holder.jobnm.setText(data.getJob_name());
        holder.loc.setText(data.getCityname()+", "+data.getState_nm());
        holder.exp.setText(data.getQualification());
        holder.amt.setText("Not-Disclosed");
        holder.compnynm.setText(data.getCompany_nm());

        if(data.getState().equals("") || data.getState() == null){
            Uri uri = Uri.parse(Constants.BASEURI2+data.getAd_img());
            Glide.with(context).load(uri).into(holder.adimg);
        }else {
            Uri uri = Uri.parse(Constants.WEBURI+data.getAd_img());
            Glide.with(context).load(uri).into(holder.adimg);
        }

        if(data.getAd_img().equals("") || data.getAd_img() == null){
            holder.click.setVisibility(View.VISIBLE);
            holder.click2.setVisibility(View.GONE);
        }else {
            holder.click.setVisibility(View.GONE);
            holder.click2.setVisibility(View.VISIBLE);
        }

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savecount("share");
                BitmapDrawable bitmapDrawable=(BitmapDrawable) holder.adimg.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                Uri uri1;
                String text ="नौकरी के लिए डाउनलोड करें Ask Hoarding app" + "\n https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                uri1=saveImage(bitmap,context.getApplicationContext());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_TEXT,text);
                intent.putExtra(Intent.EXTRA_STREAM,uri1);
                context.startActivity(Intent.createChooser(intent,"share image"));

            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savecount("call");
                String contact = data.getContactno();
                OpenCallDialog(context,contact);
                // UserUtil.CallDialog(context,contact);
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savecount("download");
                downloadImageNew(data.getAd_img(),Constants.BASEURI2+data.getAd_img());

              //  new DownloadImage().execute(uri);
            }
        });

        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savecount("whatsapp");
                String number=data.getContactno();
                String url = "https://api.whatsapp.com/send?phone="+number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);


//                savecount("whatsapp");
//
//                BitmapDrawable bitmapDrawable=(BitmapDrawable) holder.adimg.getDrawable();
//                Bitmap bitmap=bitmapDrawable.getBitmap();
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setPackage("com.whatsapp");
//                intent.setType("image/*");
//                Uri uri1;
//                String text ="नौकरी के लिए डाउनलोड करें Ask Hoarding app" + "\n https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
//                uri1=saveImage(bitmap,context.getApplicationContext());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(Intent.EXTRA_TEXT,text);
//                intent.putExtra(Intent.EXTRA_STREAM,uri1);
//
//                try {
//                    context.startActivity(intent);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    UserUtil.ShowMsg("Whatsapp have not been installed.",context);
//                }

            }
        });


    }

    private void OpenCallDialog(Context context,String contact) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_call);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button btcall = (Button) dialog.findViewById(R.id.btcall);
        Button btcancel = (Button) dialog.findViewById(R.id.btcancel);
        TextView txnumber=dialog.findViewById(R.id.txnumber);
        txnumber.setText("Dial : +91-"+contact);

        btcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact.toString().trim()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(callIntent);
            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void savecount(String str) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress((Activity) context);
        param.add(new NetParam("type",str));
        jc.SendRequest("clickbtn", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {

            }
            @Override
            public void onPostError(String msg) {
            }
        }, " ", "Loading..");
    }

    private Uri saveImage(Bitmap bitmap, Context applicationContext) {
        File file=new File(context.getCacheDir(),"images");
        Uri uri=null;
        try{

            file.mkdir();
            File file1=new File(file,"img1.jpg");
            FileOutputStream outputStream=new FileOutputStream(file1);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
            outputStream.flush();
            outputStream.close();
            uri= FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                    "codesgesture.app.askhoardingv2"+".provider",file1);


        }catch (IOException e){

            Log.d("TAG","Exception"+e.getMessage());
        }
return  uri;
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobnm,compnynm,exp,amt,loc,jobdesp;
        ImageView adimg;
        LinearLayout share,call,download,whatsapp;
        LinearLayout click,click2;

        ViewHolder(View view) {
            super(view);

            compnynm = view.findViewById(R.id.compnynm);
            jobnm = view.findViewById(R.id.jobnm);
            exp = view.findViewById(R.id.exp);
            amt = view.findViewById(R.id.amt);
            loc = view.findViewById(R.id.loc);
            jobdesp = view.findViewById(R.id.jobdesp);
            share = view.findViewById(R.id.share);
            adimg = view.findViewById(R.id.adimg);
            call = view.findViewById(R.id.call);
            click = view.findViewById(R.id.click);
            click2 = view.findViewById(R.id.click2);
            whatsapp = view.findViewById(R.id.whatsapp);
            download = view.findViewById(R.id.download);

        }
    }

    public void updateList(ArrayList<JobModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

    private void downloadImageNew(String filename, String downloadUrlOfImage){

        String[] parts = filename.split("/Jobad/");
        String part1=parts[0];
        String part2=parts[1];
        String[] extension = part2.split("[.]");
        String filenm= extension[0];
        String extnsn = extension[1];


        try{
            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(part2)
                    .setMimeType(extnsn) // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,File.separator + filename + ".jpg");
            dm.enqueue(request);
            Toast.makeText(context, "Image download started.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}