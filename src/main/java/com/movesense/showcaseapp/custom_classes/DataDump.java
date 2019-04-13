package com.movesense.showcaseapp.custom_classes;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class DataDump {
    @Expose
    public String type;
    @Expose
    public List<DataSet> dataset = new ArrayList<DataSet>();
    private transient boolean gyroOn;
    private transient boolean accOn;
    public DataDump(String type)
    {
        this.type = type;
        dataset.add(new DataSet(Constants.ARRAY_ACC));
        dataset.add(new DataSet(Constants.ARRAY_GYRO));
    }
    public boolean addData(double x, double y, double z, int timeStamp, String sensor)
    {
        switch (sensor){
            case Constants.ARRAY_ACC:

                Log.d("TestLogAdd", "adding data to acc");
                accOn = true;
                if(gyroOn&&accOn) {
                    dataset.get(0).addData(x, y, z, timeStamp);
                    return true;
                }
                break;
            case Constants.ARRAY_GYRO:

                Log.d("TestLogAdd", "adding data to gyro");
                gyroOn = true;
                if(gyroOn&&accOn) {
                    dataset.get(1).addData(x, y, z, timeStamp);
                    return true;
                }
            default:
                break;
        }
        return false;

    }
    public String createData()
    {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
    private class DataSet {
        @Expose
        public List<Data> data;
        @Expose
        public String sensor;
        public void addData(double x, double y, double z, int timeStamp)
        {
            data.add(new Data(x,y,z,timeStamp));
        }
        public DataSet(String sensor)
        {
            this.sensor = sensor;
            data = new ArrayList<Data>();
        }
        private class Data {
            private int timeStamp;
            private double x;
            private double y;
            private double z;

            public Data(double x, double y, double z, int timeStamp) {
                this.x = x;
                this.y = y;
                this.z = z;
                this.timeStamp = timeStamp;

            }

        }
    }
}
