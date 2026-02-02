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
        int i = new Random().nextInt(1000);
        User user = User.builder()
                .firstName("UUU")
                .lastName("PPP")
                .email("lmkjiu"+i+"@defrt.bhy")
                .password("Pqwerty453!")
                .build();
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
    public void registrationNegativeTest_WithPopUpPage_EmptyPassword(){
        User user = User.builder()
                .email("sima_simonova370@gmail.com")
                .password("")
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        softAssert.assertTrue(loginPage.isTextInErrorPresent
                ("It'snot look like email"), "validate field email");
        System.out.println("wrong text!!");
        softAssert.assertTrue(loginPage.isTextInErrorPresent
                ("Password is required"), "validate field password");
        System.out.println("right text!!");
        softAssert.assertAll();
    }

    @Test
    public void registrationNegativeTest_WithPopUpPage_EmptyEmail(){
        User user = User.builder()
                .email("")
                .password("D@NT#A")
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        softAssert.assertTrue(loginPage.isTextInErrorPresent
                ("It'snot look like email"), "validate field email");
        System.out.println("wrong text!!");
        softAssert.assertTrue(loginPage.isTextInErrorPresent
                ("Password is required"), "validate field password");
        System.out.println("right text!!");
        softAssert.assertAll();
    }

    @Test
    public void registrationNegativeTest_UserAlreadyExists() {
        User user = User.builder()
                .firstName("ftrye")
                .lastName("dtrye")
                .email("sveta548@smd.com")
                .password("Password123#")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBoxWithActions();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver())
                .isTextInPopUpMessagePresent("User already exists"));
    }

    @Test
    public void registrationNegativeTest_WithSpaceInFirstName() {
        User user = User.builder()
                .firstName(" ")
                .lastName("dtrye")
                .email("sveta548@smd.com")
                .password("Password123#")
                .build();
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
                .email("Venezia@gmail.it")
                .password("s3r3n!s5!m@")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password is required"),
                "validate error message Password is required");

    }

    @Test
    public void registrationNegativeTest_WrongPasswordNoLowercase() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("Venezia@gmail.it")
                .password("S3R3N!S5!M@")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password is required"),
                "validate error message Password is required");

    }

    @Test
    public void registrationNegativeTest_WrongPasswordNoSpecialCharacter() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("Venezia@gmail.it")
                .password("S3R3NiS5iMa")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password is required"),
                "validate error message Password is required");

    }

    @Test
    public void registrationNegativeTest_WrongPasswordNoNumbers() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("Venezia@gmail.it")
                .password("SeReN!Ss!M@")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password is required"),
                "validate error message Password is required");

    }

    @Test
    public void registrationNegativeTest_WrongPasswordFewerThan8Symbols() {
        User user = User.builder()
                .firstName("Pietro")
                .lastName("Aretino")
                .email("Venezia@gmail.it")
                .password("S3R&N")
                .build();
        registrationPage.typeRegistrationForm(user);
        registrationPage.setCheckBoxAgreeTermsOfUse();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage
                        .isTextInErrorPresent("Password is required"),
                "validate error message Password is required");

    }






}
