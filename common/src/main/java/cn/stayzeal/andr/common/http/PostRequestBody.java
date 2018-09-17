package cn.stayzeal.andr.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.stayzeal.andr.common.utils.MyLog;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * POST 请求参数处理类，可以给接口统一增加参数
 */

public class PostRequestBody {

    private static final String TAG = "PostRequestBody";
    private final List<String> names = new ArrayList<>();
    private final List<String> values = new ArrayList<>();


    private PostRequestBody() {

    }

    private void addParam(String name, String value) {
        names.add(name);
        values.add(value);
    }

    /**
     * no used
     *
     * @return
     */
    private String parsePrams() {
        int count = names.size();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
            stringBuffer.append("&" + names.get(i) + "=" + values.get(i));
        }

        return stringBuffer.toString();
    }


    private RequestBody body(Request request) {
        if (request == null || request.body() == null) {
            return null;
        }

        RequestBody requestBody = request.body();
        //1.body to string
        Buffer buffer = new Buffer();
        StringBuffer params = new StringBuffer();
        try {
            requestBody.writeTo(buffer);
            params.append(buffer.readUtf8());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.add new params
        int count = names.size();
        for (int i = 0; i < count; i++) {
            params.append("&" + names.get(i) + "=" + values.get(i));
        }
        MyLog.d(TAG, request.method() + "->" + request.url()+"&" + params.toString());
        //MediaType.parse("application/x-www-form-urlencoded") 同FormBody.
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), params.toString());


    }


    public static final class Builder {

        private PostRequestBody postRequestBody;
        private Request request;

        public Builder() {
            postRequestBody = new PostRequestBody();
        }

        public Builder request(Request request) {
            this.request = request;
            return this;
        }

        public Builder addParams(String name, String value) {
            postRequestBody.addParam(name, value);
            return this;
        }


        public RequestBody build() {
            return postRequestBody.body(request);
        }
    }
}
