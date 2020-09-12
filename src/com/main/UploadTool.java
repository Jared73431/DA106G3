package com.main;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class UploadTool {
    private int buffersize = 4096;
    private int SizeMax = 1024 * 1024;// 1Mbyte最大檔案大小
    private File tempfile = null;
    private String def_upload_dir = null;
    // 用來存parameter
    private Map map = null;
    private Map uploadlist = null;
    // 處始化時把給request把所有的值取出,存入map
    public UploadTool(HttpServletRequest request) throws FileUploadException,
            UnsupportedEncodingException {
        map = new HashMap();
        uploadlist = new HashMap();
        // 建立一個以disk-base的檔案物件
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 初始化內容
        // 傳送所用的buffer空間
        factory.setSizeThreshold(buffersize);
        // The directory in which temporary files will be located.
        factory.setRepository(tempfile);
        // 建立一個檔案上傳的物件
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 最大檔案大小
        upload.setSizeMax(SizeMax * 10);
        // 每一個Fileitem代表一個form上傳的物件內容ex input type="text"
        List items = null; // 會產生 FileUploadException
        // 把資料從request取出
        items = upload.parseRequest((RequestContext) request); // Parse the request
        Iterator iter = items.iterator();
        while (iter.hasNext()) {// 先把所有參數取得而不先write to file
            FileItem item = (FileItem) iter.next();
            // 一般文字欄位
            if (item.isFormField()) {
                map.put(item.getFieldName(), item.getString("Big5"));
                System.out.println("上傳檔案的其它參數:" + item.getFieldName() + "="
                        + item.getString("Big5"));
            } else {// 上傳檔案欄位
                // or it's a file upload request
                if (item.getSize() > 0) {
                    uploadlist.put(item.getFieldName(), item);
                    System.out.println("上傳檔案:" + item.getFieldName());
                }
            }
        }
    }
    // 設定檔案上傳後存放的地方
    public void setUploadDir(String upload_dir) {
        this.def_upload_dir = upload_dir;
    }
    // 取得所有欄位,包含一般欄位及上傳的欄位
    public Map getAllParameter() {
        Map rvalue = new HashMap();
        rvalue.putAll(map);
        rvalue.putAll(uploadlist);
        return rvalue;
    }
    // 取得某一欄位的值,一般欄位
    public String getParameter(String FieldName) {
        if (map.containsKey(FieldName))
            return String.valueOf(map.get(FieldName));
        else
            return null;
    }
    // 取得某一欄位的值,上傳欄位
    public FileItem getUploadParameter(String FieldName) {
        if (uploadlist.containsKey(FieldName))
            return (FileItem) uploadlist.get(FieldName);
        else
            return null;
    }
    // 檢查上傳資料是否正確
    public String checkUpload() {
        Iterator iter = uploadlist.keySet().iterator();
        while (iter.hasNext()) {
            Object Name = iter.next();
            FileItem item = (FileItem) uploadlist.get(Name);
            String itename = item.getName();
            System.out.println("上傳的檔案為:" + itename);
            if (item.getSize() > SizeMax)
                return "檔案太大!";
        }
        return "";
    }
    // 開始上傳
    public String doUpload(FileItem item, String fileName) {
        String str = "";
        long sizeInBytes = item.getSize();
        // 碓認上傳資料是否有誤
        if (sizeInBytes > SizeMax)
            return "檔案太大!";
        if (sizeInBytes > 0) {
            int index = -1;
            String itename = null;
            if ((index = item.getName().lastIndexOf("\\")) != -1)
                itename = item.getName().substring(index,
                        item.getName().length());
            else
                itename = item.getName();
            // 副檔名
            String formatName = itename.substring(itename.length() - 4,
                    itename.length());
            fileName = (fileName + formatName).toLowerCase();
            System.out.println("上傳檔案檔案名稱:" + fileName);
            File uploadedFile = new File(def_upload_dir + fileName);
            // 會產生 Exception
            try {
                item.write(uploadedFile);
            } catch (Exception e) {
                System.out.println("上傳失敗!" + e.toString());
                str = "上傳失敗!";
            }
            // 會產生 Exception
        }
        return str;
    }
    // 是否存在此上傳欄位資料
    public boolean isExtUpload(String fileName) {
        return uploadlist.containsKey(fileName);
    }
}
