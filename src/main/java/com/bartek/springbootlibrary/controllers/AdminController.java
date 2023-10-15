package com.bartek.springbootlibrary.controllers;

import com.bartek.springbootlibrary.requestsModels.AddBookRequest;
import com.bartek.springbootlibrary.services.AdminService;
import com.bartek.springbootlibrary.utils.ExtractJWT;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;

    public static final String SUB = "\"sub\"";
    public static final String USER_TYPE = "\"userType\"";


    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value = "Authorization") String auth, @RequestBody AddBookRequest addBookRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(auth, SUB);
        String admin = ExtractJWT.payloadJWTExtraction(auth, USER_TYPE);

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administrattion page only");
        }
        adminService.postBook(addBookRequest);
    }
}
