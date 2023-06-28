package co.com.onboard.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class RuObject {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean newReqresData;
}
