package com.jk.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * {{文件描述}}
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年05月20日
 */
public interface DeviceService {

    /**
     * 更新员工信息
     * @param sn     设备序列号
     * @param deptid 部门ID
     * @param card   刷卡卡号
     * @param ccid   员工ID
     * @param name   员工名字
     */
    void updateUser(String sn, String card, String ccid, String name, String deptid);

    /**
     *  更新部门
     * @param sn
     * @param id 部门id
     * @param pid 部门父id
     * @param name
     */
    void updateDept(String sn, String id, String pid, String name);

    void uploadInfo(String sn);

    void deleteUser(String sn, String ccid);

    void deleteDept(String sn, String deptid);

    JSONObject updateConfig(String sn);

    /**
     *  上传广告图
     * @param sn 设备码
     * @param index 排序号
     * @param base64Img base64编码
     */
    void updateImg(String sn, int index, String base64Img);

    /**
     * de
     * @param sn
     * @param index
     */
    void delImg(String sn, int index);
}
