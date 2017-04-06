package com.elane.smsTrans.psms.process;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;

import org.apache.commons.lang3.ArrayUtils;

import com.elane.smsTrans.psms.entity.Request;
import com.elane.smsTrans.util.DataTools;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SMSSendingRequestEncoder extends MessageToByteEncoder<Request> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {

		byte[] datas = toBytes(msg);
		out.writeBytes(datas);
	}

	static byte[] toBytes(Request msg) {

		byte[] fullLength = new byte[4];
	
		byte[] r= null;
		try {
			r= merge(
					DataTools.short2Bytes(Request.getType(),ByteOrder.LITTLE_ENDIAN),
					toBytesWithLength(Request.getGroupid()),
					DataTools.short2Bytes(Request.getInternaltype(),ByteOrder.LITTLE_ENDIAN),
					toBytesWithLength(msg.getPhone()),
					toBytesWithLength(msg.getContent())
					);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fullLength=DataTools.int2Bytes(r.length-2,ByteOrder.LITTLE_ENDIAN);

		return merge(fullLength,r);
	}

	static byte[] toBytesWithLength(String s) throws UnsupportedEncodingException {
		byte[] tmp = s.getBytes("GBK");
		byte[] l = DataTools.short2Bytes((short)tmp.length,ByteOrder.LITTLE_ENDIAN);
		return merge(l,tmp);
	}

	static byte[] merge(byte[] a1, byte[] a2) {
		byte[] a3 = new byte[a1.length + a2.length];
		System.arraycopy(a1, 0, a3, 0, a1.length);
		System.arraycopy(a2, 0, a3, a1.length, a2.length);
		return a3;
	}
	
	
	static byte[] merge(byte[] ...a) {		
		byte[] d = new byte[0];			
		for(byte[] ai:a)
			d=ArrayUtils.addAll(d, ai);
		return d;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		Request r=new Request();
		r.setContent("test¹þ");
		r.setPhone("15811382108");
		byte[] bs=toBytes(r);	
		
		for(byte b:bs)
			System.out.print(b);
		System.out.println();	
		
		
		
		System.out.println("------1--------");	
		
//		byte[] tmp = "test¹þ".getBytes("UTF-8");
//		byte[] tmp = new String("test¹þ".getBytes("UTF-8")).getBytes("GB2312");
		byte[] tmp = "KuaiCang".getBytes("GBK");
		for(byte b:tmp){
			System.out.print(b);System.out.print("-");}
		
		System.out.println();	
		System.out.println("------2--------");	
		System.out.println(tmp.length);	
		for(byte b:DataTools.short2Bytes((short)tmp.length,ByteOrder.LITTLE_ENDIAN)){
			System.out.print(b);System.out.print("-");
		}
		System.out.println();
		
		System.out.println("-------4-------");	
		for(byte b:DataTools.short2Bytes((short)80,ByteOrder.LITTLE_ENDIAN)){
			System.out.print(b);System.out.print("-");
		}
	}
}

