package com.jk.api.controller;

import com.jk.api.service.ApiService;
import com.jk.common.annotation.KrtIgnoreAuth;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.service.IOpenUserService;
import com.jk.sys.entity.Organ;
import com.jk.sys.entity.User;
import com.jk.sys.service.IOrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 机构部门相关微信小程序的API接口
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月29日
 */
@RestController
@RequestMapping("api/wechat/kq")
public class OrganApi {

    @Autowired
    private IOpenUserService openUserService;

    @Autowired
    private IOrganService organService;

    @Autowired
    private ApiService apiService;


    /**
     * 查找所有的公司或部门
     *
     * @return
     */
    @KrtIgnoreAuth
    @GetMapping("organ/list")
    public ReturnBean list(HttpServletRequest request)   {
        User openUser = apiService.getUser(request);
//        Position position = positionService.selectById(openUser.getPositionId());
//        Integer grade = position.getGrade();
        List<Organ> organs = new ArrayList<>();
//        if(grade > 0) {
//            if(grade == 1) {
//                Organ organ = organService.selectById(openUser.getOrganId());
//                organs.add(organ);
//            }else if(grade == 2) {
//                //如果该用户的审批等级为2,则为分管领导，获取分管领导的分管公司/部门的所有部门
////                organs = openUserService.getOrganByOpenUserId(openUser.getId());
//            }else {
//                //如果审批等级在3或3以上，则可以查看所有部门的信息
//                organs = organService.selectList();
//            }
//        }
        return ReturnBean.ok();
    }


}
