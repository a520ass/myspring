package com.hf.config.hadoop;


//@Configuration
//@EnableHadoop
public class HadoopConfig/* extends SpringHadoopConfigurerAdapter*/ {

	/*@Override
	public void configure(HadoopConfigConfigurer config) throws Exception {
		config.fileSystemUri("hdfs://192.168.153.161:9000");
	}
	
	@Bean
	public FileSystemFactoryBean fileSystemFactoryBean(org.apache.hadoop.conf.Configuration configuration){
		FileSystemFactoryBean fileSystemFactoryBean=new FileSystemFactoryBean();
		fileSystemFactoryBean.setConfiguration(configuration);
		fileSystemFactoryBean.setUser("hadoop");	//设置hadoop用户
		return fileSystemFactoryBean;
	}*/
	
	/*@Bean
	public FileSystem fileSystem(org.apache.hadoop.conf.Configuration configuration) throws Exception{
		return FileSystem.get(configuration);
	}*/
}
