package org.eu.hanana.reimu.lib.satori.connection;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;
import reactor.netty.resources.ConnectionProvider;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class NettyHttpClient implements Disposable {
    private static final Logger log = LogManager.getLogger(NettyHttpClient.class);
    public final HttpClient client;
    private final EventLoopGroup group;
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
    public Mono<HttpClientResponse> get(String uri) {
        return client.get()
                .uri(uri)
                .response()
                .doOnError(throwable -> log.error("GET request failed: " + uri, throwable))
                .retryWhen(
                        reactor.util.retry.Retry.backoff(3, Duration.ofSeconds(2))
                                .doBeforeRetry(retrySignal -> log.warn("Retrying GET request: " + uri))
                );
    }

    /**
     * 异步 POST 请求，带重试机制
     *
     * @param uri 请求的 URL
     * @param body 请求的内容
     * @return 请求的响应体内容
     */
    public Mono<HttpClientResponse> post(String uri, String body) {
        return client.post()
                .uri(uri)
                .send(ByteBufFlux.fromString(Mono.just(body)))
                .response()
                .doOnNext(response -> log.info("POST response received: {} - {}", response.status().code(), uri))
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
        httpClient.post("https://httpbin.org/post", "{\"name\": \"Netty\"}")
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
