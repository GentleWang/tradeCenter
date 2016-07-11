package com.jd.jr.tradeCenter.manager.workflow;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
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
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IdentityService identityService;


    public void startEnvent(String ID, String userID) {
        System.out.println("开始录入资产");
        repositoryService.createDeployment().addClasspathResource("workFlow/VariablesProcess.bpmn20.xml").deploy();
//        String processID =  runtimeService.startProcessInstanceByKey("financeAssets").getId();
//        System.out.println("新建流程ID"+processID);

        Map<String, Object> initVariables = new HashMap<String, Object>();
        initVariables.put("ipnuterGroup", userID);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financeAssets", initVariables);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId("financeAssets", variables,userID);

        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        System.out.println("ProcessInstance: " + processInstance.getId());

        Task inputTask = taskService.createTaskQuery().taskAssignee(userID).processInstanceId(processInstance.getId()).singleResult();
        Map<String, Object> inputVariables = new HashMap<String, Object>();
        inputVariables.put("ipnuterGroup", userID);
        inputVariables.put("assetID", ID);
        inputVariables.put("notices", userID + " 录入虚拟资产");
        taskService.complete(inputTask.getId(), inputVariables);
    }

    public List<Map<String, Object>> queryMyToDo(String userID, String rolesGroup) {
        System.out.println("用户：" + userID + "正在查询待办，所属角色组：" + rolesGroup);
        List<Map<String, Object>> taskAndVariablesList = new ArrayList<Map<String, Object>>();

        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup(rolesGroup).list();
        List<Task> assignList = taskService.createTaskQuery().taskAssignee(userID).list();
        taskList.addAll(assignList);
        for (Task task : taskList) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("taskID", task.getId());
            hashMap.put("taskgetDescription", task.getDescription());
            hashMap.put("taskName", task.getName());
//          taskName:运营初审
//          ipnuterGroup = inputer2
//          notices = inputer2 录入虚拟资产
//          assetID = 66684
            Map variables = taskService.getVariables(task.getId());
            hashMap.put("assetID", variables.get("assetID"));
            hashMap.put("notices", variables.get("notices"));
            hashMap.put("ipnuterGroup", variables.get("ipnuterGroup"));
            hashMap.put("progressInstanceID", task.getProcessInstanceId());
            taskAndVariablesList.add(hashMap);
        }

        return taskAndVariablesList;
    }

    public List<Map<String, Object>> queryMyHadDone(String userID) {
        System.out.println("用户：" + userID + "正在查询已办");
        List<Map<String, Object>> taskAndVariablesList = new ArrayList<Map<String, Object>>();

        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                // 过滤条件
                .taskAssignee(userID).finished()
                // 排序条件
                .orderByHistoricActivityInstanceEndTime().asc()
                // 执行查询
                .list();
        for (HistoricActivityInstance historicActivityInstance : hais) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("progressInstanceID", historicActivityInstance.getProcessInstanceId());
            hashMap.put("activityName", historicActivityInstance.getActivityName());
            hashMap.put("startTime", historicActivityInstance.getStartTime());
            hashMap.put("endTime", historicActivityInstance.getEndTime());
            taskAndVariablesList.add(hashMap);
        }
        return taskAndVariablesList;
    }

    public void operate(String taskID, Map variables, String userID) {
        taskService.claim(taskID, userID);
        taskService.complete(taskID, variables);
    }

    public List<HashMap<String,Object>> queryProcessIntenceDetail(String processIntanceID){
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                // 过滤条件
                .processInstanceId(processIntanceID)
                // 排序条件
                .orderByHistoricActivityInstanceEndTime().asc()
                // 执行查询
                .list();
        List<HashMap<String,Object>> activityList = new ArrayList<HashMap<String, Object>>();
        for (HistoricActivityInstance hai : hais) {
            HashMap<String, Object> activityMap = new HashMap<String, Object>();
            activityMap.put("activityName", hai.getActivityName());
            activityMap.put("activityAssignee", hai.getAssignee());
            activityMap.put("activityEndTime", hai.getEndTime());
            activityMap.put("duration", hai.getDurationInMillis());
            if(!"exclusiveGateway".equals(hai.getActivityType())){
                activityList.add(activityMap);
            }
        }
        return activityList;
    }

}