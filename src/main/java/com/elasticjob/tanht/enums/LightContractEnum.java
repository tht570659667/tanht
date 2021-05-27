package com.elasticjob.tanht.enums;

/**
 * @Author:Tanht
 * @Description: 轻合约权益办理常量枚举
 * @Date:2:22 PM 10/20/2020
 */
public enum LightContractEnum {
    /**
     * 未登录返回信息
     */
    NO_LOGIN("-100", "请您先登录"),

    /**
     * 已办理返回信息
     */
    IS_HANDLE("-101", "您已经领取过重庆移动充值权益"),

    /**
     * 未办理返回信息
     */
    NO_HANDLE("-102", "您还未领取重庆移动充值权益"),

    /**
     * 办理失败返回信息
     */
    HANDLE_FAIL("-99", "sorry！您暂时不满足领取条件"),

    /**
     * 办理成功返回信息
     */
    HANDLE_SUCCESS("200", "恭喜您！成功领取充值权益"),

    /**
     * 未获得redis锁返回信息
     */
    NO_TRY_LOCK("-1000", "系统繁忙，请稍后再试"),

    /**
     * 轻合约权益nCode
     */
    LIGHT_CONTRACT_CODE("gl_cqydczqy_jfjz"),

    /**
     * 用户操作redis key
     */
    USER_LOCK_KEY("lightContract-"),

    /**
     * 交费页面的判断是否有劵的缓存 redis key
     */
    APP_CHARGE_BOSS_EX_COUPON("APP_CHARGE_BOSS_EX_COUPON_"),

    /**
     * 以下是常量字符串或整型常量定义
     */
    ADD("ADD"),
    X_RESULT_CODE_STR("X_RESULTCODE"),
    RES_CODE("resCode"),
    HUNDRED_STR("100"),
    ZERO_STR("0"),
    TWO_STR("2"),
    ONE_STR("1"),
    MINUS_ONE("-1"),
    TWENTY_INT(20),
    USER_HANDLE_INFO("userHandleInfo-"),
    QUERY_INTERFACE_NAME("100006"),
    X_RETURN_RESULT("X_RETURNRESULT"),
    ITEM_ID("ITEMID"),
    TEL_NUM("TELNUM"),
    NCODE("NCODE"),
    EFFTYPE("EFFTYPE"),
    EFFTYPE_TYPE("2"),
    ONE_HOUR(3600),
    OMO_HANDLE_NOTE("充值加赠5%轻合约办理"),
    LIGHT_CONTRACT_BUSINESS_NAME("lightContract"),
    LIGHT_CONTRACT_GOODS_FEE("充值加赠5%轻合约");
    private String code;
    private String value;
    private int intValue;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    LightContractEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    LightContractEnum(String code) {
        this.code = code;
    }

    LightContractEnum(int intValue) {
        this.intValue = intValue;
    }
}
