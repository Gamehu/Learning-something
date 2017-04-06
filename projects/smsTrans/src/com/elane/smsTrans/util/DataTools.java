package com.elane.smsTrans.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataTools {

    public static short get2Bytes(byte[] from, int fromIndex){  
        int high=from[fromIndex] & 0xff;  
        int low=from[fromIndex+1] & 0xff;  
        return (short)(high<<8+low);  
    }  
    /* 
    public static byte[] short2Bytes(short val){ 
        byte[] res=new byte[2]; 
        res[0]=(byte)(val>>8 & 0xff); 
        res[1]=(byte)(val & 0xff); 
        return res; 
    } 
     
    public static byte[] int2Bytes(int val){ 
        byte[] res = new byte[4]; 
 
        res[0] = (byte) (val >> 24); 
        res[1] = (byte) (val >> 16); 
        res[2] = (byte) (val >> 8); 
        res[3] = (byte) (val); 
 
        return res; 
    } 
    */  
  
    public static byte[] short2Bytes(short x,ByteOrder byteOrder) {  
        ByteBuffer buffer = ByteBuffer.allocate(2);  
        buffer.order(byteOrder);  
        buffer.putShort(x);  
        return buffer.array();  
    }  
    public static byte[] short2Bytes(short x) {  
        ByteBuffer buffer = ByteBuffer.allocate(2);  
        buffer.order(ByteOrder.BIG_ENDIAN);  
        buffer.putShort(x);  
        return buffer.array();  
    }  
    public static byte[] int2Bytes(int x,ByteOrder byteOrder) {  
        ByteBuffer buffer = ByteBuffer.allocate(4);  
        buffer.order(byteOrder);  
        buffer.putInt(x);  
        return buffer.array();  
    }  
    public static byte[] long2Bytes(long x,ByteOrder byteOrder) {  
        ByteBuffer buffer = ByteBuffer.allocate(8);  
        buffer.order(byteOrder);  
        buffer.putLong(x);  
        return buffer.array();  
    }  
    public static short bytes2Short(byte[] src,ByteOrder byteOrder){  
        ByteBuffer buffer = ByteBuffer.wrap(src);  
        buffer.order(byteOrder);  
        return buffer.getShort();  
    }  
    public static int bytes2Int(byte[] src,ByteOrder byteOrder){  
        ByteBuffer buffer = ByteBuffer.wrap(src);  
        buffer.order(byteOrder);  
        return buffer.getInt();  
    }  

    public static long bytes2Long(byte[] src,ByteOrder byteOrder){  
        ByteBuffer buffer = ByteBuffer.wrap(src);  
        buffer.order(byteOrder);  
        return buffer.getLong();  
    }  
	public static void main(String[] args){    
		byte bs[]=short2Bytes((short)102,ByteOrder.LITTLE_ENDIAN);
		for(byte b:bs)
			System.out.print(b);
		System.out.println();
		
		System.out.println(bytes2Short(bs,ByteOrder.LITTLE_ENDIAN));
	}      
}
