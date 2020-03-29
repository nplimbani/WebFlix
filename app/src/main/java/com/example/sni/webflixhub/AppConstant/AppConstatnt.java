package com.example.sni.webflixhub.AppConstant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sni.webflixhub.R;

public class AppConstatnt {

    public static String all_response_api = "http://webflixhub.xyz/app_dashboard/v1/getData?keyword=all";
    public static String dynm_response_api = "http://webflixhub.xyz/app_dashboard/v1/getVideo?id=";
    public static String movie_response_api = "http://webflixhub.xyz/app_dashboard/v1/getData?keyword=Movies";
    public static String webseries_response_api = "http://webflixhub.xyz/app_dashboard/v1/getData?keyword=WEb%20Series";
    public static String comics_response_api = "http://webflixhub.xyz/app_dashboard/v1/getData?keyword=Comics";
    public static String like_response_api = "https://webflixhub.xyz/app_dashboard/v1/increamentLike?id=";
    public static String view_response_api = "https://webflixhub.xyz/app_dashboard/v1/increamentView?id=";
    public static String feedback_api = "https://webflixhub.xyz/app_dashboard/v1/sendFeedback?msg=";
    public static String mediarq_api = "http://webflixhub.xyz/app_dashboard/v1/sendRequest?msg=";
    public static String search_api = "http://webflixhub.xyz/app_dashboard/v1/searchVideo?search=";
    public static String particular_webseries_season_api = "http://webflixhub.xyz/app_dashboard/v1/getSeason?id=";
    public static String season_episode_api = "http://webflixhub.xyz/app_dashboard/v1/getEpisode?id=";
    public static String particulat_webseries_api = "http://webflixhub.xyz/app_dashboard/v1/getWebSeries?id=";
    public static String cardview_image_api = "http://webflixhub.xyz/app_dashboard/v1/getImages";
    public static String movie_language_api = "http://webflixhub.xyz/app_dashboard/v1/getLanguageData?keyword=";
    public static String d3_k4_api = "http://webflixhub.xyz/app_dashboard/v1/getData?keyword=";

    public static AlertDialog.Builder alertDialog;
    public static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public static void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showDialog(final Context context){
        alertDialog= new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        //alertDialog.setIcon(R.drawable.icon_attention);
        alertDialog.setMessage("Oops...! There in no internet connection. Please, check it.");
        alertDialog.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(context,context.getClass());
                context.startActivity(intent);
            }
        });
        alertDialog.show();
    }




}
