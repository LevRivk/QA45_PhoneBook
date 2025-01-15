package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.BaseApi;
@Getter
@Setter
@ToString
@Builder
public class ResponceMessageDto implements BaseApi {
    private String message;
}
