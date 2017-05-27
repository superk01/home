package org.zerock.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

  private static final Logger logger = 
      LoggerFactory.getLogger(UploadFileUtils.class);

//  public static String uploadFile(String uploadPath, 
//      String originalName, 
//      byte[] fileData)throws Exception{
//    
//    return null;
//  }
//  

  
  public static String uploadFile(String uploadPath, 
                              String originalName, 
                              byte[] fileData)throws Exception{
    
	  System.out.println(originalName);
	  
    UUID uid = UUID.randomUUID();																		//UUID를 이용한 고유한 값 생성(파일명)
    
    String savedName = uid.toString() +"_"+originalName;											//고유한 값_원래 파일명.
    System.out.println(savedName);
    String savedPath = calcPath(uploadPath);
    System.out.println("savedPath:"+savedPath);														//"\2017\05\25"
    
    File target = new File(uploadPath +savedPath,savedName);									//업로드할 파일에 관한 객체를 생성한다.
    
    FileCopyUtils.copy(fileData, target);																	//그리고 해당 파일 정보에 파일일 생성한다.
    
    String formatName = originalName.substring(originalName.lastIndexOf(".")+1);		//확장자를 받는다.
    
    String uploadedFileName = null;
    
    if(MediaUtils.getMediaType(formatName) != null){												//확장자가 이미지파일에 관련된 것인지를 확인
    																													//MediaUtils객체에 IMAGE_JPEG->jpg, IMAGE_GIF->gif, IMAGE_PNG->png처럼 MINE타입이 문자열로 미리 정의되어 있다.
      uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);			//위 MINE타입에 해당하면 이미지 파일 취급하고 썸네일을 생성.
    }else{
      uploadedFileName = makeIcon(uploadPath, savedPath, savedName);					//해당 MINE타입이 아니면 썸네일이미지를 만들지 않고 아이콘만 생성.
    }
    
    return uploadedFileName;
    
  }
  
  private static  String makeIcon(String uploadPath, 
      String path, 
      String fileName)throws Exception{																	//파라미터값이 upload폴더 경로, 날짜와 관련된 폴더들, 생성할 파일 이름.

    String iconName = 
        uploadPath + path + File.separator+ fileName;												//"upload\2017\05\25\파일명"
    
    return iconName.substring(
        uploadPath.length()).replace(File.separatorChar, '/');										//최종리턴값 : "upload/2017/05/25/파일명"
  }
  
  
  private static  String makeThumbnail(
              String uploadPath, 
              String path, 
              String fileName)throws Exception{
            
    BufferedImage sourceImg = 
        ImageIO.read(new File(uploadPath + path, fileName));									//원본파일을 메모리에 로딩하고 정해진 크기에 맞게 작은 이미지 파일을 만들어 줌.
    
    BufferedImage destImg = 
        Scalr.resize(sourceImg, 
            Scalr.Method.AUTOMATIC, 
            Scalr.Mode.FIT_TO_HEIGHT,100);															//그래서 정해진 크기는 100px;
    
    String thumbnailName = 
        uploadPath + path + File.separator +"s_"+ fileName;										//썸네일 이미지의 파일명은 원본파일 이름 앞에 s_가 붙는다.
    System.out.println(thumbnailName);
    
    File newFile = new File(thumbnailName);
   
    String formatName = 
        fileName.substring(fileName.lastIndexOf(".")+1);
    
    System.out.println("uppercase:"+formatName.toUpperCase());
    System.out.println("toString():"+newFile.toString());
    ImageIO.write(destImg, formatName.toUpperCase(), newFile);								//썸네일 파일 정보가 destImg에 있고 그 것을 formatName형식으로 써서 newFile객체에 쓴다
    return thumbnailName.substring(
        uploadPath.length()).replace(File.separatorChar, '/');
  } 
  
  
  private static String calcPath(String uploadPath){
    
    Calendar cal = Calendar.getInstance();																//오늘의 날짜.(2017.05.25)
    
    String yearPath = File.separator+cal.get(Calendar.YEAR);									//yearPath="\2017"
    
    String monthPath = yearPath + 
        File.separator + 
        new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);						//monthPath="\2017\05"

    String datePath = monthPath + 
        File.separator + 
        new DecimalFormat("00").format(cal.get(Calendar.DATE));								//datePath="\2017\05\25"
    
    makeDir(uploadPath, yearPath,monthPath,datePath);											//해당하는 경로의 각 폴더들을 생성.
    
    logger.info(datePath);
    
    return datePath;
  }
  
  
  private static void makeDir(String uploadPath, String... paths){
    
	  System.out.println("makeDir:"+paths[paths.length-1]);										//"\2017\05\25->datePath"
    if(new File(paths[paths.length-1]).exists()){														//datePath까지 경로가 만들어 졌는지 확인.
      return;
    }
    
    for (String path : paths) {																
      
      File dirPath = new File(uploadPath + path);														//yearPath, monthPath, datePath까지 순서대로 파일 객체를 생성/
      	
      if(! dirPath.exists() ){																					//해당경로에 폴더가 없으면 
        dirPath.mkdir();																							//폴더를 생성.
      } 
    }
  }
  
  
}
