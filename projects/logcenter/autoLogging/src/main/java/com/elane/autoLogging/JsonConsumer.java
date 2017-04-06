package com.elane.autoLogging;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.PropertyConfigurator;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class JsonConsumer {

	private static Logger info = LoggerFactory.getLogger("info");     
	private static Logger error = LoggerFactory.getLogger("error");     
    
    public static void main(String[] args){
    	
    	
    	PropertyConfigurator.configure(System.getenv("AUTOLOGGING_PATH") + "/conf/log4j.properties");
    	String conf=System.getenv("AUTOLOGGING_PATH") + "/conf/config.properties";
    	info.info("Loading config:"+conf);
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream (new FileInputStream(conf));  
            pps.load(in);   
        }catch (IOException e) {
            e.printStackTrace();
        }
        
		String kafkaBSServer=pps.getProperty("bootstrap.servers");
		String kafkaTopic=pps.getProperty("kafka.topic");  
		
		String mongoIP=pps.getProperty("mongodb.ip");
		String mongoPort=pps.getProperty("mongodb.port");
		String mongoDB=pps.getProperty("mongodb.db");
		//partition数
		int partitionCount=Integer.parseInt(pps.getProperty("kafka.topic.partitions"));
		//线程数
		int threadCount=Integer.parseInt(pps.getProperty("group.thread.count"));
		partitionCount = threadCount;
		
		Config.kafkaBSServer=kafkaBSServer;
		Config.kafkaTopic=kafkaTopic;
		Config.mongoIP = mongoIP;
		Config.mongoPort = mongoPort;
		Config.mongoDB = mongoDB;
		Config.partitionCount = partitionCount;
    	
		MongoClientURI uri = new MongoClientURI("mongodb://"+mongoIP+":"+mongoPort+"/",
				MongoClientOptions.builder().cursorFinalizerEnabled(false));
		
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(mongoDB);
		MongoCollection<Document> collection = db.getCollection("loggingAll");
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Exit!");
			}
		}));
		
		
        
        ArrayList<KafkaConsumer<String ,String>> pConsumers =new ArrayList<KafkaConsumer<String ,String>>();
		for(int i=0;i<partitionCount;i++){
			//定义多个group，每个group内包含一个consumer，每个consimer对应topic下面的一个partition
			//配置参数partitionCount的意义为partition的个数，否则出现连接不上的partition出现数据处理遗漏
//			KafkaConsumer<String ,String> pConsumer = new KafkaConsumer<String ,String>(getConsumerConfig("maxgroup"));
//			TopicPartition partition=new TopicPartition("maxwell", i);
			
			/*//以下代码测试consumer一对一的连接partition时，没有被连接的partition写入数据后，不能被正常处理数据
			TopicPartition partition = null;
			if(i == 2){
				partition=new TopicPartition("maxwell", 13);
			}else{
				partition=new TopicPartition("maxwell", i);
			}*/
//			pConsumer.assign(Arrays.asList(partition));
			
			//定义一个group，group订阅topic，开启多个线程去分配toppic下面的partition
			//配置参数partitionCount的意义为开启的线程数为多少
			KafkaConsumer<String, String> pConsumer = new KafkaConsumer<String ,String>(getConsumerConfig("maxgroup"));
			pConsumer.subscribe(Arrays.asList(kafkaTopic));
			
			pConsumers.add(pConsumer);
		}
		
		
		int i=0;
		for(final KafkaConsumer<String ,String> c:pConsumers){	
			
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
			        while(true){
			            ConsumerRecords<String, String> records = c.poll(100);
			            for(ConsumerRecord<String, String> record : records){
			            	info.info("fetched from partition " + record.partition() + ", offset: " 
			                		+ record.offset() + ", message: " + record.value().toString()+", topic:"+record.topic());                
			                String value=record.value().toString();
			                JSONObject jobj = null;
			                
			                try {
				                jobj=JSON.parseObject(value);  
				                Object data=jobj.get("data");
				                
				                if(data==null){
				                	continue;
				                }
	
				                JSONObject jobjData=JSON.parseObject(data.toString());  
				                
				                Object appid=jobjData.get("app_id");
				                Object companyid=jobjData.get("company_id");
				                //	优先选择update_uid
				                Object userid=jobjData.get("update_uid") == null ? jobjData.get("create_uid") : jobjData.get("update_uid");
				                Object xtransactionid=jobjData.get("xtransaction_id");
				                //	MYSQL数据主键如何选择？主键不一定是ID，不处理主键查询
				                
				                jobj.put("app_id", appid==null?"":appid.toString());
				                jobj.put("company_id", companyid==null?"":companyid.toString());
				                jobj.put("user_id", userid==null?" ":userid.toString());
				                jobj.put("xtransaction_id", xtransactionid==null?" ":xtransactionid.toString());
			                } catch (Exception e) {
			                	error.error("fetched from partition " + record.partition() + ", offset: " 
				                		+ record.offset() + ", message: " + record.value().toString()+", topic:"+record.topic());
			                	error.error("解析data异常：" + value);
			                	continue;
							}
			                
			                try {
				                Document doc = Document.parse( jobj.toJSONString() );
				                collection.insertOne(doc);
			                } catch (Exception e) {
			                	error.error("data写入mongoDB异常：" + jobj);
			                	continue;
							}
			            }
			        }
				}
			});
			t.setName("ConsumeThread--"+String.format("%02d",(i++))+"--");
			t.start();
			
		}
		
    }
    
    private static Properties getConsumerConfig(String groupid){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.162:9092,192.168.1.163:9092,192.168.1.164:9092");
        props.put("group.id", groupid);      

        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "60000");
        props.put("max.poll.records", "10");
        props.put("fetch.max.wait.ms", "60000");
        
        props.put("request.timeout.ms","100000"); 
        
	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    	
    }
}
