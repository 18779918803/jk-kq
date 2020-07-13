package com.jk.api.controller;

import com.jk.api.service.ApiService;
import com.jk.common.annotation.KrtIgnoreAuth;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.service.IOndutyRecordService;
import com.jk.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 值班记录的微信API接口
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月02日
 */
@RestController
@RequestMapping("api/wechat/kq")
public class OndutyRecordApi {


    @Autowired
    private IOndutyRecordService ondutyRecordService;

    @Autowired
    private ApiService apiService;

    /**
     * 获取所有的出差记录，可以按照月份查找
     *
     * @param request
     * @param date
     * @return
     */
    @KrtIgnoreAuth
    @GetMapping("onduty/list")
    public ReturnBean list(HttpServletRequest request, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date)   {
        User openUser = apiService.getUser(request);
//        List<OndutyRecord> ondutyRecords = ondutyRecordService.selectByOpenUserIdAndDate(openUser.getId(), date);
        return ReturnBean.ok();
    }





}
