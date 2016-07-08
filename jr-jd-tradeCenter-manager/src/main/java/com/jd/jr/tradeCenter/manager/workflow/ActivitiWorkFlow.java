package com.jd.jr.tradeCenter.manager.workflow;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * Created by wangshuo7 on 2016/7/6.
 */
public class ActivitiWorkFlow {


    public ProcessEngine workFlowDeploy(String bpmpPath){
        // 创建工作流引擎
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
//                .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/tradecenter?useUnicode=true&characterEncoding=UTF-8")
//                .setJdbcDriver("org.h2.Driver")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setJdbcUsername("root")
                .setJdbcPassword("shuo")
                .setAsyncExecutorEnabled(true)
                .setAsyncExecutorActivate(false)
                .buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //部署工作流
        repositoryService.createDeployment().addClasspathResource(bpmpPath).deploy();
//
        return processEngine;
    }


    public static void main(String[] args) {

        // Create Activiti process engine
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
//                .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/tradecenter?useUnicode=true&characterEncoding=UTF-8")
//                .setJdbcDriver("org.h2.Driver")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setJdbcUsername("root")
                .setJdbcPassword("shuo")
                .setAsyncExecutorEnabled(true)
                .setAsyncExecutorActivate(false)
                .buildProcessEngine();
        // Get Activiti services
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
//
        // Deploy the process definition
//        repositoryService.createDeployment()
//                .addClasspathResource("workFlow/FinancialReportProcess.bpmn20.xml")
//                .deploy();
//
//        // Start a process instance
//        String procressID = runtimeService.startProcessInstanceByKey("tradeCenterProcess").getId();
//        System.out.println("流程ID："+procressID);
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("转让方").list();
        for (Task task:tasks) {
            System.out.println("转让方任务待办："+task.getName()+task.getId());
            taskService.claim(task.getId(),"登记员");
        }
//

        List<Task> taskDengjiyuan = taskService.createTaskQuery().taskAssignee("登记员").list();
        for (Task task:taskDengjiyuan) {
            System.out.println("登记员的待办："+task.getName()+task.getId());
            System.out.println("***登记员录入虚拟资产****");
            taskService.complete(task.getId());
            System.out.println("***登记员录入虚拟资产END****");
        }

        List<Task> taskDengjiyuan1 = taskService.createTaskQuery().taskCandidateGroup("交易所运营").list();
        for (Task task:taskDengjiyuan1) {
            System.out.println("交易所运营的待办："+task.getName()+task.getId());
            if("50002".equals(task.getId())){
                taskService.claim(task.getId(),"交易所运营");
            }
        }

        List<Task> yunying = taskService.createTaskQuery().taskAssignee("交易所运营").list();
        for (Task task:yunying) {
            System.out.println("交易所运营的待办："+task.getName()+task.getId());
            System.out.println("***交易所运营初审****");
            taskService.complete(task.getId());
            System.out.println("***交易所运营初审****");
        }

        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId("45001").singleResult();
        System.out.println("\n流程结束时间："+historicProcessInstance.getEndTime());
        System.out.println("\nuserID ："+historicProcessInstance.getStartUserId());

    }
}
