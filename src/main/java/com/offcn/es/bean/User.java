package com.offcn.es.bean;

import lombok.Data;
/**
 * userRepository操作的bean
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String address;
    private Integer sex;
}
