# Assignment 6 – Data-Driven Testing with Selenium, TestNG & BrowserStack

## 1. Project Overview
This project demonstrates Data-Driven Testing (DDT) using Selenium WebDriver, TestNG, and BrowserStack.
Test data is stored in an Excel file and consumed via a TestNG DataProvider.

The project validates the login functionality of a web application by executing multiple test scenarios
(positive and negative) automatically, both locally and on BrowserStack cloud infrastructure.

---

## 2. Technologies Used
- Java 17  
- Maven  
- Selenium WebDriver  
- TestNG  
- Apache POI  
- BrowserStack  

---

## 3. Project Structure
```
sqat_6
│
├── pom.xml
├── testng.xml
├── testdata/
│   └── LoginData.xlsx
│
└── src
    └── test
        └── java
            ├── config
            │   └── TestConfig.java
            ├── data
            │   └── ExcelDataProvider.java
            ├── pages
            │   └── LoginPage.java
            └── tests
                └── LoginDDTTest.java
```

---

## 4. Key Components

### TestConfig.java
Handles global configuration such as:
- Base URL
- Run mode (local / browserstack)
- Browser selection
- BrowserStack RemoteWebDriver setup

### ExcelDataProvider.java
- Implements TestNG DataProvider
- Reads test data from Excel
- Each row represents one test iteration

### LoginPage.java
- Page Object Model implementation
- Encapsulates login actions and message retrieval

### LoginDDTTest.java
- Main TestNG test class
- Executes login tests using Excel-driven data
- Outputs readable test results to console

---

## 5. Test Data
File: testdata/LoginData.xlsx

Each row contains:
- TestCaseId
- Username
- Password
- Expected result (SUCCESS / ERROR)

---

## 6. Run Tests Locally
Prerequisites:
- Java 17
- Maven
- Installed browser (Chrome or Firefox)

Command:
```
mvn clean test -DrunMode=local -Dbrowser=chrome
```

---

## 7. BrowserStack Execution

### 7.1 Set Environment Variables
```
export BROWSERSTACK_USERNAME="your_browserstack_username"
export BROWSERSTACK_ACCESS_KEY="your_browserstack_access_key"
```

### 7.2 Run Tests on BrowserStack
```
mvn clean test -DrunMode=browserstack -Dbrowser=chrome
```

---

## 8. BrowserStack Results
Results are available in the BrowserStack Automate dashboard:
- Video recordings
- Logs
- Screenshots
- Execution duration
- Browser & OS information

---

## 9. Sample Console Output
```
[TC01] expected=SUCCESS => PASSED | msg=You logged into a secure area!
[TC02] expected=ERROR   => PASSED | msg=Your password is invalid!
```

---

## 10. Conclusion
This project demonstrates:
- Proper Data-Driven Testing approach
- Clean Page Object Model architecture
- Cloud-based test execution with BrowserStack
- Scalable and maintainable automated testing solution
