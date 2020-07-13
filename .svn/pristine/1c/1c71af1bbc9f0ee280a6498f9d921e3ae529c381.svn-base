package com.jk.sys.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jk.sys.entity.DeviceCmd;
import com.jk.sys.service.DeviceCmdService;
import com.jk.sys.service.DeviceService;
import com.jk.sys.util.MessageIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * {{文件描述}}
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年05月20日
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceCmdService deviceCmdService;

    /**
     * 更新员工信息
     *
     * @param sn     设备序列号
     * @param deptid 部门ID
     * @param card   刷卡卡号
     * @param ccid   员工ID
     * @param name   员工名字
     */
    @Override
    @Transactional
    public void updateUser(String sn, String card, String ccid, String name, String deptid) {
        JSONObject data = new JSONObject();
        String messageId = MessageIdUtil.messageId();
        data.put("id", String.valueOf(messageId));
        data.put("do", "update");
        data.put("data", "user");
        data.put("ccid", ccid);
        data.put("name", name);
        data.put("passwd", "md5");
        data.put("card", card);
        data.put("deptid", deptid);
        data.put("auth", 0);
        queueCmd(sn, messageId, data);
    }

    /**
     * 更新部门信息
     *
     * @param sn   设备序列号
     * @param id   部门ID
     * @param pid  部门父ID
     * @param name 部门名称
     */
    @Override
    @Transactional
    public void updateDept(String sn, String id, String pid, String name) {
        JSONObject data = new JSONObject();
        String messageId = MessageIdUtil.messageId();
        data.put("id", String.valueOf(messageId));
        data.put("do", "update");
        data.put("data", "dept");
        JSONArray dept = new JSONArray();
        JSONObject d = new JSONObject();
        d.put("id", id);
        d.put("pid", pid);
        d.put("name", name);
        dept.add(d);
        data.put("dept", dept);
        queueCmd(sn, messageId, data);
    }

    /**
     * 上传设备信息
     *
     * @param sn
     */
    @Override
    public void uploadInfo(String sn) {
        JSONObject data = new JSONObject();
        String messageId = MessageIdUtil.messageId();
        data.put("id", String.valueOf(messageId));
        data.put("do", "upload");
        data.put("data", "info");
        queueCmd(sn, messageId, data);
    }

    /**
     * 删除用户信息
     *
     * @param sn
     * @param ccid
     */
    @Override
    public void deleteUser(String sn, String ccid) {
        JSONObject data = new JSONObject();
        String messageId = MessageIdUtil.messageId();
        data.put("id", String.valueOf(messageId));
        data.put("do", "delete");
        JSONArray d = new JSONArray();
        d.add("user");
        d.add("fingerprint");
        d.add("face");
        d.add("headpic");
        d.add("clockin");
        d.add("pic");
        data.put("data", d);
        JSONArray id = new JSONArray();
        id.add(ccid);
        data.put("ccid", id);
        queueCmd(sn, messageId, data);
    }

    /**
     * 删除部门信息
     *
     * @param sn
     * @param deptid
     */
    @Override
    public void deleteDept(String sn, String deptid) {
        JSONObject data = new JSONObject();
        String messageId = MessageIdUtil.messageId();
        data.put("id", String.valueOf(messageId));
        data.put("do", "delete");
        data.put("data", "dept");
        JSONArray id = new JSONArray();
        id.add(deptid);
        data.put("deptid", id);
        queueCmd(sn, messageId, data);
    }

    @Override
    public JSONObject updateConfig(String sn) {
        JSONObject data = new JSONObject();
        data.put("id", "0");
        data.put("do", "update");
        data.put("data", "config");
        data.put("name", "建控大堂");
        data.put("company", "赣州建控集团");
        data.put("companyid", 1);
        data.put("max", 3000);
        data.put("function", 65532);
        data.put("delay", 20);
        data.put("errdelay", 30);
        data.put("timezone", "GMT+08:00");
        data.put("encrypt", 0);
        data.put("expired", "2015-12-1012:10:10");
        return data;
    }

    @Override
    public void updateImg(String sn, int index, String base64Img) {
        JSONObject data = new JSONObject();
        String messageId = MessageIdUtil.messageId();
        data.put("id", String.valueOf(messageId));
        data.put("do", "update");
        data.put("data", "advert");
        data.put("index", index);
        data.put("advert", base64Img);
        queueCmd(sn, messageId, data);
    }

    @Override
    public void delImg(String sn, int index) {
        JSONObject data = new JSONObject();
        String messageId = MessageIdUtil.messageId();
        data.put("id", String.valueOf(messageId));
        data.put("do", "delete");
        data.put("data", "advert");
        data.put("index", index);
        queueCmd(sn, messageId, data);
    }

    private void queueCmd(String sn, String messageId, JSONObject data) {
        DeviceCmd deviceCmd = new DeviceCmd();
        deviceCmd.setSn(sn);
        deviceCmd.setMsgId(messageId);
        deviceCmd.setStatus(0);
        deviceCmd.setCommand(data.toString());
        deviceCmd.setCreateTime(new Date());
        deviceCmdService.insert(deviceCmd);
    }




}
