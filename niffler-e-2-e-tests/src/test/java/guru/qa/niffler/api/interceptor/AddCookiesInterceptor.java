package guru.qa.niffler.api.interceptor;

import guru.qa.niffler.api.context.CookieContext;
import okhttp3.*;
import okhttp3.Headers.Builder;

import java.io.IOException;

public class AddCookiesInterceptor implements Interceptor {

    private static final String JSESSIONID = "JSESSIONID";
    private static final String XSRF_TOKEN = "XSRF-TOKEN";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        final CookieContext cookieContext = CookieContext.getInstance();
        String cookieXsrf = cookieContext.getCookie("XSRF-TOKEN");
        String jsessionId = cookieContext.getCookie("XSRF-TOKEN");

        final Builder builder = originalRequest.headers().newBuilder();
        builder.removeAll("Cookie");
        if (jsessionId != null) {
            builder.add("Cookie", JSESSIONID + jsessionId);
        }
        if (cookieXsrf != null) {
            builder.add("Cookie", XSRF_TOKEN + cookieXsrf);
        }

        final Headers headers = builder.build();

        return chain.proceed(
                originalRequest.newBuilder()
                        .method(originalRequest.method(), originalRequest.body())
                        .headers(headers)
                        .url(originalRequest.url())
                        .build()
        );
    }
}
