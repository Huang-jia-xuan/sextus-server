package jnu.edu.service;

//import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//@Slf4j
@Service
public class IntegrityCheckService {

    // 计算文件的SHA-256哈希值
    public String calculateFileHash(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] byteArray = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }

    // 验证文件的哈希值是否与数据库中存储的哈希值一致
    public boolean verifyFileIntegrity(String expectedHash, File file) throws IOException, NoSuchAlgorithmException {
        String calculatedHash = calculateFileHash(file);
        return expectedHash.equals(calculatedHash);
    }
}
