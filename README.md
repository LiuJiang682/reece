# Reece code test

This project is for Reece's code test. The Epic brief is:

As a Reece Branch ManagerI would like an address book application on my PC. 
So that I can keep track of my customer contacts

Acceptance Criteria

o Address book will hold name and phone numbers of contact entries 
o Users should be able to add new contact entries
o Users should be able to remove existing contact entries
o Users should be able to print all contacts in an address book
o Users should be able to maintain multiple address books
o Users should be able to print a unique set of all contacts across multiple address books

Written in Java
A working user interface is not required, nor the use of any frameworks.  
We are looking for all acceptance criteria to be demonstrable through tests 

Assumptions:

1. Print all contacts means return a list of contacts to the calling class without exactly send to a printer. It will also NOT provide any printer setup options.
2. Print a unique set of all contacts means the list provided will NOT has any duplicated contacts.
3. Nor the use of any frameworks means I can NOT use any Apache libraries, Log4j and any mocking frameworks. This requirement has impacted the way of code written. Specially those negative test cases was NOT able to develop due to the technical difficult to simulate those scenarios. 
