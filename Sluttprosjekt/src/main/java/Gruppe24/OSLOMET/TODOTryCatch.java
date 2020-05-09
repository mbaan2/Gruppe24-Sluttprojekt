/* Directory CAR (DONE)
- No try catches has to be done here
*/

/*
Directory Controllers
- Login_Controller (DONE):
- RetrievePassword_Controller:
    * First has to be made final
    * First txt file has to be changed to jobj
    * All FileOpeners needs to be checked
    * Buttons to antoher screen need testing
    * Checking of corrext feedback

- SET(Addons,Color,Fuel,Wheels) DONE:

- SignUp_Controller
    * First has to be made final
    * Filetreatment needs to be checked
    * Buttons needs to be tested

- Summary_Controller
    * Done but needs checking and testing

- SuperUSer_Controller
    * Button to other screen need testing

- SuperUserCarView
    * Buttons to other screen need testing
    * Opening files needs testing
    * Thread needs testing

- SuperUserFileRestoration
    * Creating file needs testing?
    * Buttons to other screens need testing

- UserCarView
    * Saving file needs testing
    * Thread needs testing
    * Loading file needs testing
    * Buttons to other screens need testing

- Userlist_Controller
    * Buttons to other screen need testing
    * Opening files needs testing
    * Thread needs testing

- WelcomeScreen_Controller (DONE):
 */

/* DataValidation Directory
    * Not sure if these three classes need testing
 */

/* ExceptionClasses Directorty (DONE)
 */

/* FileTreatment Directory
- FileOpenerJobj
    * file treatment (all opening should trows exceptions)

- FileSaverJobj
    * file treatment (all saving should trow exceptions)

- FileSaverTxt
    * file treatment (all saving should trow exceptions)

- FormatCar
    * done

- LoadingValuesOnScreen
    * done

- StandardPAths
    * done

 */

/* SuperUser directory
- LoadCategory
    * file treatment maybe the should trow exceptions, not sure, please test. DONE

- LoadingValuesThread
    * is already fine i think, please check             SEEMS FINE TO ME?

- RemoveCarpoarts
    * done

- SaveCarparts
    * file treatment maybe the should trow exceptions, not sure, please test.   ADDED INFO ON THE LABEL. MAGICALLY FIXES ITSELF, SO SEEMS FINE

- CreateJobjFiles
    * file treatment maybe the should trow exceptions, not sure, please test.   DIDN'T FIND ANYTHING ELSE TO CHANGE

- Filter
    * done

- TableViewCreation
    * Needs lots of attention. i think an error could be anywhere so needs a good check
        - delete user.jobj after logging into user. IO Exception when loading table, but still loads. Cars get saved to user just right
        - go through all the TODOs



 */

/* UserLogin Directory
Only WriteUser needs attention but it already looks good.
 */