package com.onurcansever.blog.service;

import com.onurcansever.blog.payload.LoginDto;
import com.onurcansever.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
