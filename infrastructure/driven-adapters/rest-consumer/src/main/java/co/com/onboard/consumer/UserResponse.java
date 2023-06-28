package co.com.onboard.consumer;

    import lombok.Builder;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
public class UserResponse {

    private UserData data;

    @Getter
    @Setter
    public static class UserData {

        private Integer id;
        private String first_name;
        private String last_name;
        private String email;
    }

}