package com.jd.jr.tradeCenter.manager.controller;

import com.jd.jr.tradeCenter.manager.biz.impl.FinanceAssetsBizImpl;
import com.jd.jr.tradeCenter.manager.enums.FinanceAssetsStatusEnum;
import com.jd.jr.tradeCenter.manager.workflow.WorkFlowRun;
import com.jd.jr.tradeCenter.model.FinanceAssets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

/**
 * Created by wangshuo7 on 2016/7/1.
 */
@Controller
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private FinanceAssetsBizImpl financeAssetsBiz;
    @Autowired
    private WorkFlowRun workFlowRun;

    @RequestMapping(value ="/assetInput", method = { RequestMethod.POST, RequestMethod.GET })
    public String AssetInput(){
        return "assetInput";
    }

    @RequestMapping(value ="/assetInputSubmit", method = { RequestMethod.POST, RequestMethod.GET })
    public String AssetInputSubmit(@RequestParam(value = "userID", required = false)String userID,FinanceAssets financeAssets, HttpServletRequest request, Model model){
        System.out.println("financeAssets = [" + financeAssets.toString() + "], request = [" + request.getCharacterEncoding() + "]");
        try {
            Random random = new Random();
            String id = random.nextInt(100000)+"";
            System.out.println(id);
            financeAssets.setStatus(FinanceAssetsStatusEnum.INIT.getCode());
            financeAssets.setId(id);
            financeAssets.setCrateDate(new Date());
            financeAssets.setUpdateDate(new Date());
            financeAssetsBiz.assetInput(financeAssets);
            model.addAttribute("assetsData",financeAssets);
            model.addAttribute("userID",userID);
            workFlowRun.startEnvent(id,userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "assetList";
    }

    @RequestMapping(value ="/assetAuditSubmit", method = { RequestMethod.POST, RequestMethod.GET })
    public String AssetAuditSubmit(@RequestParam(value = "userID", required = false)String userID,FinanceAssets financeAssets, HttpServletRequest request, Model model){
        System.out.println("financeAssets = [" + financeAssets.toString() + "], request = [" + request.getCharacterEncoding() + "]");
        try {
            financeAssets.setStatus(FinanceAssetsStatusEnum.INIT.getCode());
            financeAssets.setId("20");
            financeAssets.setCrateDate(new Date());
            financeAssets.setUpdateDate(new Date());
            financeAssetsBiz.assetInput(financeAssets);
            model.addAttribute("assetsData",financeAssets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "assetList";
    }
}
