package com.sngular.omb.ombapi.util;

public class RequestValidator {
    public static void hasEnoughBalanceForWithdraw(double currentAmount, double withdrawAmount) {
        if (currentAmount < withdrawAmount) {
            throw new IllegalArgumentException("Withdraw amount cannot be greater than current amount");
        }
    }
}
