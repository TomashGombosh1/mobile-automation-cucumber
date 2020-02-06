package com.mobile.automation.framework.models;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Data;

import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
@Data
@AllArgsConstructor
public class User {
    private String userName;
    private String firstName;
    private String lastName;

    public User(final Consumer<User> builder) {
        requireNonNull(builder).accept(this);
    }
}
