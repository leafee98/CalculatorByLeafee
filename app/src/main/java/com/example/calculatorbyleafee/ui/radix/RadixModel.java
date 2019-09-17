package com.example.calculatorbyleafee.ui.radix;

class RadixModel {

    private int value;

    RadixModel() {
        this.value = 0;
    }

    void setDec(String val) {
        if (val.equals("")) {
            this.value = 0;
        } else {
            this.value = Integer.parseInt(val);
        }
    }

    void setBin(String val) {
        if (val.equals("")) {
            this.value = 0;
        } else {
            this.value = Integer.parseInt(val, 2);
        }
    }

    void setOct(String val) {
        if (val.equals("")) {
            this.value = 0;
        } else {
            this.value = Integer.parseInt(val, 8);
        }
    }

    void setHex(String val) {
        if (val.equals("")) {
            this.value = 0;
        } else {
            this.value = Integer.parseInt(val, 16);
        }
    }

    String getBin() {
        return Integer.toBinaryString(this.value);
    }

    String getOct() {
        return Integer.toOctalString(this.value);
    }

    String getDec() {
        return Integer.toString(this.value);
    }

    String getHex() {
        return Integer.toHexString(this.value).toUpperCase();
    }
}
