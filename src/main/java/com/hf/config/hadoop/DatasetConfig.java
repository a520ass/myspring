package com.hf.config.hadoop;


//@Configuration
public class DatasetConfig {
	
	/*@Autowired
	private org.apache.hadoop.conf.Configuration hadoopConfiguration;

	@Bean
	public DatasetRepositoryFactory datasetRepositoryFactory() {
		DatasetRepositoryFactory datasetRepositoryFactory = new DatasetRepositoryFactory();
		datasetRepositoryFactory.setConf(hadoopConfiguration);
		datasetRepositoryFactory.setBasePath("/user/hadoop");
		datasetRepositoryFactory.setNamespace("default");
		return datasetRepositoryFactory;
	}

    @Bean
    public DataStoreWriter<FileInfo> dataStoreWriter() {
        return new AvroPojoDatasetStoreWriter<FileInfo>(FileInfo.class, datasetRepositoryFactory(), fileInfoDatasetDefinition());
    }

	@Bean
	public DatasetOperations datasetOperations() {
		DatasetTemplate datasetOperations = new DatasetTemplate();
		datasetOperations.setDatasetDefinitions(Arrays.asList(fileInfoDatasetDefinition()));
		datasetOperations.setDatasetRepositoryFactory(datasetRepositoryFactory());
 		return datasetOperations;
	}

    @Bean
    public DatasetDefinition fileInfoDatasetDefinition() {
        DatasetDefinition definition = new DatasetDefinition();
        definition.setFormat(Formats.AVRO.getName());
        definition.setTargetClass(FileInfo.class);
        definition.setAllowNullValues(false);
        return definition;
    }*/
}
