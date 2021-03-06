package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

/**
 * 图片上传Controller
 * <p>Title: PictureController</p>
 * <p>Description: </p>
 * <p>Company: www.my.dhu.edu.cn</p>
 * @author LongLong
 * @data 2018年6月16日
 * @version 1.0
 */
@Controller
public class PictureController {
	
	
	//spring容器加载的配置文件,可以这样取.resource.properties
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	//可以自己设置返回类型是Content-Type: text/plain,字符集是utf-8
	//@RequestMapping(value="/pic/upload", produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	
	@RequestMapping("/pic/upload")
	@ResponseBody//意义:直接响应浏览器,不走逻辑视图了,相当于响应response对象调用writer方法,直接往浏览器写返回的内容
				//不过如果返回类型是一个对象的话,就会有一个默认行为,会把对象变成json再响应
	//如果返回值类型是String的话,因为浏览器可以识别,所以不加转换,直接响应.兼容性更好
//	public Map fileUpload(MultipartFile uploadFile) {//为了兼容性不用Map或者其他类,这个时候响应的类型为application/json
	public String fileUpload(MultipartFile uploadFile) {//手工转成json字符串,此时响应的类型为Content-Type: text/plain;
														//虽然返回内容和之前json一样,但被认为是一个普通的文本,兼容性好
		
		try {											
			//1、取文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			//2、创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			//3、执行上传处理
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//4、拼接返回的url和ip地址，拼装成完整的url
			String url = IMAGE_SERVER_URL + path;
			//5、返回map
			Map result = new HashMap<>();
			//正常情况下
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);//这样就变成了json字符串
		} catch (Exception e) {
			e.printStackTrace();
			//5、返回map
			//除问题的情况下
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);//这样就变成了json字符串;
		}
	}

}
