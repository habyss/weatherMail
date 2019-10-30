package com.test.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kun.han on 2019/10/17 16:29
 */
public class Test implements Serializable {
    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS z")
    // @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
    @NotNull
    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
