package com.my.firstproject;

class AccNoDoesntExistException extends Exception {

    AccNoDoesntExistException(String str) {
        super(str);
    }
}