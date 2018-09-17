package cn.stayzeal.andr.common.http;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.schedulers.Schedulers;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static volatile Retrofit sInstance;

    private RetrofitUtil() {
    }

    /*超时设置*/
    private static final int DEFAULT_TIMEOUT = 10;

    public static Retrofit getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitUtil.class) {
                OkHttpClient.Builder httpClient = getOkHttp();
                addInterceptor(httpClient);
                sInstance = new Retrofit.Builder()
                        .baseUrl(GlobalConfig.baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(httpClient.build())
                        .build();
            }
        }
        return sInstance;
    }

    public static Retrofit getHttpsInstance() {
        if (sInstance == null) {
            synchronized (RetrofitUtil.class) {

                OkHttpClient.Builder httpClient = getOkHttp();
                addInterceptor(getOkHttp());
                //add https
                onHttpCertficates(httpClient, true);

                sInstance = new Retrofit.Builder()
                        .baseUrl(GlobalConfig.baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(httpClient.build())
                        .build();
            }
        }
        return sInstance;
    }

    public static OkHttpClient.Builder okHttpClient;

    public static OkHttpClient.Builder getOkHttp() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder();
            okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }

        return okHttpClient;
    }


    private static void addInterceptor(OkHttpClient.Builder okHttpClient) {
        okHttpClient.addInterceptor(new TokenInterceptor());
        addLog(okHttpClient);
    }

    private static void addLog(OkHttpClient.Builder okHttpClient){
        if (!GlobalConfig.RELEASE_MODE) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            // add your other interceptors …
            // add logging as last interceptor
            okHttpClient.addInterceptor(logging);  // <-- this is the import
        }
    }

    public static OkHttpClient.Builder newOkhttpClient(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        addLog(okHttpClient);
        return okHttpClient;
    }



    private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {

        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];

        return new TrustManager[]{

                new X509TrustManager() {

                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkClientTrusted(certs, authType);
                        } catch (CertificateException e) {
                            e.printStackTrace();
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkServerTrusted(certs, authType);
                        } catch (CertificateException e) {
                            e.printStackTrace();
                        }
                    }
                }
        };

    }

    protected static SSLSocketFactory getSSLSocketFactory(Context context, String keyStoreType, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }
        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            //Create a KeyStore containing our trusted CAs
            if (keyStoreType == null || keyStoreType.length() == 0) {
                keyStoreType = KeyStore.getDefaultType();
            }
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(is));
                if (is != null) {
                    is.close();
                }
            }
            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(trustManagerFactory.getTrustManagers());
            //Create an SSLContext that uses our TrustManager

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, wrappedTrustManagers, new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {

        }
        return null;
    }

    public static void onHttpCertficates(OkHttpClient.Builder builder, boolean isRelease) {
        int[] certficates;
        CertificatePinner cerPinner;
        if (isRelease) {
            certficates = new int[]{R.raw.kanlaoshi};
            cerPinner = new CertificatePinner.Builder().add("www.kanlaoshi.com", "sha256/niFlbMYoVnGXzaPuJ41tWvmb/c1kGyzi5dT0DRrAoFs=").build();
        } else {
            certficates = new int[]{R.raw.testkanlaoshi};
            cerPinner = new CertificatePinner.Builder().add("test.kanlaoshi.com", "sha256/kAJWgxGyLm3B4Gri/ujzt4KZ27EM8oFmEaPn8aSKALo=").build();
        }
        builder.sslSocketFactory(getSSLSocketFactory(MyApplication.appContext, "", certficates));
        builder.certificatePinner(cerPinner);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }
}
