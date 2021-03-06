package cn.e3mall.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class FastDfsTest {
	@Test
	public void testUpload() throws Exception {
		//创建一个配置文件。文件名任意。内容就是tracker服务器的地址。
		//使用全局对象加载配置文件。
		ClientGlobal.init("G:/Code/JavaEEProject/e3-manager-web/src/main/resources/conf/client.conf");//需要把复制过来的/改成/
//						  /G:/Code/JavaEEProject/e3-manager-web/target/classes/resource/client.conf
		//创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//通过TrackClient获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StrorageServer的引用，可以是null
		StorageServer storageServer = null;
		//创建一个StorageClient，参数需要TrackerServer和StrorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//使用StorageClient上传文件。
		String[] strings = storageClient.upload_file("D:/BaiduYunDownload/16、商城项目/01.参考资料/广告图片/f5fe1218bf3098984ec744bf993ee2fd.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	
	@Test
	public void testFastDfsClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("G:/Code/JavaEEProject/e3-manager-web/src/main/resources/conf/client.conf");
		String string = fastDFSClient.uploadFile("D:/BaiduYunDownload/16、商城项目/01.参考资料/广告图片/b463a2b010033397a2dcd09aa6f57d0c.jpg");
		System.out.println(string);
	}
}
