package com.jk.framework.tag;

import com.jk.common.bean.ReturnBean;
import com.jk.common.constant.GlobalConstant;
import com.jk.common.exception.KrtException;
import com.jk.sys.entity.Dic;
import com.jk.sys.service.IDicService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 字典标签
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年06月29日
 */
@Component
public class DicTag implements TemplateDirectiveModel {

    @Autowired
    private IDicService dicService;

    /**
     * 字典类型
     */
    private final String TYPE = "type";

    /**
     * 字典父id
     */
    private final String PID = "pid";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String type;
        Integer pid;
        if (params.containsKey(TYPE)) {
            type = params.get(TYPE).toString();
        } else {
            throw new KrtException(ReturnBean.error("字典类型不能为空"));
        }
        if (params.containsKey(PID)) {
            pid = Integer.valueOf(params.get(PID).toString());
        } else {
            pid = GlobalConstant.DEFAULT_PID;
        }
        List<Dic> dicList = dicService.selectByTypeAndPid(type, pid);
        loopVars[0] = new SimpleSequence(dicList, env.getObjectWrapper());
        body.render(env.getOut());
    }
}
