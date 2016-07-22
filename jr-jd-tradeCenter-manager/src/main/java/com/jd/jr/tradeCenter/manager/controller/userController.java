package com.jd.jr.tradeCenter.manager.controller;

import com.jd.jr.tradeCenter.manager.enums.UserEnum;
import com.jd.jr.tradeCenter.manager.workflow.WorkFlowRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuo7 on 2016/7/1.
 */
@Controller
@RequestMapping("/user")
public class userController {

    @Autowired
    private WorkFlowRun workFlowRun;

    @RequestMapping(value ="/loginView", method = { RequestMethod.POST, RequestMethod.GET })
    public String loginView(HttpServletRequest request, Model model){
        System.out.println("用户登录");
        return "login";
    }

    @RequestMapping(value ="/login", method = { RequestMethod.POST, RequestMethod.GET })
    public String login(@RequestParam(value = "userID", required = false)String userID,HttpServletRequest request, Model model){
        System.out.println("用户："+userID+"正在登录");
        try {
            UserEnum user = UserEnum.valueOf(userID);
            model.addAttribute("userID",user.getUserID());
            model.addAttribute("userName",user.getUserName());
            model.addAttribute("roles",user.getRoles());
            model.addAttribute("rolesGroup",user.getRolesGroup());
            System.out.println("用户："+user.getUserName()+"登录成功");
            List<Map<String,Object>> taskList = workFlowRun.queryMyToDo(user.getUserID(),user.getRolesGroup(),"financeAssets1");
            List<Map<String,Object>> doneList = workFlowRun.queryMyHadDone(user.getUserID(),"financeAssets1");
            for (Map task:taskList) {
                System.out.println("用户："+user.getUserName()+"的待办："+task.toString());
            }
            model.addAttribute("taskList",taskList);
            model.addAttribute("doneList",doneList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "userView";
    }
    @RequestMapping(value ="/auditAgree", method = { RequestMethod.POST, RequestMethod.GET })
    public String  operatorsAuditAgree(@RequestParam(value = "userID", required = false)String userID,@RequestParam(value = "processInstanceID", required = true)String processInstanceID,HttpServletRequest request, Model model){
        System.out.println("用户："+userID+"正在审核，业务单号是："+processInstanceID);
        HashMap<String,Object> variables = new HashMap<String, Object>();
        variables.put("AuditApproved","AGREE");
        variables.put("resendRequest","AGREE");
        variables.put("notices","同意");
        String roleGroup = UserEnum.valueOf(userID).getRolesGroup();
        workFlowRun.operate(processInstanceID,variables,userID,"financeAssets1",roleGroup);
        return "login";
    }
    @RequestMapping(value ="/auditRefuse", method = { RequestMethod.POST, RequestMethod.GET })
    public String operatorsAuditRefuse(@RequestParam(value = "userID", required = false)String userID,@RequestParam(value = "processInstanceID", required = true)String processInstanceID,HttpServletRequest request, Model model){
        System.out.println("用户："+userID+"正在审核，任务ID是："+processInstanceID);
        HashMap<String,Object> variables = new HashMap<String, Object>();
        variables.put("AuditApproved","REFUSE");
        variables.put("resendRequest","REFUSE");
        variables.put("notices","不同意");
        String roleGroup = UserEnum.valueOf(userID).getRolesGroup();
        workFlowRun.operate(processInstanceID,variables,userID,"financeAssets1",roleGroup);
        return "login";
    }

    @RequestMapping(value ="/queryActivityDetail", method = { RequestMethod.POST, RequestMethod.GET })
    public String queryActivityDetail(@RequestParam(value = "processInstanceID")String processInstanceID,HttpServletRequest request,Model model){
        System.out.println("正在查询任务："+processInstanceID+"的详细流程：");
        List list = workFlowRun.queryProcessIntenceDetail(processInstanceID,"financeAssets1");
        model.addAttribute("activityDetails",list);
        return "processInstanceDetail";
    }
}
