package networking;

import game.GameHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class LoginHandler extends ChannelInboundHandlerAdapter {
	
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("Connection established");
    	ctx.write(Unpooled.copiedBuffer(GameHandler.me.getUsername()+"\n", CharsetUtil.UTF_8));
    	ctx.write(Unpooled.copiedBuffer("password\n", CharsetUtil.UTF_8));
    	ctx.flush();
	}
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf b = (ByteBuf) msg;
    	if(b.getInt(0) == 1){
    		System.out.println("Successfully logged in");
    		System.out.println(ctx.pipeline().names().get(0));
    		ctx.pipeline().remove("LoginHandler#0");
    		//ctx.pipeline().removeLast(); //Remove the login handler
    		ctx.pipeline().addLast("client handler",new ClientHandler());
    		GameHandler.connection = ctx.channel();
    	}else if(b.getInt(0) == 0){
    		System.out.println("Failed login");
    	}
    	
    	b.release();
    }
}
