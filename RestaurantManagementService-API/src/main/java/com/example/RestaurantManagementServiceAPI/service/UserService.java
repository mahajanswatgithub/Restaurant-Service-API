package com.example.RestaurantManagementServiceAPI.service;

import com.example.RestaurantManagementServiceAPI.model.AuthenticationToken;
import com.example.RestaurantManagementServiceAPI.model.User;
import com.example.RestaurantManagementServiceAPI.model.UserType;
import com.example.RestaurantManagementServiceAPI.model.dto.SignInInput;
import com.example.RestaurantManagementServiceAPI.model.dto.SignUpOutput;
import com.example.RestaurantManagementServiceAPI.repository.IAuthTokenRepo;
import com.example.RestaurantManagementServiceAPI.repository.IUserRepo;
import com.example.RestaurantManagementServiceAPI.service.emailUtility.EmailHandler;
import com.example.RestaurantManagementServiceAPI.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepo iUserRepo;
    @Autowired
    IAuthTokenRepo iAuthTokenRepo;

    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
        UserType type = user.getUserType();
        if(type == UserType.Admin && !newEmail.endsWith(("@admin.com")))
        {
            throw new IllegalArgumentException("Invalid email format for admin");
        }
        else if(type == UserType.Normal_User && !newEmail.endsWith(("@gmail.com")))
        {
            throw new IllegalArgumentException("Invalid email format for Normal User");
        }


        //check if this user email already exists ??
        User existingUser = iUserRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            //save the User with the new encrypted password

            user.setUserPassword(encryptedPassword);
            iUserRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }
    public User findByUserEmail(String email)
    {
        return iUserRepo.findByUserEmail(email);
    }

    public List<User> getAllUsers() {
        return iUserRepo.findAll();
    }

    public String sigInUser(SignInInput signInInput) {

        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;
        }

        User existingUser = iUserRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                iAuthTokenRepo.save(authToken);

                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public String deleteUser(Integer Id) {
         iUserRepo.deleteById(Id);
         return "User Deleted Succesfully";
    }

}
