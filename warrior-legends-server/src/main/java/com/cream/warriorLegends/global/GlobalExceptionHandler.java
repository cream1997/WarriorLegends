package com.cream.warriorLegends.global;

import com.cream.warriorLegends.common.Ret;
import com.cream.warriorLegends.common.exception.Err;
import com.cream.warriorLegends.common.exception.RunErr;
import com.cream.warriorLegends.common.exception.TipErr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RunErr.class)
    public Ret<String> handleCommonRunErr(RunErr e) {
        log.error("发生异常", e);
        return Ret.err(e.getMessage());
    }

    @ExceptionHandler(Err.class)
    public Ret<String> handleCommonErr(Err e) {
        log.error("发生异常", e);
        return Ret.err(e.getMessage());
    }

    @ExceptionHandler(TipErr.class)
    public Ret<String> handleCommonErr(TipErr e) {
        return Ret.err(e.getMessage());
    }

    /**
     * 处理其他不可知的异常
     * 不会和上面的冲突，也就是说如果上面的异常都能处理，就不会执行到这里（实测）
     */
    @ExceptionHandler(Exception.class)
    public Ret<String> handlerException(Exception e) {
        log.error("服务器异常", e);
        return Ret.err("服务器异常");
    }
}
