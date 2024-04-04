package ru.nsu.ccfit.lisitsin.dto.reponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersResponse extends BaseResponse {

    private List<String> names;

}
