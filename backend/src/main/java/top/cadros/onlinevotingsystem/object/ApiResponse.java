package top.cadros.onlinevotingsystem.object;

/**
 * <p>API响应数据类</p>
 * <p>用于封装http响应数据</p>
 * @author 高洪森
 */
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
