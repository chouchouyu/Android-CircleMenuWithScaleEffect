package com.example.susan.balldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.susan.balldemo.CircleLayout.OnItemClickListener;
import com.example.susan.balldemo.CircleLayout.OnItemSelectedListener;

public class Seller_HomePageActivity extends AppCompatActivity implements OnItemSelectedListener,
        OnItemClickListener {
    protected CircleLayout circleLayout;
    protected TextView selectedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_homepage);
        // Set listeners
        circleLayout = (CircleLayout) findViewById(R.id.circle_layout);
        circleLayout.setOnItemSelectedListener(this);
        circleLayout.setOnItemClickListener(this);

        selectedTextView = (TextView) findViewById(R.id.selected_textView);

        String name = null;
        View view = circleLayout.getSelectedItem();
        if (view instanceof RippleCircle) {
            name = ((RippleCircle) view).getName();
        }
        selectedTextView.setText(name);
    }

    @Override
    public void onItemSelected(View view) {
        final String name;
        if (view instanceof RippleCircle) {
            name = ((RippleCircle) view).getName();
        } else {
            name = null;
        }
        selectedTextView.setText(name);


    }

    @Override
    public void onItemClick(View view) {
        String name = null;
        if (view instanceof RippleCircle) {
            name = ((RippleCircle) view).getName();
        }
        //TODO 在此处做跳转动作

        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();


    }
}