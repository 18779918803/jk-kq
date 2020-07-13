package com.jk.kq.service;

import com.jk.common.base.IBaseService;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.entity.JkDeviceAds;
import com.jk.kq.entity.dto.JkDeviceAdsDTO;

import java.io.IOException;


/**
 * 广告位服务接口层
 *
 * @author 张威伦
 * @version 1.0
 * @date 2020年06月12日
 */
public interface IJkDeviceAdsService extends IBaseService<JkDeviceAds> {


    /**
     * 插入广告图，并推送到考勤机
     * @param dto 传输对象
     * @return 前端对象
     */
    ReturnBean insertAndCmd(JkDeviceAdsDTO dto) throws IOException;


    ReturnBean delAndSend(JkDeviceAds dto) ;

}
