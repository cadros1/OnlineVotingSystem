package top.cadros.onlinevotingsystem.object;

public class ApiResponse {
    int code;
    String message;
    String authorization;
    Object data;

    public ApiResponse(int code, String message, String authorization, Object data) {
        this.code = code;
        this.message = message;
        this.authorization = authorization;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthorization() {
        return authorization;
    }

    public Object getData() {
        return data;
    }
}
