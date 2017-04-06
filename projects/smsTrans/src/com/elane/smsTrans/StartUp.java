package com.elane.smsTrans;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.elane.smsTrans.bHttp.HttpServer;
import com.elane.smsTrans.psms.process.TransferClient;
import com.elane.smsTrans.util.DataPool;


public class StartUp {

	private static Logger log = Logger.getLogger(StartUp.class);
    
	public static void main(String[] args) throws Exception {

		PropertyConfigurator.configure(System.getenv("SMSTRANS_PATH") + "/conf/log4j.properties");
		String conf=System.getenv("SMSTRANS_PATH") + "/conf/config.properties";
	
        Properties pps = new Properties();

        try {
            InputStream in = new BufferedInputStream (new FileInputStream(conf));  
            pps.load(in);
   
        }catch (IOException e) {
            e.printStackTrace();
        }
 
        
		int httpServiceport=Integer.parseInt(pps.getProperty("http.serviceport","8845"));         
        
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpServer server = new HttpServer();
				try {
					server.start(httpServiceport);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		
		String smsServiceIP=pps.getProperty("sms.serverip","211.154.163.237");
		int smsServiceport=Integer.parseInt(pps.getProperty("sms.serverport","8818"));
//		SMSConfig.smsServiceIP=smsServiceIP;
//		SMSConfig.smsServiceport=smsServiceport;
		
		
		String httpuser=pps.getProperty("http.user");
		String httppass=pps.getProperty("http.pass");
		
		String[] users=httpuser.split(",");
		String[] passes=httppass.split(",");
		
		for(int i=0;i<users.length;i++){
			DataPool.authMap.put(users[i], passes[i]);
			log.trace("add user:"+users[i]+","+passes[i]);
		}
		
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				TransferClient client = new TransferClient();
				try {
					while(true){
						if(DataPool.datas.peek()!=null){
							log.trace("============client.start==============");
							client.start(smsServiceIP, smsServiceport);							
						}else{
							Thread.sleep(500);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
		}).start();	
	
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				//TODO 清理挤压未发的数据
				log.info("Exit!");
			}
		})); 
		
	}

}
