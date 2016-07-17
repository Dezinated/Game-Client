package networking;

import game.GameHandler;
import game.Player;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	public void handlerAdded(ChannelHandlerContext ctx) {
		//Add the channel to the group
		System.out.println("CH: Client Handler Active");
	}

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	ByteBuf b = (ByteBuf) msg;
		short packetID = b.readShort();
		switch(packetID){
		case 0:
			//uPDATE pckt
			float x = b.readFloat();
			float y = b.readFloat();
			char direction = b.readChar();
			//long time = b.readLong();
			String[] info = b.toString(CharsetUtil.UTF_8).split("\n");
			//System.out.println(info[0]+ " X:"+x+" Y:"+y);
			//System.out.println("Spawning player "+newPlayer.getUsername()+" at X: "+x+" Y:"+y);
			//newPlayer.setPosition(x, y);
			GameHandler.getPlayer(info[0]).setPosition(x, y);
			GameHandler.getPlayer(info[0]).setDirection(direction);
			break;
		}
		
		b.release();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
       ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}