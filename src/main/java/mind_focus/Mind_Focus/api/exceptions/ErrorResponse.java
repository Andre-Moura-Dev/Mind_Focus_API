package mind_focus.Mind_Focus.api.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private Integer statusCode;
    private String userMessage;
    private String developerMessage;
}
