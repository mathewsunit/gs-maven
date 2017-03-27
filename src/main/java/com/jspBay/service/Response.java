package com.jspBay.service;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunit on 3/21/17.
 */
@Getter
@Setter
public class Response<T> {
    private T object;
    private Status status;

    public Response(T object, Status status) {
        this.object = object;
        this.status = status;
    }

    public enum Status {
        CREATED,
        NOT_CREATED,
        RECEIVED,
        NOT_RECEIVED
    }

}
