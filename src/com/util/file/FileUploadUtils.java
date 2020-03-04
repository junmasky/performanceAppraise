package com.util.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.util.log.Log;
import com.util.string.StringUtil;

public class FileUploadUtils {
	private static Log log=new Log(FileUploadUtils.class);
	
	//储存路径
	private static String path = getFilePath();
	
	
	
	/**
	 * 返回上传成功的文件路径
	 * @param request
	 * @param storePath 储存文件的相对路径
	 * @return
	 */
	public static List<Map<String,String>> fileUpload(HttpServletRequest request,String storePath){
		ServletContext s = request.getSession().getServletContext();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		List<Map<String,String>> filePaths = new ArrayList<Map<String,String>>();
		//获取文件储存的绝对路径
		String absolutePath = path+storePath+"/";
		
		if(multipartResolver.isMultipart(request) && !"".equals(path)){
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> fileNames = multiRequest.getFileNames();
			
			while(fileNames.hasNext()){
				String fileName = fileNames.next();
				System.out.println("fileName:"+fileName);
				List<MultipartFile> fileList = multiRequest.getFiles(fileName);
				
				if(fileList.size() > 0){
					Iterator<MultipartFile> fileIte = fileList.iterator();
					
					while(fileIte.hasNext()){
						MultipartFile multipartFile = fileIte.next();
						String originalFilename = multipartFile.getOriginalFilename();
						System.out.println("originalFilename:"+originalFilename);
						
						//判断文件储存目录是否存在
						File dir = new File(absolutePath);
						if(!dir.exists()){
							//如果文件储存目录建立失败则跳出循环,上传失败
							if(!dir.mkdirs()){
								log.info("上传文件储存目录建立失败");
								break;
							}
						}
						
						String newFileName = StringUtil.getUUID();
						String filePath = absolutePath + newFileName;
						System.out.println("oldFileName:"+originalFilename);
						System.out.println("newFileName:"+newFileName);
						Map<String,String> fileMap = new HashMap<String, String>();
						fileMap.put("oldFileName", originalFilename);
						fileMap.put("newFileName", newFileName);
						fileMap.put("filePath", absolutePath);
						File dest = new File(filePath);
						if(!dest.exists()){
							try {
								multipartFile.transferTo(dest);
								filePaths.add(fileMap);
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								log.error(e.getMessage(),e);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								log.error(e.getMessage(),e);
							}
						}
						String contentType = multipartFile.getContentType();
						System.out.println("contentType:"+contentType);
						String name = multipartFile.getName();
						System.out.println("name:"+name);
						long size = multipartFile.getSize();
						System.out.println("size:"+size);
					}
				}
			}
		}
		return filePaths;
	}
	
	/**
	 * 获取上传文件储存目录
	 * @param request
	 * @return
	 */
	public static String getFilePath(){
		String path = "";
		try {
			path = PropertiesLoaderUtils.loadAllProperties("sysconfig.properties").getProperty("uploadFilePath");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return StringUtil.showNull(path);
	}

	public static String getFilePath(String key){
		String path = "";
		try {
			path = PropertiesLoaderUtils.loadAllProperties("sysconfig.properties").getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return StringUtil.showNull(path);
	}
}
