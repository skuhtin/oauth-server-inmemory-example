package ua.skuhtin.controller;

import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.skuhtin.dto.ResponseModel;
import ua.skuhtin.dto.UserDto;
import ua.skuhtin.model.Users;
import ua.skuhtin.repository.UsersRepository;
import ua.skuhtin.security.SecurityUser;

@RestController
public class AuthExampleController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "free access")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> getUnprotectedResponse() {
        ResponseModel responseModel = new ResponseModel("free access");
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @ApiOperation(value = "for Admin role only")
    @RequestMapping(value = "/secure", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> getSecuredResponse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        ResponseModel responseModel = new ResponseModel(securityUser.getUsername(), securityUser.getRole(), "secured");
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Users> createUser(@RequestBody UserDto dto) {
        return new ResponseEntity<>(usersRepository.save(modelMapper.map(dto, Users.class)), HttpStatus.CREATED);
    }
}
