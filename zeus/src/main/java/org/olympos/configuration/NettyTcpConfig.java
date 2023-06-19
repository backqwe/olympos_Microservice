package org.olympos.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class NettyTcpConfig {

    @Value("${netty.tcp.select.count:1}")
    private int selectCount;

    @Value("${netty.tcp.worker.count:4}")
    private int workerCount;

    @Value("${netty.tcp.connect_timeout_millis:10000}")
    private int connectTimeoutMillis;

    @Value("${netty.tcp.write_buffer_high_water_mark:65536}")
    private int writeBufferHighWaterMark;

    @Value("${netty.tcp.write_buffer_low_water_mark:32768}")
    private int writeBufferLowWaterMark;

    @Value("${netty.tcp.so_keepalive:false}")
    private boolean soKeepalive;

    @Value("${netty.tcp.so_reuseaddr:false}")
    private boolean soReuseaddr;

    @Value("${netty.tcp.so_linger:-1}")
    private int soLinger;

    @Value("${netty.tcp.so_backlog:128}")
    private int soBacklog;

    @Value("${netty.tcp.tcp_nodelay:true}")
    private boolean tcpNodelay;
}
