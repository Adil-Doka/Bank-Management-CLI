package com.myfirstproject;

class NumberNotValidException extends Exception {
    String str;

    NumberNotValidException(String str) {
        super(str);
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}