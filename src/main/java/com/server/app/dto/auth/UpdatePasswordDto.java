package com.server.app.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordDto {

    @NotBlank(message = "La contraseña actual no puede estar vacía")
    private String oldpassword;

    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&._-]).+$", message = "La contraseña debe incluir al menos una mayúscula, una minúscula, un número y un carácter especial")
    private String newpassword;

    @NotBlank(message = "La confirmación de contraseña no puede estar vacía")
    private String confirmpassword;
}