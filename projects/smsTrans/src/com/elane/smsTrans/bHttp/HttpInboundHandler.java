package com.elane.smsTrans.bHttp;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.elane.smsTrans.psms.entity.Request;
import com.elane.smsTrans.util.CheckPhone;
import com.elane.smsTrans.util.DataPool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static Logger log = Logger.getLogger(HttpInboundHandler.class);
	

	
	@Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
			super.channelActive(ctx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 

    // Please keep in mind that this method will be renamed to messageReceived(ChannelHandlerContext, I) in 5.0.
    @Override
    public void channelRead0 (ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        // Check for invalid http data:
        if(msg.getDecoderResult() != DecoderResult.SUCCESS ) {
            ctx.close();
            return;
        }



        
/*
        System.out.println("Recieved request!");
        System.out.println("HTTP Method: " + msg.getMethod());
        System.out.println("HTTP Version: " + msg.getProtocolVersion());
        System.out.println("URI: " + msg.getUri());
        System.out.println("Headers: " + msg.headers());
        System.out.println("Trailing headers: " + msg.trailingHeaders());
         
        ByteBuf data = msg.content();
      
        System.out.println("POST/PUT length: " + data.readableBytes());
        System.out.println("POST/PUT as string: ");
        System.out.println("-- DATA --");
        System.out.println(data.toString(StandardCharsets.UTF_8));
        System.out.println("-- DATA END --");
*/       
        ByteBuf responseBytes = ctx.alloc().buffer();  
        
        RequestParser rp=new RequestParser(msg);
        Map<String, String> requestParams=rp.parse();
        String phone=requestParams.get("phone");
//        System.out.println("-- DATA --="+phone);
        String content=requestParams.get("content");
//        System.out.println("-- DATA --="+content);	
        
        String user=requestParams.get("user");
        String m=requestParams.get("m");
        
        
        
        log.debug("find data:phone="+phone+",content="+content);    
        
        String msgWrite="0";
        
/*
        if(msg.uri().startsWith("/sms")
        		&&StringUtils.isNoneBlank(phone)
        		&&StringUtils.isNoneBlank(content)
        		&&CheckPhone.simpleMobileCheck(phone)){
        	
        	DataPool.datas.add(new Request(phone,content));

	        responseBytes.writeBytes("0".getBytes());
        }else{
	        responseBytes.writeBytes("99".getBytes());       	
        }
*/
        if(msg.uri().startsWith("/sms")
        		&&StringUtils.isNoneBlank(phone)
        		&&StringUtils.isNoneBlank(content)
        		&&StringUtils.isNoneBlank(user)
        		&&StringUtils.isNoneBlank(m)
        		&&CheckPhone.simpleMobileCheck(phone)
        		){      
        	String pass=DataPool.authMap.get(user);
        	if(pass==null){
        		msgWrite="11";//用户不存在        		
        	}else{
	        	if(!m.equals(DigestUtils.md5Hex(pass+content))){
	        		msgWrite="14";//摘要不正确
	        	}else{
	        		DataPool.datas.add(new Request(phone,content));
	        	}
        	}
        	
        }else{
        	msgWrite="99";//参数不合法
        }
        
        
        responseBytes.writeBytes(msgWrite.getBytes()); 
        
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, responseBytes);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }
    
    
    public static void main(String[] args){
    	System.out.println(DigestUtils.md5Hex("a000111月渤海海冰情况正常-测301"));
    }
}