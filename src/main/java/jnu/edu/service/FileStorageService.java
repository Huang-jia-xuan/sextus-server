package jnu.edu.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;
@Setter
@Getter
//@Slf4j
public class FileStorageService {

    private final OSS ossClient;
    private final String bucketName;
    private final String endpoint;
    public FileStorageService(String endpoint, OSS ossClient, String bucketName) {
        this.ossClient = ossClient;
        this.bucketName = bucketName;
        this.endpoint = endpoint;
    }
    public FileStorageService(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        this.bucketName = bucketName;
        this.endpoint = endpoint;
    }

    // 文件上传
    public String uploadFile(String fileKey, File file) {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileKey, file);
            ossClient.putObject(putObjectRequest);
            return ossClient.generatePresignedUrl(bucketName, fileKey, null).toString();  // 返回文件的访问URL
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // 上传失败，返回null
        }
    }

    // 文件下载
    public InputStream downloadFile(String fileKey) {
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileKey);
            return ossClient.getObject(getObjectRequest).getObjectContent();  // 返回文件流
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // 下载失败，返回null
        }
    }

    // 文件删除
    public boolean deleteFile(String fileKey) {
        try {
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
            deleteObjectsRequest.setKeys(List.of(fileKey));  // 删除指定文件
//            DeleteObjectsResponse response = ossClient.deleteObjects(deleteObjectsRequest);
            DeleteObjectsResult response = ossClient.deleteObjects(deleteObjectsRequest);
//            return response.fileKey);  // 返回是否成功删除
            return response.getDeletedObjects().contains(fileKey);
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // 删除失败，返回false
        }
    }

    // 关闭OSS客户端
    public void shutdown() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}
