package com.rishiqing.qywx.service.model.website;

/**
 * @author Wallace Mao
 * Date: 2018-05-12 21:00
 */
public class RegisterCodeVO {
    private String registerCode;
    private Long expiresIn;

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "RegisterCodeVO{" +
                "registerCode='" + registerCode + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
