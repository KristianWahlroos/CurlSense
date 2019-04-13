package com.movesense.showcaseapp.custom_classes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.movesense.mds.Mds;
import com.movesense.mds.MdsException;
import com.movesense.mds.MdsNotificationListener;
import com.movesense.mds.MdsSubscription;
import com.movesense.mds.internal.connectivity.BleManager;
import com.movesense.mds.internal.connectivity.MovesenseConnectedDevices;
import com.movesense.showcaseapp.BaseActivity;
import com.movesense.showcaseapp.R;
import com.movesense.showcaseapp.bluetooth.MdsRx;
import com.movesense.showcaseapp.model.AngularVelocity;
import com.movesense.showcaseapp.model.LinearAcceleration;
import com.movesense.showcaseapp.model.MagneticField;
import com.movesense.showcaseapp.model.MdsConnectedDevice;
import com.movesense.showcaseapp.model.TemperatureSubscribeModel;
import com.movesense.showcaseapp.section_00_mainView.MainViewActivity;
import com.movesense.showcaseapp.section_02_multi_connection.sensor_usage.MultiSensorUsageActivity;
import com.movesense.showcaseapp.section_02_multi_connection.sensor_usage.MultiSensorUsageContract;
import com.movesense.showcaseapp.section_02_multi_connection.sensor_usage.MultiSensorUsagePresenter;
import com.movesense.showcaseapp.utils.FormatHelper;
import com.movesense.showcaseapp.utils.ThrowableToastingAction;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


public class MultiSensorDataGatherer  {
    private final String LINEAR_ACC_PATH = "Meas/Acc/26";
    private final String ANGULAR_VELOCITY_PATH = "Meas/Gyro/26";
    private MdsSubscription mMdsLinearAccSubscription;
    private MdsSubscription mMdsAngularVelocitySubscription;
    private DataDump dataDump;
    private int debugCounter = 0;
    public static final MultiSensorDataGatherer Instance = new MultiSensorDataGatherer();
    public String getData()
    {
        String data = dataDump.createData();
        //dataDump = null;
        return data;
    }

    public void enableGathering(Activity activity, String type) {
        dataDump = new DataDump(type);
        Log.d("testLogx" ,"GATHERING ENABLED");
        mMdsLinearAccSubscription = Mds.builder().build(activity).subscribe("suunto://MDS/EventListener",
                FormatHelper.formatContractToJson(MovesenseConnectedDevices.getConnectedDevice(0)
                        .getSerial(), LINEAR_ACC_PATH), new MdsNotificationListener() {
                    @Override
                    public void onNotification(String s) {
                        LinearAcceleration linearAccelerationData = new Gson().fromJson(
                                s, LinearAcceleration.class);

                        if (linearAccelerationData != null) {

                            LinearAcceleration.Array arrayData = linearAccelerationData.body.array[0];
                            dataDump.addData(arrayData.x, arrayData.y, arrayData.z, (int) (System.currentTimeMillis()), Constants.ARRAY_ACC);

                            Log.d("testLog","x " + arrayData.x +" y " + arrayData.y +" z " + arrayData.z);

                        }
                        Log.d("testLog2","Things are happening");
                    }

                    @Override
                    public void onError(MdsException e) {

                    }
                });

        mMdsAngularVelocitySubscription = Mds.builder().build(activity).subscribe("suunto://MDS/EventListener",
                FormatHelper.formatContractToJson(MovesenseConnectedDevices.getConnectedDevice(0)
                        .getSerial(), ANGULAR_VELOCITY_PATH), new MdsNotificationListener() {
                    @Override
                    public void onNotification(String s) {
                        AngularVelocity angularVelocity = new Gson().fromJson(
                                s, AngularVelocity.class);

                        if (angularVelocity != null) {

                            AngularVelocity.Array arrayData = angularVelocity.body.array[0];
                            dataDump.addData(arrayData.x, arrayData.y, arrayData.z, (int) (System.currentTimeMillis()), Constants.ARRAY_GYRO);

                        }
                        Log.d("testLog2","Things are happening");
                    }

                    @Override
                    public void onError(MdsException e) {

                    }
                });


    }

}
