COMP249 Assignment in Java
==

This is an attempt at rebuilding the 2019 COMP249 Web Application assignment in 
Java using the [spark](http://sparkjava.com) framework.  The app implements a simple
shopping cart for an online store making use of sessions. 

Things to note. 
* By default there is no logging of requests coming to the server so it is hard to 
    the relationship between browser requests and the server's responses
* Errors just silently fail in some cases or produce cryptic stack dumps unrelated 
    to the actual error.  Running in the debugger does not interrupt when an error
    occurs.  Not catching an exception in a handler results in a 404 response.
* The built-in session object seems to implement a key value store for string values only,
    could use this to store a JSON version of the cart but here I chose to 
    add a CartEntry class and cart store to the model.  Provided sessions takes
    care of cookie management - could also do that explicitly.
* The model interface suggested is the DAO (Data Access Object), my initial version
    uses in-memory data storage but this could be where we interface to a db

