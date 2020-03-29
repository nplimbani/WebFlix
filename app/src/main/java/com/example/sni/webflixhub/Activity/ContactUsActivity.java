package com.example.sni.webflixhub.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sni.webflixhub.R;

public class ContactUsActivity extends AppCompatActivity {

    TextView mailid,txt_fblink,txt_instalink,txt_twitterlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        mailid=(TextView)findViewById(R.id.txt_mailid);
        txt_fblink=(TextView)findViewById(R.id.txt_fblink);
        txt_instalink=(TextView)findViewById(R.id.txt_instalink);
        txt_twitterlink=(TextView)findViewById(R.id.txt_twitterlink);


        mailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mailid.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Add your subject...");
                i.putExtra(Intent.EXTRA_TEXT   , "Thank for using WebFlix...");
                i.putExtra(Intent.ACTION_SENDTO,mailid.getText().toString());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactUsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_fblink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(txt_fblink.getText().toString()));
                startActivity(intent);
            }
        });

        txt_instalink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(txt_instalink.getText().toString()));
                startActivity(intent);
            }
        });
        txt_twitterlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(txt_twitterlink.getText().toString()));
                startActivity(intent);
            }
        });
    }
}
