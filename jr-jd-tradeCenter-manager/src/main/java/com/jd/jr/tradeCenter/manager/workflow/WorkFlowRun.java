package com.jd.jr.tradeCenter.manager.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuo7 on 2016/7/7.
 */
@Component
public class WorkFlowRun {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;


    public void startEnvent(String ID,String userID){
        System.out.println("开始录入资产");
        repositoryService.createDeployment().addClasspathResource("workFlow/VariablesProcess.bpmn20.xml").deploy();
//        String processID =  runtimeService.startProcessInstanceByKey("financeAssets").getId();
//        System.out.println("新建流程ID"+processID);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("ipnuterGroup", userID);
        variables.put("assetID", ID);
        variables.put("notices", userID+" 录入虚拟资产");

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financeAssets", variables);

        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        System.out.println("ProcessInstance: " + processInstance.getId());
    }

    public List<Map<String,Object>> queryMyToDo(String userID,String rolesGroup){
        System.out.println("用户："+userID+"正在查询待办，所属角色组："+ rolesGroup);
        List<Map<String,Object>> taskAndVariablesList = new ArrayList<Map<String, Object>>();

        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup(rolesGroup).list();
        List<Task> assignList = taskService.createTaskQuery().taskAssignee(userID).list();
        taskList.addAll(assignList);
        for (Task task:taskList) {
            HashMap<String,Object> hashMap = new HashMap<String, Object>();
            hashMap.put("taskID",task.getId());
            hashMap.put("taskgetDescription",task.getDescription());
            hashMap.put("taskName",task.getName());
//          taskName:运营初审
//          ipnuterGroup = inputer2
//          notices = inputer2 录入虚拟资产
//          assetID = 66684
            Map variables = taskService.getVariables(task.getId());
            hashMap.put("assetID",variables.get("assetID"));
            hashMap.put("notices",variables.get("notices"));
            hashMap.put("ipnuterGroup",variables.get("ipnuterGroup"));
            taskAndVariablesList.add(hashMap);
        }

        return taskAndVariablesList;
    }
    public void operate(String taskID,Map variables){
        taskService.complete(taskID,variables);
    }

}
