package my.vono.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultResponseDto<T> {
	private Boolean isSuccess; 
	private String message;
	private T data;
}