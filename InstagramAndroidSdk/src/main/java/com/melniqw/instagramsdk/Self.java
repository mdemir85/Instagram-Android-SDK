package com.melniqw.instagramsdk;

import java.io.Serializable;

public class Self extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    public String accessToken = "";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append("<accessToken>" + accessToken + "</accessToken>" + "\n");
        return  builder.toString();
    }
}
