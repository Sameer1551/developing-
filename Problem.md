# Problems

## Problem 1: User Session Handling

### Issue
Whenever the server is run or restarted, all previously logged-in users should be automatically logged out and required to log in again. This functionality was working earlier but is no longer behaving as expected.

### Important Note
There is another function that allows a logged-in user to remain logged in across page refreshes or new tabs in the same browser session. This feature is working correctly and should not be disrupted while fixing the above issue.

## Problem 2: User Management - Admin Dashboard

### Issue
In the Admin Dashboard, under User Management, when clicking "Add User", filling out the form, and submitting:

- The new user appears to be added.
- However, the data is stored only temporarily (not persisted).

### Expected Behavior
When a new user is added via the form, their data should be saved permanently in the `USERUAD.json` file.

### Additional Context
The `USERUAD.json` file is also used for login authentication. Therefore, only users whose data exists in this file should be able to log in.
