package org.zerock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

import org.apache.commons.io.IOUtils;
import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.util.MediaUtils;
import org.zerock.util.UploadFileUtils;

@Controller
public class UploadController {
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	private String uploadFile(String originalName, byte[] fileData)throws Exception {
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString()+"_"+originalName;
		File target=new File(uploadPath, savedName);
		
		FileCopyUtils.copy(fileData, target);
		
		return savedName;
	}
	
	@RequestMapping(value="uploadForm", method=RequestMethod.GET)
	public void uploadFormGET() {
		
	}
	
	@RequestMapping(value="uploadForm", method=RequestMethod.POST)
	public String uploadFormPOST(MultipartFile file, Model model)throws Exception{
		System.out.println("uploadFormPOST");
		System.out.println(file.getOriginalFilename());
		String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
		model.addAttribute("savedName", savedName);
		
		return "uploadResult";
	}
	@RequestMapping(value="uploadAjax", method=RequestMethod.GET)
	public void uploadAjaxGET(){
		
	}
	@ResponseBody
	@RequestMapping(value="uploadAjax", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjaxPOST(MultipartFile file)throws Exception{
		/*System.out.println(UploadFileUtils.uploadFile("uploadAjaxPOST:"+uploadPath, file.getOriginalFilename(), file.getBytes()));*/
		return new ResponseEntity<>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()),HttpStatus.CREATED);
	}
	@ResponseBody
	@RequestMapping(value="displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName)throws Exception{
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			MediaType mediaType = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(uploadPath+fileName);
			if(mediaType!=null){
				headers.setContentType(mediaType);
			}else{
				fileName=fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			entity= new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName){
		System.out.println(fileName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		System.out.println(formatName);
		MediaType mediaType = MediaUtils.getMediaType(formatName);
		
		if(mediaType!=null){
			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			
			new File(uploadPath+(front+end).replace('/', File.separatorChar)).delete();//원본파일 삭제
			
		}
		new File(uploadPath+fileName.replace('/', File.separatorChar)).delete();//썸네일 파일 삭제
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
