package com.aibles.utils.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtils {

    @Autowired
    public ServiceUtils() {
    }

    public <T> ResponseEntity<T> createOkResponse(T body) {
        return createResponse(body, HttpStatus.OK);
    }

    /**
     * Clone an existing result as a new one, filtering out http headers that not should be moved on and so on...
     *
     * @param result
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> createResponse(ResponseEntity<T> result) {

        // TODO: How to relay the transfer encoding??? The code below makes the fallback method to kick in...
        ResponseEntity<T> response = createResponse(result.getBody(), result.getStatusCode());
//        LOG.info("NEW HEADERS:");
//        response.getHeaders().entrySet().stream().forEach(e -> LOG.info("{} = {}", e.getKey(), e.getValue()));
//        String ct = result.getHeaders().getFirst(HTTP.CONTENT_TYPE);
//        if (ct != null) {
//            LOG.info("Add without remove {}: {}", HTTP.CONTENT_TYPE, ct);
////            response.getHeaders().remove(HTTP.CONTENT_TYPE);
//            response.getHeaders().add(HTTP.CONTENT_TYPE, ct);
//        }
        return response;
    }

    public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }
}

