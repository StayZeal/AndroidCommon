package cn.stayzeal.andr.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 使用时需要指定TOKEN变量
 */

public class TokenInterceptor implements Interceptor {
    private static final String TAG = "TokenInterceptor";

//    public static volatile String TOKEN = GlobalConfig.getToken();

    private final List<String> names = new ArrayList<>();
    private final List<String> values = new ArrayList<>();

    /**
     * 该方法被调用不是在主线程
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();

//        MyLog.i(TAG, "Thread id:" + Thread.currentThread().getId());
//        MyLog.d(TAG, "old request:" + oldRequest.url() + " " + oldRequest.method());

        Request newRequest = oldRequest;
        if (oldRequest.method().equals("GET")) {
            HttpUrl url = oldRequest.url().newBuilder()
                    .addQueryParameter("token", GlobalConfig.getToken())
                    .build();
            newRequest = oldRequest.newBuilder().url(url).build();

//            MyLog.i(TAG, oldRequest.method() + " " + oldRequest.body());
        }
        if (oldRequest.method().equals("POST")) {
            RequestBody postRequestBody = new PostRequestBody.Builder()
                    .request(oldRequest)
                    .addParams("token", GlobalConfig.getToken())
                    .build();

            newRequest = oldRequest
                    .newBuilder()
//                    .method("POST", postRequestBody)
                    .post(postRequestBody)
                    .build();
        }

//        MyLog.d(TAG, "new request:" + newRequest.url());

        return chain.proceed(newRequest);
    }


}
