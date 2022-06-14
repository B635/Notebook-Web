package com.b635.notebook.Service;

import com.b635.notebook.Model.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    int updatePassword(int userId, String newPassword);

    Authentication getAuth();
}
