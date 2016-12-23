package com.hf.web;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessInstanceDiagramCmd implements Command<InputStream>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ProcessInstanceDiagramCmd.class);
	protected String processInstanceId;

	public ProcessInstanceDiagramCmd(String processInstanceId) {
		if (processInstanceId == null || processInstanceId.length() < 1) {
			throw new ActivitiIllegalArgumentException(
					"The process instance id is mandatory, but '"
							+ processInstanceId + "' has been provided.");
		}
		this.processInstanceId = processInstanceId;
	}

    public InputStream execute(CommandContext commandContext) {
        ExecutionEntityManager executionEntityManager = commandContext
            .getExecutionEntityManager();
        ExecutionEntity executionEntity = executionEntityManager
            .findExecutionById(processInstanceId);
        List<String> activiityIds = executionEntity.findActiveActivityIds();
        String processDefinitionId = executionEntity.getProcessDefinitionId();
        GetBpmnModelCmd getBpmnModelCmd = new GetBpmnModelCmd(processDefinitionId);
        BpmnModel bpmnModel = getBpmnModelCmd.execute(commandContext);
        InputStream is = new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel,
                "png", activiityIds);

        return is;
    }

}
