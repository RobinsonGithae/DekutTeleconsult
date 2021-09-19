package com.example.dekutteleconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class HelpActivity extends AppCompatActivity {

    EditText ETIssue;
    Button BtnsubmitIssue;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //backbutton functionality
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        ETIssue=(EditText) findViewById(R.id.DstbngIssueET);
        BtnsubmitIssue=(Button) findViewById(R.id.btnSubmitIssue);



BtnsubmitIssue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String Issue=ETIssue.getText().toString().trim();
        if (!TextUtils.isEmpty(Issue)) {


            Intent intent = new Intent(Intent.ACTION_SEND);

            String[] developerEmails = {"robinsongithae2018@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, developerEmails);
            intent.putExtra(Intent.EXTRA_SUBJECT, "DEKUT TELECONSULT ISSUE");
            intent.putExtra(Intent.EXTRA_TEXT, Issue);
            intent.setDataAndType(Uri.parse("email"), "message/rfc822");

            Intent helpchooser = Intent.createChooser(intent, "Choose Default EmailAPP");
            startActivity(helpchooser);

        }
        else {
            ETIssue.setError("Issue Field can`t be Empty");
        }


    }
});

    }
}
