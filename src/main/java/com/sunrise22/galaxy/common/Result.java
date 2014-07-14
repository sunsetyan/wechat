/**
 * 北京中航嘉城科技股份有限公司(2013)
 */
package com.sunrise22.galaxy.common;

/**
 * 服务的返回结果
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-10 lexloo
 * </pre>
 */
public class Result {
    /**
     * 成功代码
     */
    public static final int SUCCESS = 0;
    /**
     * 失败代码
     */
    public static final int FAILURE = 1;
    /**
     * 返回结果代码
     */
    private int code;
    /**
     * 返回结果
     */
    private Object results;

    /**
     * 成功结果,无消息
     */
    public static final Result SUCCESS_RESULT = new Result();

    /**
     * 构造函数
     */
    public Result() {
        this(SUCCESS, "");
    }

    /**
     * 构造函数，执行成功
     * 
     * @param results 成功结果
     */
    public Result(Object results) {
        this(SUCCESS, results);
    }

    /**
     * 构造函数
     * 
     * @param code 成功或失败代码
     * @param results 成功或失败结果
     */
    public Result(int code, Object results) {
        this.code = code;
        this.results = results;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param results the results to set
     */
    public void setResults(String results) {
        this.results = results;
    }

    /**
     * @return the results
     */
    public Object getResults() {
        return results;
    }

    @Override
    public String toString() {
        return JsonUtils.obj2Json(this);
    }
}
