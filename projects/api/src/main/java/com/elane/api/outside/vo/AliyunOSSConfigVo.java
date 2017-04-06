package com.elane.api.outside.vo;
/**
 *
 * @description
 *          阿里云OSS上传配置vo类
 * @author carl
 *
 * @version 创建时间：2016年12月8日 下午2:30:49
 * 
 **/
public class AliyunOSSConfigVo {
    private String accessId;//阿里云ACCESS_ID
    private String accessKey;//阿里云ACCESS_KEY
    private String ossEndpoint;//阿里云OSS_ENDPOINT  Url
    private String bucketName;//阿里云BUCKET_NAME  OSS
    
    public AliyunOSSConfigVo(String accessId,String accessKey,String ossEndpoint,String bucketName){
        this.accessId=accessId;
        this.accessKey=accessKey;
        this.ossEndpoint=ossEndpoint;
        this.bucketName=bucketName;
    }
    
    public String getAccessId() {
        return accessId;
    }
    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }
    public String getAccessKey() {
        return accessKey;
    }
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
    public String getOssEndpoint() {
        return ossEndpoint;
    }
    public void setOssEndpoint(String ossEndpoint) {
        this.ossEndpoint = ossEndpoint;
    }
    public String getBucketName() {
        return bucketName;
    }
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
}
