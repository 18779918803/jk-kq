package com.jk.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.ReturnBean;
import com.jk.common.constant.GlobalConstant;
import com.jk.common.validator.Assert;
import com.jk.common.validator.ValidatorUtils;
import com.jk.common.validator.group.InsertGroup;
import com.jk.common.validator.group.UpdateGroup;
import com.jk.sys.entity.Organ;
import com.jk.sys.entity.Region;
import com.jk.sys.service.DeviceService;
import com.jk.sys.service.IOrganService;
import com.jk.sys.service.IRegionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 组织机构控制层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Slf4j
@Controller
public class OrganController extends BaseController {

    @Autowired
    private IOrganService organService;

    @Autowired
    private IRegionService regionService;

    @Autowired
    private DeviceService deviceService;
//    @Autowired
//    private KqClient kqClient;

    /**
     * 组织机构管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("sys:organ:list")
    @GetMapping("sys/organ/list")
    public String list() {
        return "sys/organ/list";
    }

    /**
     * 组织机构管理
     *
     * @param pid 父id
     * @return {@link List}
     */
//    @RequiresPermissions("sys:organ:list")
    @PostMapping("sys/organ/list")
    @ResponseBody
    public ReturnBean list(Integer pid) {
        List list = organService.selectByPid(pid);
        return ReturnBean.ok(list);
    }

    /**
     * 新增组织机构页
     *
     * @param pid 父id
     * @return {@link String}
     */
    @RequiresPermissions("sys:organ:insert")
    @GetMapping("sys/organ/insert")
    public String insert(Integer pid) {
        if (pid != null) {
            Organ pOrgan = organService.selectById(pid);
            request.setAttribute("pOrgan", pOrgan);
        }
        return "sys/organ/insert";
    }

    /**
     * 添加组织机构
     *
     * @param organ 组织机构
     * @return {@link ReturnBean}
     */
    @KrtLog("添加组织机构")
    @RequiresPermissions("sys:organ:insert")
    @PostMapping("sys/organ/insert")
    @ResponseBody
    public ReturnBean insert(Organ organ) {
        ValidatorUtils.validateEntity(organ, InsertGroup.class);
        organService.insert(organ);
//        kqClient.updateDept(
//                DeptDTO.builder().sn("Q10193310233").id(organ.getId().toString())
//                        .pid(organ.getPid().toString()).name(organ.getName()).build()
//        );
        deviceService.updateDept("Q10193310233", organ.getId().toString(), organ.getPid().toString(), organ.getName());
        return ReturnBean.ok();
    }

    /**
     * 修改组织机构页
     *
     * @param id 组织机构 id
     * @return {@link String}
     */
    @RequiresPermissions("sys:organ:update")
    @GetMapping("sys/organ/update")
    public String update(Integer id) {
        Organ organ = organService.selectById(id);
        request.setAttribute("organ", organ);
        Integer pid = organ.getPid();
        if (!GlobalConstant.DEFAULT_PID.equals(pid)) {
            Organ pOrgan = organService.selectById(pid);
            request.setAttribute("pOrgan", pOrgan);
        }
        Integer regionId = organ.getRegionId();
        if (!Assert.isNull(regionId)) {
            Region region = regionService.selectById(regionId);
            request.setAttribute("region", region);
        }
        return "sys/organ/update";
    }

    /**
     * 修改组织机构
     *
     * @param organ 组织机构
     * @return {@link ReturnBean}
     */
    @KrtLog("修改组织机构")
    @RequiresPermissions("sys:organ:update")
    @PostMapping("sys/organ/update")
    @ResponseBody
    public ReturnBean update(Organ organ) {
        ValidatorUtils.validateEntity(organ, UpdateGroup.class);
        organService.updateById(organ);
//        kqClient.updateDept(
//                DeptDTO.builder().sn("Q10193310233").id(organ.getId().toString())
//                        .pid(organ.getPid().toString()).name(organ.getName()).build()
//        );
        deviceService.updateDept("Q10193310233", organ.getId().toString(), organ.getPid().toString(), organ.getName());

        return ReturnBean.ok();

    }

    /**
     * 删除组织机构
     *
     * @param id 组织机构 id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除组织机构")
    @RequiresPermissions("sys:organ:delete")
    @PostMapping("sys/organ/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        organService.deleteById(id);
//        kqClient.deleteDept(
//                DeptDTO.builder().sn("Q10193310233").id(id.toString()).build()
//        );
        deviceService.deleteDept("Q10193310233", id.toString());

        return ReturnBean.ok();


    }

    /**
     * 组织机构树
     *
     * @return {@link ReturnBean}
     */
    @PostMapping("sys/organ/organTree")
    @ResponseBody
    public ReturnBean organTree() {
        List list = organService.selectList();
        return ReturnBean.ok(list);
    }

    /**
     * 检查机构代码是否重复
     *
     * @param id   机构id
     * @param code 机构编码
     * @return {@link Boolean}
     */
    @PostMapping("sys/organ/checkCode")
    @ResponseBody
    public Boolean checkCode(Integer id, String code) {
        LambdaQueryWrapper<Organ> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Organ::getCode, code);
        queryWrapper.ne(!Assert.isNull(id), Organ::getId, id);
        Integer count = organService.selectCount(queryWrapper);
        return count == 0;
    }

    /**
     * 修改组织机构页
     *
     * @param id 组织机构 id
     * @return {@link String}
     */
    @RequiresPermissions("sys:organ:see")
    @GetMapping("sys/organ/see")
    public String see(Integer id) {
        Organ organ = organService.selectById(id);
        request.setAttribute("organ", organ);
        Integer regionId = organ.getRegionId();
        if (!Assert.isNull(regionId)) {
            Region region = regionService.selectById(regionId);
            request.setAttribute("region", region);
        }
        return "sys/organ/see";
    }
}
