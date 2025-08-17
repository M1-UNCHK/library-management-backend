package sn.unchk.librarymanagement.presentation.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sn.unchk.librarymanagement.presentation.dto.request.MemberLoginRequest;

import java.util.Map;

import static sn.unchk.librarymanagement.constant.GlobalConstant.AUTH_BASE_ROUTE;

@RequestMapping(value = AUTH_BASE_ROUTE)
public interface AuthController {

    @PostMapping("/login")
    ResponseEntity<Map<String, String>> login(@RequestBody MemberLoginRequest request);
}
