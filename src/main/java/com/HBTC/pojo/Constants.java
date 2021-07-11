package com.HBTC.pojo;

public class Constants {

    // FIXME: change to a valid key and secret for test
    public final static String ACCESS_KEY = "6nHtqiROQHefY4luypSlpaSPUx7rLl74hpHigdKpyCOHlJgKfGbJXUD6uUgrNMlN";
    public final static String SECRET_KEY = "raF7i1i3NsuETq4H1dwZ1beDbp3ugP9YFNVbmsmT7SXVf5GuXD9V75zH2Eyn0bPF";

    // FIXME: please change to your valid api domain for test, for example: bhop.cloud
    public static final String BASE_DOMAIN = "hbtc.co";

    // REST api url format: https://api.BASE_DOMAIN
    // for example: https://api.bhop.cloud
    public static final String API_BASE_URL = "https://api." + BASE_DOMAIN;

    // Websocket base api url format: wss://wsapi.BASE_DOMAIN/openapi/quote/ws/v1
    // for example: wss://wsapi.bhop.cloud/openapi/quote/ws/v1
    public static final String WS_API_BASE_URL =  "wss://wsapi." + BASE_DOMAIN +  "/openapi/quote/ws/v1";

    // Websocket user api url format: wss://wsapi.BASE_DOMAIN/openapi/ws
    // for example: wss://wsapi.bhop.cloud/openapi/ws
    public static final String WS_API_USER_URL = "wss://wsapi." + BASE_DOMAIN + "/openapi/ws";
}
