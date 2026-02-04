package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import pages.PopUpPage;
import pages.RegistrationPage;

import java.util.Random;

import static utils.UserFactory.*;
import static utils.PropertiesReader.*;

public class RegistrationTests extends ApplicationManager {
    RegistrationPage registrationPage;
    SoftAssert softAssert =  new SoftAssert();

    @BeforeMethod
    public void goToRegistrationPage() {
        new HomePage(getDriver()).clickBtnSignUp();
        registrationPage = new RegistrationPage(getDriver());
    }

    @Test
    public void registrationPositiveTest() {
        User user = new User(getProperty("base.properties", "FirstName"),
                getProperty("base.properties", "LastName"),
                getProperty("base.properties", "Email"),
                getProperty("base.properties", "Password"));

        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver())
                .isTextInPopUpMessagePresent("You are logged in success"));
    }

    @Test
    public void registrationPositiveTest_WithFaker() {
        User user = positiveUser();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver())
                .isTextInPopUpMessagePresent("You are logged in success"));
    }



    @Test
    public void registrationNegativeTest_UserAlreadyExists() {
        User user = new User(getProperty("base.properties", "FirstName"),
                getProperty("base.properties", "LastName"),
                getProperty("base.properties", "Email"),
                getProperty("base.properties", "Password"));


        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver())
                .isTextInPopUpMessagePresent("User already exists"));
    }

    @Test
    public void registrationNegativeTest_WithSpaceInFirstName() {
        User user = new User(getProperty("base.properties", " "+"FirstName"),
                getProperty("base.properties", "LastName"),
                getProperty("base.properties", "Email"),
                getProperty("base.properties", "Password"));

        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver())
                .isTextInPopUpMessagePresent("must not be blank"));
    }

    @Test
    public void registrationNegativeTest_WithAllEmptyFields() {
        User user = User.builder()
                .firstName("")
                .lastName("")
                .email("")
                .password("")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        softAssert.assertTrue(registrationPage
                        .isTextInErrorPresent("Name is required"),
                "validate error message Name is required");
        softAssert.assertTrue(registrationPage
                        .isTextInErrorPresent("Last name is required"),
                "validate error message Last name is required");
        softAssert.assertTrue(registrationPage
                        .isTextInErrorPresent("Email is required"),
                "validate error message Email is required");
        softAssert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password is required"),
                "validate error message Password is required");
        softAssert.assertAll();
    }

    @Test
    public void registrationNegativeTest_WrongPasswordNoUppercase() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("venezia@gmail.it")
                .password("s3r3n!s5!m@")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]"),
                "validate error message Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]");

    }

    @Test
    public void registrationNegativeTest_WrongPasswordNoLowercase() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("venezia@gmail.it")
                .password("S3R3N!S5!M@")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]"),
                "validate error message Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]");

    }

    @Test
    public void registrationNegativeTest_WrongPasswordNoSpecialCharacter() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("venezia@gmail.it")
                .password("S3R3NiS5iMa")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]"),
                "validate error message Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]");

    }

    @Test
    public void registrationNegativeTest_WrongPasswordNoNumbers() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("venezia@gmail.it")
                .password("SeReN!Ss!M@")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]"),
                "validate error message Password must contain 1 uppercase letter, 1 lowercase letter, 1 number and one special symbol of [@$#^&*!]");

    }

    @Test
    public void registrationNegativeTest_WrongPasswordFewerThan8Symbols() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("venezia@gmail.it")
                .password("S3R&N")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password must contain minimum 8 symbols"),
                "validate error message Password must contain minimum 8 symbols");

    }

}
