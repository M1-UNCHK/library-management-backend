package sn.unchk.librarymanagement.constant;

public class GlobalConstant {
    //Message Constant
    public static final String REQUIRED_FIELD_NAME = "Field is required";
    public static final String CREATED_MESSAGE = "%s is created successfully";
    public static final String UPDATED_MESSAGE = "%s is updated successfully";
    public static final String DELETED_MESSAGE = "%s deleted successfully";
    public static final String UPDATED_STATUS_MESSAGE = "%s status is updated successfully";
    public static final String UPDATED_PASSWORD_MESSAGE = "Member password is updated successfully";


    public static final String DEFAULT_PASSWORD = "passer@123";

    //Token Constant
    public static final String SCOPE = "scope";
    public static final String USER_EMAIL = "email";
    public static final String USER_ROLE = "role";
    public static final String ACCESS_TOKEN = "access_token";

    //Base Route Constant
    public static final String AUTH_BASE_ROUTE = "/oauth";
    public static final String MEMBER_BASE_ROUTE = "/members";
    public static final String BOOK_BASE_ROUTE = "/books";
    public static final String AUTHOR_BASE_ROUTE = "/authors";
    public static final String CATEGORY_BASE_ROUTE = "/categories";

}
