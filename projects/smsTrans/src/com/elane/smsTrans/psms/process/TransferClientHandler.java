package com.elane.smsTrans.psms.process;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.elane.smsTrans.psms.entity.Request;
import com.elane.smsTrans.util.DataPool;
import com.elane.smsTrans.util.DataTools;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;  
  
public class TransferClientHandler extends ChannelInboundHandlerAdapter {  
	private static Logger log = Logger.getLogger(TransferClientHandler.class);
	  private int UNCONNECT_NUM = 0;  

	@Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  

		ByteBuf result = (ByteBuf) msg;
        byte[] resultBytes = new byte[result.readableBytes()];    
        result.readBytes(resultBytes);    

		byte[] lengthBytes=new byte[4];
		System.arraycopy(resultBytes, 0, lengthBytes, 0, 4);
		int length=DataTools.bytes2Int(lengthBytes,ByteOrder.LITTLE_ENDIAN);

		byte[] codeBytes=new byte[2];
		System.arraycopy(resultBytes, 4, codeBytes, 0, 2);
		short code=DataTools.bytes2Short(codeBytes,ByteOrder.LITTLE_ENDIAN);
	
        log.info("Server said: length="+length+",code="+code );    
		
		result.release();
//        ctx.close();
//        ctx.disconnect();//ÿ���������ӣ������ӣ�

    }  
	
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        cause.printStackTrace();  
        ctx.close();  
    }  

    @Override    
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

/*		Request r=new Request();
		r.setContent("test����1");
		r.setPhone("15611031530");
		
        ctx.write(r);    
        ctx.flush(); 
        System.out.println("loop"); 
*/

/*    	while(true){
    		Request r=DataPool.datas.take();
    		log.debug("find data:phone="+r.getPhone()+",content="+r.getContent() );    
	        ctx.write(r);    
	        ctx.flush();
    	}
*/
    	

    		Request r=DataPool.datas.take();
    		while(true){
    			log.debug("find data:phone="+r.getPhone()+",content="+r.getContent() );    
		        ctx.write(r);    
		        ctx.flush();
		        r=DataPool.datas.poll(1, TimeUnit.SECONDS);
		        
		        if(r==null)
		        	break;
    		}
	        

    } 
    
    
    @Override  
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {  
        if (evt instanceof IdleStateEvent) {  
            if(UNCONNECT_NUM >= 23) {  
            	log.trace("connect status is disconnect.");  
                ctx.close();  
                //�˴������������ﵽ4��֮�󣬹رմ����Ӻ󣬲������������һ�ε�¼����  
                return;  
            }  
              
            IdleStateEvent e = (IdleStateEvent) evt;  
            switch (e.state()) {  
                case WRITER_IDLE:  
                    log.trace("writer_idle. and UNCONNECT_NUM=" + UNCONNECT_NUM);  
                    UNCONNECT_NUM++;  
                    break;  
                case READER_IDLE:    
                	log.trace("reader_idle. and UNCONNECT_NUM=" + UNCONNECT_NUM);  
                    UNCONNECT_NUM++;  
                    //��ȡ�������Ϣ��ʱʱ��ֱ�ӶϿ������ӣ������µ�¼���󣬽���ͨ��  
                case ALL_IDLE:  
                	log.trace("all_idle. and UNCONNECT_NUM="+ UNCONNECT_NUM);  
                    UNCONNECT_NUM++;  
                    //��ȡ�������Ϣ��ʱʱ��ֱ�ӶϿ������ӣ������µ�¼���󣬽���ͨ��  
                default:  
                    break;  
            }  
        }  
    }  

}  