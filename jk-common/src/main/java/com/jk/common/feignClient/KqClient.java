//package com.jk.common.feignClient;
//
//
//import com.jk.common.bean.ReturnBean;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//
//@FeignClient("kqj")
//public interface KqClient {
//
//
//
//    /**
//     * 增加用户
//     * @param dto
//     * @return
//     */
//    @PostMapping("/device/api/v1/updateUser")
//    public ReturnBean updateUser(@RequestBody  DeviceUserDTO dto);
//
//    /**
//     * 删除用户
//     * @param dto
//     * @return
//     */
//    @PutMapping("/device/api/v1/deleteUser")
//    public ReturnBean deleteUser(@RequestBody DeviceUserDTO dto);
//
//    @PutMapping("/api/v1/updateDept")
//    public ReturnBean updateDept(@RequestBody DeptDTO dto);
//
//    @PutMapping("/api/v1/deleteDept")
//    public ReturnBean deleteDept(@RequestBody DeptDTO dto);
//
//
//}
