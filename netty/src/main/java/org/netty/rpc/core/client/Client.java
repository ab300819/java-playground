package org.netty.rpc.core.client;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.netty.rpc.core.common.RpcDecoder;
import org.netty.rpc.core.common.RpcEncoder;
import org.netty.rpc.core.common.RpcInvocation;
import org.netty.rpc.core.common.RpcProtocol;
import org.netty.rpc.core.common.cache.CommonServerCache;
import org.netty.rpc.core.common.config.ClientConfig;
import org.netty.rpc.core.proxy.JDKProxyFactory;
import org.netty.rpc.interfaces.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.common.util.JsonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/22 23:27
 */
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    protected static final Executor executor = Executors.newFixedThreadPool(8);

    private final ClientConfig clientConfig;

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public Client(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public RpcReference startClient() {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(clientConfig.getAddress(), clientConfig.getPort());
        executor.execute(new AsyncSendJob(channelFuture));
        return new RpcReference(new JDKProxyFactory());
    }


    static class AsyncSendJob implements Runnable {

        private ChannelFuture channelFuture;

        public AsyncSendJob(ChannelFuture channelFuture) {
            this.channelFuture = channelFuture;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    RpcInvocation data = CommonServerCache.SEND_QUEUE.take();
                    String json = JsonUtil.toString(data);
                    RpcProtocol rpcProtocol = new RpcProtocol(json.getBytes());
                    channelFuture.channel().writeAndFlush(rpcProtocol);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig("localhost", 9090);
        Client client = new Client(config);
        RpcReference rpcReference = client.startClient();
        DataService dataService = rpcReference.get(DataService.class);
        for (int i = 0; i < 100; i++) {
            String result = dataService.sendData("test");
            log.info(result);
        }

    }
}