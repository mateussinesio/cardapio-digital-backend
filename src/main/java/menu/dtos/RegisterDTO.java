package menu.dtos;

import menu.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
