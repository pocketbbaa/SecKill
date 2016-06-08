package myTest;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
/**
 * Created by admin on 2016/6/8.
 */
public class HelloServer {

    public static void main(String[] args) {

        //启动server服务
        NioServerSocketChannelFactory nioServerSocketChannelFactory =
                new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                                                  Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(nioServerSocketChannelFactory);

        //设置一个处理客户端消息和各种消息事件的类(ServerHandler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory(){
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new StringDecoder(), new StringEncoder(), new ServerHandler());
            }
        });

        // 开放8000端口供客户端访问。
        Channel bind = bootstrap.bind(new InetSocketAddress(8000));
        System.out.println("Server已经启动，监听端口: " + bind.getLocalAddress() + "， 等待客户端注册。。。");
    }

    //当有客户端绑定到服务端的时候触发本类的messageReceived方法
    private static class ServerHandler extends SimpleChannelHandler {

        //当有客户端绑定到服务端的时候触发，打印"我是服务端"
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("我是服务端");
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            if(e.getMessage() instanceof String){
                String message = (String) e.getMessage();
                System.out.println("客户端发来的消息："+message);
                e.getChannel().write(e.getMessage());
            }
            super.messageReceived(ctx, e);
        }
    }
}
