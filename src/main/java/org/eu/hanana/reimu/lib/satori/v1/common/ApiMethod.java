package org.eu.hanana.reimu.lib.satori.v1.common;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("SameParameterValue")
@RequiredArgsConstructor
public class ApiMethod<T extends IApiData> {
    private final List<String> location = new ArrayList<>();
    public final String method;
    public final Class<T> dataClass;

    public static final String POST = "POST";
    public static final String GET = "GET";


    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (String s : location) {
            sb.append(s).append(".");
        }
        if (sb.toString().endsWith("."))
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ApiMethod<?> apiMethod){
            return apiMethod.location.equals(location)&&apiMethod.method.equals(method);
        }
        return false;
    }
    public static <T extends IApiData>ApiMethod<T> parse(String path,Class<T> dataClass){
        return parse(path,POST,dataClass);
    }

    private static <T extends IApiData> ApiMethod<T> parse(String path, String method, Class<T> dataClass) {
        String[] split = path.split("\\.");
        ApiMethod<T> apiMethod = new ApiMethod<>(method,dataClass);
        apiMethod.location.addAll(Arrays.asList(split));
        return apiMethod;
    }

}
