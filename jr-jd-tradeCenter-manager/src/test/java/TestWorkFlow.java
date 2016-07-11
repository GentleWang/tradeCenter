import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuo7 on 2016/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-config-test.xml")
public class TestWorkFlow {

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

    @Test
    public void test(){
//        repositoryService.createDeployment().addClasspathResource("workFlow/VariablesProcess.bpmn20.xml").deploy();
        List<Execution> processInstanceList =  runtimeService.createExecutionQuery().processDefinitionKey("financeAssets").list();

        for (Execution processInstance:processInstanceList ) {
            System.out.println("ID:"+ processInstance.getId());
            System.out.println("ID:" +processInstance.getProcessInstanceId());
            System.out.println("ID:" +processInstance.getName());
            System.out.println("ID:" +processInstance.getDescription());
            System.out.println("ID:" +processInstance.getActivityId());
            System.out.println("ID:" +processInstance.getId());
            System.out.println("ID:" +processInstance.getId());
        }
//        System.out.println("4");
//        List<Task> inputerTodos  = taskService.createTaskQuery().taskAssignee("inputer1").list();
//        for (Task task:inputerTodos) {
//            System.out.println("taskID："+task.getId());
//            System.out.println("TaskName："+task.getName());
//            System.out.println("TaskOwer："+task.getOwner());
//            System.out.println("TaskAssignee："+task.getAssignee());
//            System.out.println("ParentTaskId："+task.getParentTaskId());
//            System.out.println("ProcessVariables："+task.getProcessVariables().toString());
//            System.out.println("LocalVariables："+task.getTaskLocalVariables().toString());
//            System.out.println("ProcessInstanceId()："+task.getProcessInstanceId());
//            System.out.println("ProcessDefinitionId()："+task.getProcessDefinitionId());
//            System.out.println("Description()："+task.getDescription());
////            HashMap<String,Object> variables = new HashMap<String,Object>();
////            variables.put("resendRequest","false");
////            variables.put("notices","不录入了");
////            taskService.complete(task.getId(),variables);
//        }
//        taskService.addCandidateGroup("14","inputers");


    }

    // 历史活动查看(某一次流程的执行经历的多少步)
    @Test
    public void queryHistoricActivityInstance() throws Exception {
        String processInstanceId = "1401";
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery()
                // 过滤条件
                .taskAssignee("inputer1").finished()
                // 排序条件
                .orderByHistoricActivityInstanceEndTime().asc()
                // 执行查询
                .list();
        for (HistoricActivityInstance hai : hais) {
            System.out.print("activitiId:" + hai.getActivityId()+"，");
            System.out.print("name:" + hai.getActivityName()+"，");
            System.out.print("type:" + hai.getActivityType()+"，");
            System.out.print("pid:" + hai.getProcessInstanceId()+"，");
            System.out.print("assignee:" + hai.getAssignee()+"，");
            System.out.print("startTime:" + hai.getStartTime()+"，");
            System.out.print("endTime:" + hai.getEndTime()+"，");
            System.out.println("duration:" + hai.getDurationInMillis());
            System.out.println("ActivityType:" + hai.getActivityType());
        }
    }

    // 历史流程实例查看（查找按照某个规则一共执行了多少次流程）
    @Test
    public void queryHistoricProcessInstance() throws Exception {
        // 获取历史流程实例的查询对象
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        // 设置查询参数
        historicProcessInstanceQuery
                //过滤条件
                .processDefinitionKey("financeAssets")
                // 分页条件
//     .listPage(firstResult, maxResults)
                // 排序条件
                .orderByProcessInstanceStartTime().desc();
        // 执行查询
        List<HistoricProcessInstance> hpis = historicProcessInstanceQuery.list();
        // 遍历查看结果
        for (HistoricProcessInstance hpi : hpis) {
            System.out.print("pid:" + hpi.getId()+",");
            System.out.print("pdid:" + hpi.getProcessDefinitionId()+",");
            System.out.print("startTime:" + hpi.getStartTime()+",");
            System.out.print("endTime:" + hpi.getEndTime()+",");
            System.out.print("duration:" + hpi.getDurationInMillis()+",");
            System.out.println("vars:" + hpi.getProcessVariables());
        }

    }

    // 历史流程实例查看（查找按照某个规则一共执行了多少次流程）
    @Test
    public void queryHistoricTaskInstance() throws Exception {
        // 获取历史流程实例的查询对象
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        // 设置查询参数
        historicTaskInstanceQuery
                //过滤条件
//                .processDefinitionKey("financeAssets")
                .processDefinitionKey("5017")
                // 分页条件
//     .listPage(firstResult, maxResults)
                // 排序条件
                .orderByHistoricTaskInstanceEndTime().desc();
        // 执行查询
        List<HistoricTaskInstance> hpis = historicTaskInstanceQuery.list();
        // 遍历查看结果
        for (HistoricTaskInstance hpi : hpis) {
            System.out.print("LocalVariables:" + hpi.getTaskLocalVariables()+",");
            System.out.print("Description:" + hpi.getDescription()+",");
            System.out.print("Assignee:" + hpi.getAssignee()+",");
            System.out.print("pdid:" + hpi.getProcessDefinitionId()+",");
            System.out.print("startTime:" + hpi.getStartTime()+",");
            System.out.print("endTime:" + hpi.getEndTime()+",");
            System.out.print("duration:" + hpi.getDurationInMillis()+",");
            System.out.println("vars:" + hpi.getProcessVariables());
        }

    }

    @Test
    public void viewVar() throws Exception {
        String processInstanceId = "1901";
        Task task =taskService.createTaskQuery().taskId("12510").singleResult();
        System.out.println("taskName:" + task.getName());
//        String variableName = "请假人";
//        String val = (String)taskService.getVariable(task.getId(), variableName );
        Map<String,Object> vars = taskService.getVariables(task.getId());
        for (String variableName : vars.keySet()) {
            String val = (String) vars.get(variableName);
            System.out.println(variableName + " = " +val);
        }
    }


    @Test
    public  void setProcessIniter(){
        System.out.println("开始录入资产");
        repositoryService.createDeployment().addClasspathResource("workFlow/VariablesProcess.bpmn20.xml").deploy();
//        String processID =  runtimeService.startProcessInstanceByKey("financeAssets").getId();
//        System.out.println("新建流程ID"+processID);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("ipnuterGroup", "inputer1");
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financeAssets", variables);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId("financeAssets", variables,userID);

        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        System.out.println("ProcessInstance: " + processInstance.getId());
        Map<String, Object> initVariables = new HashMap<String, Object>();
        Task inputTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("inputer1").singleResult();
        System.out.println(inputTask.getId());
        Map<String, Object> inputVariables = new HashMap<String, Object>();
        inputVariables.put("ipnuterGroup", "inputer1");
        inputVariables.put("assetID", 1111);
        inputVariables.put("notices", "inputer1"+" 录入虚拟资产");
        taskService.complete(inputTask.getId(),inputVariables);
    }
}
