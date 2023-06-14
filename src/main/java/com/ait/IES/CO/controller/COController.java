package com.ait.IES.CO.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ait.IES.CO.service.COServiceImpl;

@RestController
@RequestMapping("/file")
public class COController {

	@Autowired
	private COServiceImpl service;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadfile(@RequestParam(value = "file") MultipartFile file) throws IOException {
		return new ResponseEntity<String>(service.uploadFile(file), HttpStatus.OK);
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<ByteArrayResource> downloadfile(@PathVariable String filename) throws IOException {
		byte[] file = service.downloadFile(filename);
		ByteArrayResource resource = new ByteArrayResource(file);
		return ResponseEntity.ok()
											.contentLength(file.length)
											.header("Content-type", "application/octet-stream")
											.header("Content-disposition", "attachment; filename\"" + filename + "\" ")
											.body(resource);
	}

	@DeleteMapping("/delete/{filename}")
	public ResponseEntity<String> deletefile(@PathVariable String filename) {
		return new ResponseEntity<String>(service.deleteFile(filename), HttpStatus.OK);
	}
}
