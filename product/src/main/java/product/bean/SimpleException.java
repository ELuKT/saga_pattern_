package product.bean;

import lombok.Getter;

@Getter
public class SimpleException extends RuntimeException {
    private final String appMsg;
    public SimpleException(String appMsg, Exception e) {
        super(e);
        this.appMsg = appMsg;
    }

    public SimpleException(String appMsg) {
        this.appMsg = appMsg;
    }


}
