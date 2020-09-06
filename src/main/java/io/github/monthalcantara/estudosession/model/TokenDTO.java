package io.github.monthalcantara.estudosession.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO  implements Serializable {

    private String login;

    private String token;


}

