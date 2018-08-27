package com.example.risa.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_view);
    }
    public void showCustomLayoutToast(View view) {
        // get Activity's layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // inflate layout from XML (toast.xml), LinearLayout with ImageView and TextView
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast));
        // get image from resource
        ImageView image = (ImageView) layout.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.heart);
        // set text
        TextView text = (TextView) layout.findViewById(R.id.textView);
        text.setText("Add to favorite");
        // create and show toast
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void goBack(View view){
        finish();
    }
}
