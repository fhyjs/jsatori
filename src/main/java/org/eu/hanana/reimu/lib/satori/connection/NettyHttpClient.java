package org.eu.hanana.reimu.lib.satori.connection;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;
import reactor.netty.resources.ConnectionProvider;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NettyHttpClient implements Disposable {
    private static final Logger log = LogManager.getLogger(NettyHttpClient.class);
    public final HttpClient client;
    public final EventLoopGroup group;
    private boolean running;
    public final ConnectionProvider httpConnectionProvider =
            ConnectionProvider.builder("nhc").build();

    public NettyHttpClient() {
        running = true;
        group = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        client = HttpClient.create()
                .runOn(group)
                .compress(true)  // 启用压缩
                .followRedirect(true)  // 自动跟随重定向
                .keepAlive(true);  // 启用长连接
    }

    /**
     * 异步 GET 请求，带重试机制
     *
     * @param uri 请求的 URL
     * @return 请求的响应体内容
     */
    public Mono<Tuple2<HttpClientResponse, ByteBufMono>> get(String uri) {
        return client.get()
                .uri(uri)
                .responseSingle((httpClientResponse, byteBufMono) -> Mono.just(Tuples.of(httpClientResponse,byteBufMono)))
                .doOnError(throwable -> log.error("GET request failed: " + uri, throwable))
                .retryWhen(
                        reactor.util.retry.Retry.backoff(3, Duration.ofSeconds(2))
                                .doBeforeRetry(retrySignal -> log.warn("Retrying GET request: " + uri))
                );
    }

    /**
     * 异步 POST 请求，带重试机制
     *
     * @param uri  请求的 URL
     * @param body 请求的内容
     * @return 请求的响应体内容
     */
    public Mono<Tuple2<HttpClientResponse, ByteBufMono>> post(String uri,Map<String,String> header, String body) {
        return client.headers(entries -> {
                    if (header == null) return;
                    HashMap<String, String> stringStringHashMap = new HashMap<>(header);
                    // 过滤掉 null 键或 null 值的条目
                    stringStringHashMap.forEach((key, value) -> {
                        if (key != null && value != null) {
                            entries.add(key, value);
                        } else {
                            log.warn("Skipping null key or value in header: key = {}, value = {}", key, value);
                        }
                    });
                })
                .post()
                .uri(uri)
                .send(ByteBufFlux.fromString(Mono.just(body)))
                .responseSingle((httpClientResponse, byteBufMono) -> Mono.just(Tuples.of(httpClientResponse,byteBufMono)))
                .doOnNext(response -> log.debug("POST response received: {} - {}", response.getT1().status().code(), uri))
                .doOnError(throwable -> log.error("POST request failed: " + uri, throwable))
                .retryWhen(
                        reactor.util.retry.Retry.backoff(3, Duration.ofSeconds(2))
                                .doBeforeRetry(retrySignal -> log.warn("Retrying POST request: " + uri))
                );
    }
    public static void main(String[] args) {
        NettyHttpClient httpClient = new NettyHttpClient();

        // 示例：发起 GET 请求
        httpClient.get("https://httpbin.org/get")
                .doOnNext(response -> System.out.println("GET Response: " + response))
                .block();

        // 示例：发起 POST 请求
        httpClient.post("https://httpbin.org/post", null,"{\"name\": \"Netty\"}")
                .doOnNext(response -> System.out.println("POST Response: " + response))
                .block();

        httpClient.dispose();
    }

    @Override
    public boolean isDisposed() {
        return !this.running;
    }

    @Override
    public void dispose() {
        running = false;
        this.httpConnectionProvider.disposeLater().block();
        group.shutdownGracefully();
    }
}
