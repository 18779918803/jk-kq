package com.jk.common.exception;

import com.jk.common.bean.ReturnBean;

/**
 * 自定义异常
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public class KrtException extends RuntimeException {


    protected ReturnBean returnBean;

    public KrtException(ReturnBean returnBean) {
        super(returnBean.getMsg());
        this.returnBean = returnBean;
    }

    public KrtException(String msg, Throwable e) {
        super(msg, e);
        this.returnBean = ReturnBean.error(msg);
    }

    public KrtException(ReturnBean returnBean, Throwable e) {
        super(returnBean.getMsg(), e);
        this.returnBean = returnBean;
    }

    public ReturnBean getReturnBean() {
        return returnBean;
    }

    public void setReturnBean(ReturnBean returnBean) {
        this.returnBean = returnBean;
    }
}
