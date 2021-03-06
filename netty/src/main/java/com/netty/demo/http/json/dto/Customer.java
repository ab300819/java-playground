package com.netty.demo.http.json.dto;

import lombok.Data;

import java.util.List;

/**
 * Customer information.
 */
@Data
public class Customer {
    private long customerNumber;

    /**
     * Personal name.
     */
    private String firstName;

    /**
     * Family name.
     */
    private String lastName;

    /**
     * Middle name(s), if any.
     */
    private List<String> middleNames;

}