package spring.springassignment.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CredentialDTO {
    private String accessToken;
    private String refreshToken;
}
