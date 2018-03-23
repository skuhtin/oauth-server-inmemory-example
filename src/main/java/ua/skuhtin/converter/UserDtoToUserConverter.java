package ua.skuhtin.converter;


import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.skuhtin.dto.UserDto;
import ua.skuhtin.model.Users;

public class UserDtoToUserConverter implements Converter<UserDto, Users> {

    private final PasswordEncoder encoder;

    public UserDtoToUserConverter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public Users convert(MappingContext<UserDto, Users> context) {
        Users user = new Users();
        user.setLogin(context.getSource().getLogin());
        user.setPassword(encoder.encode(context.getSource().getPassword()));
        user.setEnabled(context.getSource().isEnabled());
        user.setRoleId(context.getSource().getRoleId());
        return user;
    }
}
