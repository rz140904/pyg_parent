package cn.itcast.core.controller;

import cn.itcast.core.common.FastDFSClient;
import cn.itcast.core.pojo.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadContorller {


    //根据键找值获取服务器下载路径(服务器地址)
    @Value("${FILE_SERVER_URL}")
    private String flieServer;


    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){

        try {
            //创建fastDFS工具类对象 传入配置文件路径
            FastDFSClient fastDFS = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            //上传并返回文件的路径和文件名
            String path = fastDFS.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());
            //上传后返回fastDFS文件服务器地址+ 上传后的文件路径和文件名
            return new Result(true,flieServer+path);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }
}
