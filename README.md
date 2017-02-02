# Data-Driven-Automation-using-xlsx-sheet

To run a data driven automation to test Student login page using xlsx sheet.

1) Created a xlsx sheet with multiple rows (3 or more) and columns data (e.g. Email and dummy password etc.)

2) Created a Java Selenium WebDriver test case using TestNG framework to test NPU student login to drive data line by line from a xlsx sheet and write
data (running result) to the xlsx sheet.

3) Using as much as function method calls.

4) Using TestNG annotations @BeforeTest, @AfterTest, @BeforeMethod, @AfterMethod and @Test.

5) Use those data for “Email”.

First Name.Last Name@mail.npu.edu

First Name-Last Name@mail.npu.edu

First Name_Last Name@mail.npu.edu

6) Use your student id as “Password”.

@Test(dataProvider="studentdata")

public void NPUStudentLoginTestNG(String email, String password) 
{

// Step 1: Initiate Firefox browser

// Step 2: open NPU from google search

openNPUFromGoogleSearch(SearchText, LinkText);

// Step 3: click Admissions then click Apply Online to open Student Logon page

openApplyOnline();

// Step 4: check Student Login page title is "Log On"

checkOpenStudentLogin("Log On");

// Step 5: input Email and Password

InputEmailPassword(email, password);

// Step 6: click Log On button

clickLogonbutton();

// Step 7: close window and exit

}
