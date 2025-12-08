package com.eorion.bo.enhancement.application.config;

import com.eorion.bo.enhancement.application.domain.dto.common.ErrorMessage;
import com.eorion.bo.enhancement.application.domain.exception.DataNotExistException;
import com.eorion.bo.enhancement.application.domain.exception.InsertFailedException;
import com.eorion.bo.enhancement.application.domain.exception.UpdateFailedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ApplicationRestControllerAdvice {

    @ExceptionHandler(InsertFailedException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage insertFailed(Throwable e) {
        log.error(e.getLocalizedMessage());
        return new ErrorMessage(e.getLocalizedMessage());
    }

    /**
     * 修改失败
     */
    @ExceptionHandler(UpdateFailedException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage updateFailed(Throwable e) {
        log.error(e.getLocalizedMessage());
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(DataNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage dataNotExist(Throwable e) {
        log.error(e.getLocalizedMessage());
        return new ErrorMessage(e.getMessage());
    }

}
