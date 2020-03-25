package com.yaochen.tester.day01;

import java.lang.ref.PhantomReference;

public class RecasePr {
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RecasePr{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public RecasePr(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
