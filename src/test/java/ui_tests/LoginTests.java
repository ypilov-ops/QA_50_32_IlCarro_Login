package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import pages.PopUpPage;

import static utils.PropertiesReader.getProperty;
import static utils.UserFactory.faker;
import static utils.UserFactory.positiveUser;

public class LoginTests extends ApplicationManager {
    SoftAssert softAssert =  new SoftAssert();
    @Test
    public void loginPositiveTest(){
        User user = User.builder()
                .email(getProperty("base.properties", "Email"))
                .password(getProperty("base.properties", "Password"))
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        Assert.assertTrue(loginPage.isLoggedInDisplayed());

    }

    @Test
    public void loginPositiveTest_WithPopUpPage(){
        User user = User.builder()
                .email(getProperty("base.properties", "Email"))
                .password(getProperty("base.properties", "Password"))
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver()).isTextInPopUpMessagePresent("Logged in success"));

    }


    @Test
    public void loginNegativeTest_WrongPassword_WOSpecSymbol(){
        User user = User.builder()
                .email("venezia@gmail.it")
                .password("S3R3NiS5iMa")
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        Assert.assertTrue(new PopUpPage(getDriver())
                .isTextInPopUpMessagePresent("Login or Password incorrect"));
    }
    @Test
    public void loginNegativeTest_Password_Empty(){
        User user = User.builder()
                .email("venezia@gmail.it")
                .password("")
                .build();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        Assert.assertTrue(loginPage.isTextInErrorPresent
                ("Password is required"), "validate field password");

    }


}
