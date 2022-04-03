# Simple User CRUD Api.

User Model:
  - String id;
  - String name;
  - String email;

UserController EndPoints:
  - Get api/v1/users/ - fetches all users.
  - Post api/v1/users/ - should contain User Model in request body. Save given user.
  - Put api/v1/users/ - should contain User Model in request body. Update given user.
  - Delete api/v1/users/ - deletes user with given ID.
