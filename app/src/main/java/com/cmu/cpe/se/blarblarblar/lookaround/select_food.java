package com.cmu.cpe.se.blarblarblar.lookaround;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class select_food extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);

        ImageButton back_button = (ImageButton)findViewById(R.id.imageBackButton4);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), Select_food_or_gadget.class);
                finish();
            }
        });
        ImageView food_button1 = (ImageView)findViewById(R.id.imageView_food1);
        food_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j =  new Intent(getApplicationContext(), select_Shop.class);
                startActivity(j);
            }
        });
        ImageView food_button2 = (ImageView)findViewById(R.id.imageView_food2);
        food_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j =  new Intent(getApplicationContext(), select_Shop.class);
                startActivity(j);
            }
        });
        ImageView food_button3 = (ImageView)findViewById(R.id.imageView_food3);
        food_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j =  new Intent(getApplicationContext(), select_Shop.class);
                startActivity(j);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
