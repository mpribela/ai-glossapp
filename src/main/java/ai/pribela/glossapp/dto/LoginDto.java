package ai.pribela.glossapp.dto;

import lombok.Builder;

@Builder
public record LoginDto(String username, String password) {

}
