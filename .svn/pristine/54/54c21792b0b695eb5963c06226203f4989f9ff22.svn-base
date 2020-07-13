package com.jk.kq.service;

import com.alibaba.fastjson.JSONObject;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.entity.OpenUser;

/**
 * {{文件描述}}
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年05月20日
 */
public interface DeviceService {



    ReturnBean updateDept(String sn, String id, String pid, String name);

    void uploadInfo(String sn);

    void deleteUser(String sn, String ccid);

    ReturnBean deleteDept(String sn, String deptid);

    JSONObject updateConfig(String sn);

    /**
     *  上传广告图
     * @param sn 设备码
     * @param index 排序号
     * @param base64Img base64编码
     */
    void updateImg(String sn,int index,String base64Img);


    ReturnBean updateUser(OpenUser user);
}
