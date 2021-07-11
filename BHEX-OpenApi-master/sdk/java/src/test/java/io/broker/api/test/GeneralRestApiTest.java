package io.broker.api.test;

import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerApiRestClient;
import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.client.domain.general.BrokerInfo;
import io.broker.api.test.constant.Constants;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GeneralRestApiTest {


    public static void main(String[] args) {

        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL);
        BrokerApiRestClient client = factory.newRestClient();

        System.out.println("\n ------BrokerInfo-----");
       BrokerInfo brokerInfo = client.getBrokerInfo();
        //client.ping();
        //System.out.println(brokerInfo);

    }

    public interface BlogService {
        /**
         * method 表示请求的方法，区分大小写，retrofit 不会做处理
         * path表示路径
         * hasBody表示是否有请求体
         */
        @GET("openapi/v1/brokerInfo")
        Call<ResponseBody> getBrokerInfo();
    }

//    public static void main(String[] args) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.hbtc.com/")
//                .build();
//
//        BlogService service = retrofit.create(BlogService.class);
//        Call<ResponseBody> call = service.getBrokerInfo();
//        try {
//            Response<ResponseBody> response = call.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
