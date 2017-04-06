package com.elane.api.outside.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.elane.api.outside.vo.AliyunOSSConfigVo;

/**
 *
 * @description 
 *              此类为外部接口阿里云OSS文件上传下载服务类
 * @author carl
 *
 * @version 创建时间：2016年12月6日 上午11:16:22
 * 
 **/
public class AliyunOSSService {
    private static OSSClient client=null;
    /**
     * 阿里云ACCESS_ID
     */
    private static String ACCESS_ID = "";
    /**
     * 阿里云ACCESS_KEY
     */
    private  static String ACCESS_KEY = "";
    /**
     * 阿里云OSS_ENDPOINT  Url
     */
    public static String OSS_ENDPOINT = "";
     
    /**
     * 阿里云BUCKET_NAME  OSS
     */
    public static String BUCKET_NAME = "";
     
     
    /**
     * 初始化OSS客户端
     */
    public static void init(AliyunOSSConfigVo config){
        ACCESS_ID=config.getAccessId();
        ACCESS_KEY=config.getAccessKey();
        OSS_ENDPOINT=config.getOssEndpoint();
        BUCKET_NAME=config.getBucketName();
        client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
    }
     
    /**
     * 创建Bucket
     *
     * @param bucketName  BUCKET名
     * @throws OSSException
     * @throws ClientException
     */
    public static void ensureBucket(String bucketName)throws OSSException, ClientException{
        try{
            client.createBucket(bucketName);
        }catch(ServiceException e){
            if(!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())){
                throw e;
            }
        }
    }
     
    /**
     * 删除一个Bucket和其中的Objects
     *
     * @param bucketName  Bucket名
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteBucket(String bucketName)throws OSSException, ClientException{
        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();
        for(int i = 0; i < listDeletes.size(); i++){
            String objectName = listDeletes.get(i).getKey();
            System.out.println("objectName = " + objectName);
            //如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }
     
    /**
     * 把Bucket设置成所有人可读
     *
     * @param bucketName  Bucket名
     * @throws OSSException
     * @throws ClientException
     */
    public static void setBucketPublicReadable(String bucketName)throws OSSException, ClientException{
        //设置bucket的访问权限， public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }
     
    /**
     * 上传文件
     *
     * @param bucketName  Bucket名
     * @param Objectkey  上传到OSS起的名
     * @param file         文件对象
     * @throws OSSException
     * @throws ClientException
     * @throws IOException 
     */
    public static void uploadFile(String bucketName, String Objectkey,MultipartFile file)
            throws OSSException, ClientException, IOException{
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.getSize());
        objectMeta.setContentType(file.getContentType());
        client.putObject(bucketName, Objectkey, file.getInputStream(), objectMeta);
    }
     
    /**
     *  下载文件
     *
     * @param client  OSSClient对象
     * @param bucketName  Bucket名
     * @param Objectkey  上传到OSS起的名
     * @return 
     * @throws OSSException
     * @throws ClientException
     */
    public static OSSObject downloadFile(String bucketName, String Objectkey)
            throws OSSException, ClientException {
        return client.getObject(new GetObjectRequest(bucketName, Objectkey));
    }
}
