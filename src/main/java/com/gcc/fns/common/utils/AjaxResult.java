package com.gcc.fns.common.utils;

import lombok.Data;

/**
 * 封装结果返回集
 * @author snail
 * @create 2022-10-30  16:32
 */
@Data
public class AjaxResult<T> {

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 成功与否
     */
    private Boolean success;

    /**
     * 消息提示
     */
    private String msg;

    /**
     * 错误描述
     */
    private String errDesc;

//    /**
//     * 用户token
//     */
//    private String token;

    public AjaxResult() {
    }



    /**
     *操作失败
     * @param errDesc
     * @return AjaxResult<?>
     * @author Snail
     * @date 2022/10/30 16:48
     */

    public static AjaxResult<?> failure(String errDesc) {
        AjaxResult<Object> objectAjaxResult = new AjaxResult<>();
        objectAjaxResult.setErrDesc(errDesc);
        objectAjaxResult.setSuccess(false);
        objectAjaxResult.setCode(-1);
        return objectAjaxResult;
    }

    /**
     *操作成功
     * @param msg   返回消息
     * @param total 总条数
     * @param data 返回的数据
     * @return AjaxResult<T>
     * @author Snail
     * @date 2022/10/30 16:49
     */
    public static <T> AjaxResult<T> success(String msg,long total,T data){
        AjaxResult<T> result = new AjaxResult<>();
        result.setSuccess(true);
        result.setTotal(total);
        result.setMsg(msg);
        return result;
    }


    /**
     * 操作成功
     * @param total 总条数
     * @param data 返回的数据
     * @return AjaxResult<T>
     * @author Snail
     * @date 2022/10/30 16:50
     */
    public static <T> AjaxResult<T> success(T data,long total){
        AjaxResult<T> result = new AjaxResult<>();
        result.setSuccess(true);
        result.setTotal(total);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 操作成功
     * @param data 返回的数据
     * @author Snail
     * @date 2022/10/30 16:50
     */
    public static <T> AjaxResult<T> success(T data){
        AjaxResult<T> result = new AjaxResult<>();
        result.setSuccess(true);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> AjaxResult<T> success(T data,String url){
        AjaxResult<T> result = new AjaxResult<>();
        result.setSuccess(true);
        result.setMsg("操作成功");
        result.setData(data);
        result.setData(data);
        return result;
    }


    /**
     * 操作成功
     * @param msg  返回消息
     * @return AjaxResult<T>
     * @author Snail
     * @date 2022/10/30 16:51
     */
    public static <T> AjaxResult<T> success(String msg){
        return success(msg,0,null);
    }

    /**
     *
     * @param msg 返回消息
     * @param total
     * @return AjaxResult<T>
     * @author Snail
     * @date 2022/10/30 16:51
     */


    public static <T> AjaxResult<T> success(String msg,Integer total){
        return success(msg,total,null);
    }

    /**
     *操作成功
     * @param
     * @return AjaxResult<T>
     * @author Snail
     * @date 2022/10/30 16:52
     */


    public static <T> AjaxResult<T> success(){
        return success("操作成功",0,null);
    }

}
