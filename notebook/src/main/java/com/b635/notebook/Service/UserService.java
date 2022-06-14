package com.b635.notebook.Service;

import com.b635.notebook.Model.entity.User;

public interface UserService {
    int updatePassword(int userId, String newPassword);

    User getUser(String username);
}
