package order.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import order.bean.BaseResult;
import order.bean.SimpleException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = SimpleException.class)
    @ResponseBody
    public BaseResult handleSimpleError(SimpleException simpleException, HttpServletRequest request) {

        String errorMsg = simpleException.getMessage();
        log.error("ERROR MSG: {} at Request URI : {}", errorMsg, request.getRequestURI());


        return new BaseResult(errorMsg);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleGlobalError(Exception exception, HttpServletRequest request) {

        log.error("==== 出現未預期的錯誤 ====");
        log.error("Request URL：{}", request.getRequestURL());
        log.error("Exception msg：{}", exception.getMessage());
        log.error("Stack Trace ：{}", ExceptionUtils.getMessage(exception) + " " + ExceptionUtils.getStackTrace(exception));
        log.error("========================");

        return new ResponseEntity<>("請稍後", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
