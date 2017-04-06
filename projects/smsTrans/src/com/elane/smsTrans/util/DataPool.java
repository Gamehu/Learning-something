package com.elane.smsTrans.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.elane.smsTrans.psms.entity.Request;

public class DataPool {

	public final static LinkedBlockingQueue<Request> datas=new LinkedBlockingQueue<Request>();
	
	public final static Map<String,String> authMap=new HashMap<String,String>();

}
