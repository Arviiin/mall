package cn.e3mall.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPublish {
	@Test
	public void publishService() throws Exception{
		//初始化一个Spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//让程序不会结束      这里主要是测试duboo服务发布与tomcat没关系,实际上是spring来发布服务,
		//只要初始化一个Spring容器并且程序一直运行就可以发布服务了.
		//如果是测试后台管理这个系统,测试的时候manager不要开启(也即是没有启动tomcat),只要开manager-web就可以了.
		//然后运行这个junit测试,通过对比:发现manager里的tomcat(在manager这个聚合工程的pom.xml中)和此处的代码一样其实就是提供了一个Spring容器,没有别的用处.
		//用tomcat的原因在于,后面部署系统比较方便.
		System.out.println("服务已经启动...");
		System.in.read();
		System.out.println("服务已经关闭");
	}

}
