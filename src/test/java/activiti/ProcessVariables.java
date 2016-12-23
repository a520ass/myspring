package activiti;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessVariables {

	private static final ProcessEngine processEngine;
	static {
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}

	/**
	 * 部署流程定义（从inputStream）
	 */
	@Test
	public void deploymentProcessDefinition_inputStream() {

		InputStream inputStreamBpmn = this.getClass().getResourceAsStream(
				"/diagrams/processVariables.bpmn");
		// InputStream inputStreamPng = this.getClass().getResourceAsStream(
		// "/diagrams/processVariables.png");
		Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createDeployment()// 创建一个部署对象
				.name("流程定义")// 添加部署名称
				.addInputStream("processVariables.bpmn", inputStreamBpmn)// 使用资源文件的名称（要求:与资源文件的名称要一致），和输入流完成部署
				// .addInputStream("processVariables.png", inputStreamPng)//
				// 使用资源文件的名称(要求:与资源文件的名称要一致)，和输入流完成部署
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstance() { // 30001
		// 流程定义的key
		String processDefinitionKey = "processVariables";
		ProcessInstance pi = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应processVariables文件中的id的属性值，使用key值启动，默认是按照最新版本进行启动

		System.out.println("流程实例ID：" + pi.getId());
		System.out.println("流程定义ID：" + pi.getProcessDefinitionId());
		System.out.println("流程实例ID" + pi.getProcessInstanceId());

	}
	
	/** 
	 * 查询任务通过流程实例id 
	 */  
	@Test  
	public void findTask(){  	//30004
	    String processInstanceId="30001";  
	    List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的service  
	            .createHistoricTaskInstanceQuery()//创建历史任务实例查询  
	            .processInstanceId(processInstanceId)  
	            .list();  
	    if(list!=null && list.size()>0){  
	        for(HistoricTaskInstance hti:list){  
	            System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());  
	            System.out.println("################################");  
	        }  
	    }     
	}  
	
	/** 
	 * 设置流程变量 
	 */  
	@Test  
	public void setVariables() {  
	    // 与任务相关的service,正在执行的service  
	    TaskService taskService = processEngine.getTaskService();  
	  
	    // 任务ID  
	    String taskId = "30004";  
	  
//	    // 1.设置流程变量，使用基本数据类型  
//	    taskService.setVariable(taskId, "请假天数", 7);// 与任务ID邦德  
//	    taskService.setVariable(taskId, "请假日期", new Date());  
//	    taskService.setVariableLocal(taskId, "请假原因", "回去探亲，一起吃个饭123");  
//	      
	    Person p = new Person();  
	    p.setName("翠花");  
	    p.setId(20);  
	    p.setDate(); 
	    p.setNote("回去探亲，一起吃个饭123");  
	    taskService.setVariable(taskId, "人员信息(添加固定版本)", p);  
	    System.out.println("设置流程变量成功！");  
	  
	}  
	
	/** 
	 * 获取流程变量 
	 */  
	@Test  
	public void getVariables() {  
	    // 与任务（正在执行的service）  
	    TaskService taskService = processEngine.getTaskService();  
	    // 任务Id  
	    String taskId = "30004";  
	    // 1.获取流程变量，使用基本数据类型  
//	    Integer days = (Integer) taskService.getVariable(taskId, "请假天数");  
//	    Date date = (Date) taskService.getVariable(taskId, "请假日期");  
//	    String reason = (String) taskService.getVariable(taskId, "请假原因");  
//	  
//	    System.out.println("请假天数：" + days);  
//	    System.out.println("请假日期：" + date);  
//	    System.out.println("请假原因：" + reason);  
	 // 2.获取流程变量，使用javaBean类型  
	    Person p = (Person)taskService.getVariable(taskId, "人员信息(添加固定版本)");  
	    System.out.println(" 请假人：  "+p.getName()+"  请假天数：  "+p.getId()+"   请假时间："+ p.getDate()+ "   请假原因： "+p.getNote());  
	  
	}  
	
	/** 
	 * 查询流程变量的历史表 
	 */  
	@Test  
	public void findHistoryProcessVariables(){  
	    List<HistoricVariableInstance> list = processEngine.getHistoryService()  
	            .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询对象  
	            .variableName("请假原因")  
	            .list();  
	    if (list!=null &&list.size()>0) {  
	        for (HistoricVariableInstance hvi : list) {  
	            System.out.println(hvi.getId()+"     "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()  
	                    +"   "+hvi.getVariableTypeName()+"    "+hvi.getValue());  
	            System.out.println("########################################");  
	        }  
	    }  
	  
	}  
}
