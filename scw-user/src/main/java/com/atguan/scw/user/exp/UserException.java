package com.atguan.scw.user.exp;

import com.atguan.scw.user.enums.UserExceptionEnum;

public class UserException extends RuntimeException {

    public UserException() {}


    public UserException(UserExceptionEnum enums) {

        super(enums.getMessage());
    }


}
