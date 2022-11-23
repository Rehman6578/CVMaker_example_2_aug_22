package com.example.resume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.resume.activities.MainActivity;

public class Splash extends Activity  {


    Handler handler = new Handler();
    Runnable ranable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities.splash);

        Context context=this;


                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
//            }
//        });

        ranable = new Runnable() {
            @Override
            public void run() {

                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
					finish();
//                }

            }
        };

        handler.postDelayed(ranable, 5000);
    }



    public void onBackPressed() {
        this.handler.removeCallbacks(this.ranable);
        super.onBackPressed();

    }
}