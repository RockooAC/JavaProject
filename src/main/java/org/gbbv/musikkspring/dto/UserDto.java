package org.gbbv.musikkspring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gbbv.musikkspring.model.Role;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Integer userId;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 16, message = "Password should be at least 16 characters." +
            " If you're not capable to remember a password of 16 characters, you're not capable to remember any password." +
            " If you're not capable to remember any password, you should use a password manager." +
            " If you're not using a password manager, you're not taking security seriously. If you're not taking security seriously, you're not taking your privacy seriously." +
            "lol, dont take it seriously, but use a password manager. ok? ok. " +
            "best regards, gabb")
    private String password;

    public void setRole(String user) {
    }

}