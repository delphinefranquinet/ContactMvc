package dto;

public class ResponseDto {
	
	private String status;
	private String message;
	private Object payload;
	
	public ResponseDto() {}
	
	public ResponseDto(ResponseDtoStatus status, String message) {
		super();
		this.status = status.name();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

}
