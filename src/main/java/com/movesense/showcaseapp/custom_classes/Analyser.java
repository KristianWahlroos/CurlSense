package com.movesense.showcaseapp.custom_classes;

import android.util.Log;
import android.widget.TextView;

public class Analyser {
    public final static Analyser Instance = new Analyser();
    public final int THRESHOLD_GYRO = 55;
    public final int THRESHOLD_ACC = 5;
    public int accPositiveCounter;
    public int accNegativeCounter;
    public int gyroNegativeCounter;
    public int gyroPositiveCounter;
    public final int maxCount = 40;

    public void analyseGyro(double x)
    {

        if(gyroPositiveCounter>0)
        {
            gyroPositiveCounter--;
        }
        if(gyroNegativeCounter >0)
        {
            gyroNegativeCounter --;
        }
        if(x>=THRESHOLD_GYRO)
        {
            gyroPositiveCounter += 8;
            if(gyroPositiveCounter>=maxCount)
            {
                gyroPositiveCounter=maxCount;
            }
        }
        else if(x<=-THRESHOLD_GYRO)
        {
            gyroNegativeCounter += 8;
            if(gyroNegativeCounter >=maxCount)
            {
                gyroNegativeCounter =maxCount;
            }
        }
        if(accNegativeCounter==0)
            return;
        if(accPositiveCounter==0)
            return;
        if(gyroNegativeCounter==0)
            return;
        if(gyroPositiveCounter==0)
            return;
    }

    public void analyseAcc(double x, TextView textView)
    {
        if(accPositiveCounter>0)
        {
            accPositiveCounter--;
        }
        if(accNegativeCounter>0)
        {
            accNegativeCounter--;
        }
        if(x>=THRESHOLD_ACC)
        {
            accPositiveCounter += 6;
            if(accPositiveCounter>=maxCount)
            {
                accPositiveCounter=maxCount;
            }
        }
        else if(x<=-THRESHOLD_ACC)
        {
            accNegativeCounter += 6;
            if(accNegativeCounter>=maxCount)
            {
                accNegativeCounter=maxCount;
            }
        }
        Log.d("hellox", "x" + x);
        Log.d("hellox", "xacc-" + accNegativeCounter);
        Log.d("hellox", "xacc+" + accPositiveCounter);
        Log.d("hellox", "xgyro-" + gyroNegativeCounter);
        Log.d("hellox", "xgyro+" + gyroPositiveCounter);
        textView.setText("Not Curling");
        if(accNegativeCounter==0)
            return;
        if(accPositiveCounter==0)
            return;
        if(gyroNegativeCounter==0)
            return;
        if(gyroPositiveCounter==0)
            return;
        textView.setText("Curling");

    }
}
