package com.example.dekutteleconsult;

import com.example.dekutteleconsult.Notification.MyResponse;
import com.example.dekutteleconsult.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
//CLOUD MESSEGING FOR NOTIFICATIONS

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAieK15eQ:APA91bECgsZxJ3_XT95GgnmVJfdmMAMO0wk7sXUWMwFWd3cvDbVS577_Ic1waZUeAtGAF9u9azptijIGnVUUyFcvTQr-LD3cGIXi1QbQN_lTDjaxytUzRVmIIwmfwW07GttsICRwVWA6"
            }
    )





    @POST("fcm/send")
    Call<MyResponse>sendNotification(@Body Sender body);

}
