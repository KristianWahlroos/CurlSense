package com.movesense.showcaseapp.custom_classes;



import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class JsonSender{

    public static final JsonSender Instance = new JsonSender();
    private String serverAddress = "https://wet-stingray-76.localtunnel.me/data";

    //Uploads a Json through Http request.
    public void jsonUpload(Activity activity, final String json)
    {

        Log.d("logDataCool", json);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //Creates a client that timeouts in 60 seconds instead of the default 10 seconds
                //Has now enough wait time that the analysis can finish in time.
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                clientBuilder.readTimeout(60, TimeUnit.SECONDS);
                OkHttpClient client = clientBuilder.build();
                /**
                 * {@link Constants#UPLOADED_FILE_P} name saved to server-side.
                 */
                RequestBody formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);// RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                        /*.build();
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("json","json", RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                        .build();*/
                Request request = new Request.Builder().url(serverAddress)
                        .post(formBody).build();

                //In server php script handles the request.
                //TODO create more pleasant url in the future.
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("ERROR : " + response);
                    }
                    else
                    {
                        //Prints a log after the server has returned it.
                        Log.d("hello", "Here is the response: " + response.body().string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }


}
