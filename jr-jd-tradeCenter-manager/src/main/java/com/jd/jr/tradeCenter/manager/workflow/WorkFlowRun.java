package com.jd.jr.tradeCenter.manager.workflow;

import com.jd.fastjson.JSON;
import jd.jr.workFlow.bo.request.OperateRequestBo;
import jd.jr.workFlow.bo.request.QueryRequestBo;
import jd.jr.workFlow.bo.request.StartProcessRequestBo;
import jd.jr.workFlow.bo.response.QueryResponseBo;
import jd.jr.workFlow.bo.response.StartProcessResponseBo;
import jd.jr.workFlow.bo.response.TaskResultBo;
import jd.jr.workFlow.enums.OperateEnum;
import jd.jr.workFlow.service.QueryWorkFlowService;
import jd.jr.workFlow.service.WorkFlowRunService;
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

    private static String system_source_id = "TradeCenter";

    @Autowired
    private WorkFlowRunService workFlowRunService;

    @Autowired
    private QueryWorkFlowService queryWorkFlowService;

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


    public void startEnvent(String ID, String userID,String workFlowType) {

        StartProcessRequestBo startProcessRequestBo = new StartProcessRequestBo();
        startProcessRequestBo.setBizType(workFlowType);
        startProcessRequestBo.setSystemSourceID(system_source_id);
        startProcessRequestBo.setIniter(userID);
        startProcessRequestBo.setRemark(userID+" 录入资产");
        startProcessRequestBo.setBussinessID(ID);
        StartProcessResponseBo startProcessResponseBo =workFlowRunService.startProcessInstance(startProcessRequestBo);
        System.out.println(startProcessResponseBo.toString());

//        System.out.println("开始录入资产");
//        repositoryService.createDeployment().addClasspathResource("workFlow/VariablesProcess.bpmn20.xml").deploy();
////        String processID =  runtimeService.startProcessInstanceByKey("financeAssets").getId();
////        System.out.println("新建流程ID"+processID);
//
//        Map<String, Object> initVariables = new HashMap<String, Object>();
//        initVariables.put("initer", userID);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financeAssets1", initVariables);
////        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId("financeAssets", variables,userID);
//
//        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
//        System.out.println("ProcessInstance: " + processInstance.getId());
//
//        Task inputTask = taskService.createTaskQuery().taskAssignee(userID).processInstanceId(processInstance.getId()).singleResult();
//        Map<String, Object> inputVariables = new HashMap<String, Object>();
//        inputVariables.put("ipnuterGroup", userID);
//        inputVariables.put("assetID", ID);
//        inputVariables.put("notices", userID + " 录入虚拟资产");
//        taskService.complete(inputTask.getId(), inputVariables);
    }

    public List<Map<String, Object>> queryMyToDo(String userID, String rolesGroup,String bizType) {

        System.out.println("用户：" + userID + "正在查询待办，所属角色组：" + rolesGroup+"，工作流类型："+bizType);
        QueryRequestBo queryRequestBo = new QueryRequestBo();
        queryRequestBo.setUserID(userID);
        queryRequestBo.setRoleGroupID(rolesGroup);
        queryRequestBo.setSystemSourceID(system_source_id);
        queryRequestBo.setBizType(bizType);
        QueryResponseBo queryResponseBo = queryWorkFlowService.queryMyTask(queryRequestBo);
        System.out.println(JSON.toJSONString(queryResponseBo));


        List<Map<String, Object>> taskAndVariablesList = new ArrayList<Map<String, Object>>();

//        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup(rolesGroup).list();
//        List<Task> assignList = taskService.createTaskQuery().taskAssignee(userID).list();
//        taskList.addAll(assignList);
        if("000000".equals(queryResponseBo.getRespCode())){
            for (TaskResultBo task : queryResponseBo.getTaskResultList()) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("processInstanceID",task.getBussinessID());
                hashMap.put("taskID", task.getTaskID());
                hashMap.put("taskgetDescription", task.getDescription());
                hashMap.put("taskName", task.getTaskName());
//          taskName:运营初审
//          ipnuterGroup = inputer2
//          notices = inputer2 录入虚拟资产
//          assetID = 66684
                hashMap.put("notices",task.getRemark());
                hashMap.put("ipnuterGroup", task.getProcessInstanceIniter());
                taskAndVariablesList.add(hashMap);
            }
        }
        return taskAndVariablesList;
    }

    public List<Map<String, Object>> queryMyHadDone(String userID,String bizType) {
        System.out.println("用户：" + userID + "正在查询已办");
        List<Map<String, Object>> taskAndVariablesList = new ArrayList<Map<String, Object>>();
        QueryRequestBo queryRequestBo = new QueryRequestBo();
        queryRequestBo.setUserID(userID);
        queryRequestBo.setBizType(bizType);
        queryRequestBo.setSystemSourceID(system_source_id);
//        queryRequestBo.setRoleGroupID("Operaters");
        QueryResponseBo queryResponseBo = queryWorkFlowService.queryMyHadDode(queryRequestBo);
        System.out.println(queryResponseBo.toString());

//        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
//                // 过滤条件
//                .taskAssignee(userID).finished()
//                // 排序条件
//                .orderByHistoricActivityInstanceEndTime().asc()
//                // 执行查询
//                .list();
        if("000000".equals(queryResponseBo.getRespCode())){
            for (TaskResultBo resp : queryResponseBo.getTaskResultList()) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("processInstanceID", resp.getBussinessID());
                hashMap.put("activityName", resp.getTaskName());
                hashMap.put("startTime", resp.getCreateTime());
                hashMap.put("endTime", resp.getEndTime());
                taskAndVariablesList.add(hashMap);
            }
        }
        return taskAndVariablesList;
    }

    public void operate(String businessID, Map variables, String userID,String bizType,String roleGroup) {

        OperateRequestBo operateRequestBo = new OperateRequestBo();
        operateRequestBo.setBizType(bizType);
        operateRequestBo.setSystemSourceID(system_source_id);
        operateRequestBo.setOpinions((String)variables.get(variables.get("notices")));
        operateRequestBo.setOperate(OperateEnum.valueOf((String)variables.get("AuditApproved")));
        operateRequestBo.setRemark((String)variables.get(variables.get("notices")));
        operateRequestBo.setBussinessID(businessID);
        operateRequestBo.setUserID(userID);
        operateRequestBo.setRoleGroupID(roleGroup);
        workFlowRunService.executeWorkFlow(operateRequestBo);
//        taskService.claim(taskID, userID);
//        taskService.complete(taskID, variables);
    }

    public List<HashMap<String,Object>> queryProcessIntenceDetail(String bizID,String bizType){

        List<HashMap<String,Object>> activityList = new ArrayList<HashMap<String, Object>>();
        QueryRequestBo queryRequestBo = new QueryRequestBo();
        queryRequestBo.setBizType(bizType);
        queryRequestBo.setSystemSourceID(system_source_id);
        queryRequestBo.setBussinessID(bizID);
//        queryRequestBo.setRoleGroupID("Operaters");
        QueryResponseBo queryResponseBo = queryWorkFlowService.queryProcessIntenceDetail(queryRequestBo);
        System.out.println(queryResponseBo.toString());
        if("000000".equals(queryResponseBo.getRespCode())){
            for (TaskResultBo taskResultBo : queryResponseBo.getTaskResultList()) {
                HashMap<String, Object> activityMap = new HashMap<String, Object>();
                activityMap.put("activityName", taskResultBo.getTaskName());
                activityMap.put("activityAssignee", taskResultBo.getUserID());
                activityMap.put("activityEndTime", taskResultBo.getEndTime());
                activityMap.put("duration", taskResultBo.getDuration());
                activityList.add(activityMap);
            }
        }
        return activityList;
    }

}