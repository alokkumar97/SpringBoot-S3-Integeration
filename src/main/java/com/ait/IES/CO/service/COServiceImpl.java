package com.ait.IES.CO.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Service
public class COServiceImpl {

	@Value("${application.bucket.name}")
	private String bucket;
	
	@Autowired
	private AmazonS3 s3client;

	//Upload File to S3
	public String uploadFile(MultipartFile file) throws IOException{
		File fileObj = convertMultipartFile(file);
		String fileName = System.currentTimeMillis() +"_"+file.getOriginalFilename();
		s3client.putObject(new PutObjectRequest(bucket, fileName, fileObj));
		fileObj.delete();
		return "File Uploaded Successfully:: "+fileName;
	}
	
	//Download File from S3
	public byte[] downloadFile(String fileName) throws IOException {		
		S3Object s3Object = s3client.getObject(bucket, fileName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		byte[] content = IOUtils.toByteArray(inputStream);
		return content;		
	}
	
	//Delete File from S3
	public String deleteFile(String fileName) {
		s3client.deleteObject(bucket, fileName);
		return fileName+" : deleted successfully .";
	}	
	
	//Convert MultiPart File to file object
	@SuppressWarnings("unused")
	private File convertMultipartFile(MultipartFile file) throws IOException{
		File convertFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertFile);
		fos.write(file.getBytes());
		fos.close();
		return convertFile;
	}
	
	
	
	
	
	
	
	
}
