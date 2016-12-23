package activiti;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.EngineServices;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TestActiviti {

	public static final EngineServices processEngine;
	static{
		processEngine = ProcessEngineConfiguration  
	            .createProcessEngineConfigurationFromResource(  
	                    "activiti.cfg.xml").buildProcessEngine();  
	}
	
	//方法1：使用代码来创建activiti工作流的表

	// 使用代码创建工作流需要的23张表
	@Test
	public void createTable() {
		// 工作流引擎的全部配置
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();

		// 链接数据的配置
		processEngineConfiguration.setJdbcDriver("org.mariadb.jdbc.Driver");
		processEngineConfiguration
				.setJdbcUrl("jdbc:mariadb://123.206.31.80:3306/activiti?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("hf0321");
	 
		/*
		 * public static final String DB_SCHEMA_UPDATE_FALSE = "false";
		 * 不能自动创建表，需要表存在 public static final String DB_SCHEMA_UPDATE_CREATE_DROP
		 * = "create-drop"; 先删除表再创建表 public static final String
		 * DB_SCHEMA_UPDATE_TRUE = "true";如果表不存在，自动创建表
		 */
		//如果表不存在，自动创建表
		processEngineConfiguration
				.setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		// 工作流的核心对象，ProcessEnginee对象
		ProcessEngine processEngine = processEngineConfiguration
				.buildProcessEngine();
		System.out.println(processEngine);


	}
	
	@Test  
	public void deploymentProcessDefinition() {  
	    //通过让工作流引擎的全部配置对象来执行配置文件中的内容来创建流程引擎对象  
//	    ProcessEngine processEngine = ProcessEngineConfiguration  
//	            .createProcessEngineConfigurationFromResource(  
//	                    "activiti.cfg.xml").buildProcessEngine();  
	    //ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();   
	    
	    Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service  
	            .createDeployment()// 创建一个部署对象  
	            .name("helloworld入门程序")// 添加部署的名称  
	            .addClasspathResource("diagrams/helloworld.bpmn")// classpath的资源中加载，一次只能加载  
	                                                                // 一个文件  
	            //.addClasspathResource("diagrams/helloworld.png")// classpath的资源中加载，一次只能加载  
	                                                            // 一个文件  
	            .deploy();// 完成部署  
	    System.out.println("部署ID:" + deployment.getId());  
	    System.out.println("部署名称：" + deployment.getName());  
	}  
	
	/** 
	 * 启动流程实例 
	 */  
	@Test  
	public void startProcessInstance() {  
	    // 流程定义的key  
	    String processDefinitionKey = "HelloWorld";  
	    ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service  
	            .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动  
	    System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101  
	    System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4  
	}
	
	/** 
	 * 查询历史流程实例 
	 */  
	@Test  
	public void findHistoryProcessInstance(){  
	    String processInstanceId="22501";  
	    HistoricProcessInstance hpi = processEngine.getHistoryService()  
	            .createHistoricProcessInstanceQuery()  
	            .processInstanceId(processInstanceId)  
	            .singleResult();  
	    System.out.println(hpi.getId() +"    "+hpi.getProcessDefinitionId()+"   "+ hpi.getStartTime()+"   "+hpi.getDurationInMillis());  
	}  
	
	
	
	/** 
	 * 查询当前人的个人任务 
	 */  
	@Test  
	public void findMyPersonTask() {  
	    String assignee = "张三";  
	    List<Task> list = processEngine.getTaskService()// 与正在执行的认为管理相关的Service  
	            .createTaskQuery()// 创建任务查询对象  
	            .taskAssignee(assignee)// 指定个人认为查询，指定办理人  
	            .list();  
	  
	    if (list != null && list.size() > 0) {  
	        for (Task task:list) {  
	            System.out.println("任务ID:"+task.getId());  
	            System.out.println("任务名称:"+task.getName());  
	            System.out.println("任务的创建时间"+task.getCreateTime());  
	            System.out.println("任务的办理人:"+task.getAssignee());  
	            System.out.println("流程实例ID:"+task.getProcessInstanceId());  
	            System.out.println("执行对象ID:"+task.getExecutionId());  
	            System.out.println("流程定义ID:"+task.getProcessDefinitionId());  
	            System.out.println("#################################");  
	        }  
	    }  
	}  
	
	/** 
	 * 完成我的任务 
	 */  
	@Test  
	public void compliteMyPersonTask() {  
	    // 任务ID  
	    String taskId = "22504";  
	    processEngine.getTaskService().complete(taskId);  
	    ;  
	    System.out.println("完成任务：任务ID:" + taskId);  
	}  
	
	/** 
	 * 查询历史任务 
	 */  
	@Test  
	public void findHistoryTask(){  
	    String processInstanceId="22501";  
	    List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的service  
	            .createHistoricTaskInstanceQuery()//创建历史任务实例查询  
	            .processInstanceId(processInstanceId)  
//	              .taskAssignee(taskAssignee)//指定历史任务的办理人  
	            .list();  
	    if(list!=null && list.size()>0){  
	        for(HistoricTaskInstance hti:list){  
	            System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());  
	            System.out.println("################################");  
	        }  
	    }     
	  
	}  
	
	/** 
	 * 查询流程状态（判断流程正在执行，还是结束） 
	 */  
	@Test  
	public void isProcessEnd(){  
	    String processInstanceId =  "22501";  
	    ProcessInstance pi = processEngine.getRuntimeService()//表示正在执行的流程实例和执行对象  
	            .createProcessInstanceQuery()//创建流程实例查询  
	            .processInstanceId(processInstanceId)//使用流程实例ID查询  
	            .singleResult();  
	      
	    if(pi==null){  
	        System.out.println("流程已经结束");  
	    }  
	    else{  
	        System.out.println("流程没有结束");  
	    }  
	      
	}  
	
	/** 
	 * 查询所有的流程定义 
	 */  
	@Test  
	public void findProcessDefinition() {  
	    List<ProcessDefinition> list = processEngine.getRepositoryService()// 与流程定义和部署对象先相关的service  
	            .createProcessDefinitionQuery()// 创建一个流程定义的查询  
	            /** 指定查询条件，where条件 */  
	            // .deploymentId(deploymentId) //使用部署对象ID查询  
	            // .processDefinitionId(processDefinitionId)//使用流程定义ID查询  
	            // .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询  
	  
	            /* 排序 */  
	            .orderByProcessDefinitionVersion().asc()  
	            // .orderByProcessDefinitionVersion().desc()  
	  
	            /* 返回的结果集 */  
	            .list();// 返回一个集合列表，封装流程定义  
	    // .singleResult();//返回惟一结果集  
	    // .count();//返回结果集数量  
	    // .listPage(firstResult, maxResults);//分页查询  
	  
	    if (list != null && list.size() > 0) {  
	        for (ProcessDefinition pd : list) {  
	            System.out.println("流程定义ID:" + pd.getId());// 流程定义的key+版本+随机生成数  
	            System.out.println("流程定义的名称:" + pd.getName());// 对应helloworld.bpmn文件中的name属性值  
	            System.out.println("流程定义的key:" + pd.getKey());// 对应helloworld.bpmn文件中的id属性值  
	            System.out.println("流程定义的版本:" + pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1  
	            System.out.println("资源名称bpmn文件:" + pd.getResourceName());  
	            System.out.println("资源名称png文件:" + pd.getDiagramResourceName());  
	            System.out.println("部署对象ID：" + pd.getDeploymentId());  
	            System.out.println("#########################################################");  
	        }  
	    }  
	}  
	
	/** 
	 * 附加功能，查询最新版本的流程定义 
	 */  
	@Test  
	public void findLastVersionProcessDefinition() {  
	    List<ProcessDefinition> list = processEngine.getRepositoryService()  
	            .createProcessDefinitionQuery()  
	            .orderByProcessDefinitionVersion().asc() // 使用流程定义的版本升序排列  
	            .list();  
	  
	    /** 
	     * Map<String,ProcessDefinition> map集合的key：流程定义的key map集合的value：流程定义的对象 
	     * map集合的特点：当map集合key值相同的情况下，后一次的值将替换前一次的值 
	     */  
	    Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();  
	    if (list != null && list.size() > 0) {  
	        for (ProcessDefinition pd : list) {  
	            map.put(pd.getKey(), pd);  
	        }  
	    }  
	  
	    List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(  
	            map.values());  
	    if (pdList != null && pdList.size() > 0) {  
	        for (ProcessDefinition pd : pdList) {  
	            System.out.println("流程定义ID:" + pd.getId());// 流程定义的key+版本+随机生成数  
	            System.out.println("流程定义的名称:" + pd.getName());// 对应helloworld.bpmn文件中的name属性值  
	            System.out.println("流程定义的key:" + pd.getKey());// 对应helloworld.bpmn文件中的id属性值  
	            System.out.println("流程定义的版本:" + pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1  
	            System.out.println("资源名称bpmn文件:" + pd.getResourceName());  
	            System.out.println("资源名称png文件:" + pd.getDiagramResourceName());  
	            System.out.println("部署对象ID：" + pd.getDeploymentId());  
	            System.out  
	                    .println("#########################################################");  
	        }  
	    }  
	  
	}  
	
	/** 
	 * 删除流程定义(删除key相同的所有不同版本的流程定义) 
	 */  
	@Test  
	public void delteProcessDefinitionByKey() {  
	    // 流程定义的Key  
	    String processDefinitionKey = "HelloWorld";  
	    // 先使用流程定义的key查询流程定义，查询出所有的版本  
	    List<ProcessDefinition> list = processEngine.getRepositoryService()  
	            .createProcessDefinitionQuery()  
	            .processDefinitionKey(processDefinitionKey)// 使用流程定义的key查询  
	            .list();  
	    // 遍历，获取每个流程定义的部署ID  
	    if (list != null && list.size() > 0) {  
	        for (ProcessDefinition pd : list) {  
	            // 获取部署ID  
	            String deploymentId = pd.getDeploymentId();  
	            //      /*  
	            //       * 不带级联的删除， 只能删除没有启动的流程，如果流程启动，就会抛出异常  
	            //       */  
	            //       processEngine.getRepositoryService().deleteDeployment(deploymentId);  
	              
	            /** 
	             * 级联删除 不管流程是否启动，都可以删除 
	             */  
	            processEngine.getRepositoryService().deleteDeployment(  
	                    deploymentId, true);  
	  
	        }  
	  
	    }  
	}  
}
