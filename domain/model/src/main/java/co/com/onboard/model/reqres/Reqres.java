package co.com.onboard.model.reqres;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reqres {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
