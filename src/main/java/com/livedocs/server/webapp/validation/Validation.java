package com.livedocs.server.webapp.validation;

import java.beans.JavaBean;

import com.livedocs.server.webapp.entity.Users;
import java.util.*;

@JavaBean
public class Validation {
    public boolean validateRequestBody(Users user) {
        if (Objects.isNull(user.getEmail()) || Objects.isNull(user.getPassword()) || user.getEmail().isEmpty()
                || user.getPassword().isEmpty())
            return true;
        return false;
    }
}
