package myTest;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 当有客户端绑定到服务端的时候触发本类的channelConnected方法
 * Created by admin on 2016/6/8.
 */
public class HelloServerHandler extends SimpleChannelHandler {

    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e){

        System.out.println("test netty by wangyang!");
    }

}
