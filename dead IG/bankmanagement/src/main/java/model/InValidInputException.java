package model;

class InValidInputException extends Exception {
    String str;

    InValidInputException(String str) {
        super(str);
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}