package ua.oblikchasu.logger;

public abstract class LogMsg {
    private LogMsg(){}

    public static final String ERROR = "Error!";

    public static final String USER_ADDED = "New user added to database: {}";
    public static final String USER_UPDATED = "User updated: {}";
    public static final String USER_DELETED_ID = "User deleted. Id: {}";
    public static final String USER_ADD_FAIL = "Unable to add user";
    public static final String USER_DELETE_FAIL_ID = "Unable to delete user. Id: {}";
    public static final String USER_UPDATE_FAIL = "Unable to update user: {}";
    public static final String USER_NOT_FOUND_ID = "User not found. Id: {}";

    public static final String ACTIVITY_ADDED = "New activity added to database: {}";
    public static final String ACTIVITY_ADD_FAIL = "Unable to add activity";
    public static final String ACTIVITY_UPDATED = "Activity updated: {}";
    public static final String ACTIVITY_UPDATE_FAIL = "Unable to update activity: {}";
    public static final String ACTIVITY_DELETED = "Activity deleted. Id: {}";
    public static final String ACTIVITY_DELETE_FAIL = "Unable to delete activity. Id: {}";
    public static final String ACTIVITY_NOT_FOUND = "Activity not found. Id: {}";

    public static final String ACTIVITY_CATEGORY_ADDED = "New activity category added to database: {}";
    public static final String ACTIVITY_CATEGORY_ADD_FAIL = "Unable to add activity category";
    public static final String ACTIVITY_CATEGORY_UPDATED = "Activity category updated: {}";
    public static final String ACTIVITY_CATEGORY_UPDATE_FAIL = "Unable to update activity category: {}";
    public static final String ACTIVITY_CATEGORY_DELETED = "Activity category deleted. Id: {}";
    public static final String ACTIVITY_CATEGORY_DELETE_FAIL = "Unable to delete category. Id: {}";
    public static final String ACTIVITY_CATEGORY_NOT_FOUND = "Activity category not found. Id: {}";

    public static final String USERS_ACTIVITY_ADDED = "New users activity added to database: {}";
    public static final String USERS_ACTIVITY_ADD_FAIL = "Unable to add users activity";
    public static final String USERS_ACTIVITY_GET_FAIL = "Unable to get users activities for user. Id: {}";
    public static final String USERS_ACTIVITY_UPDATED = "Users activity updated. Id: {}, status {}";
    public static final String USERS_ACTIVITY_UPDATE_FAIL = "Unable to update users activity: {}";
    public static final String USERS_ACTIVITY_DELETED = "Users activity deleted: {}";
    public static final String USERS_ACTIVITY_DELETE_FAIL = "Unable to delete users activity. Id: {}";
    public static final String USERS_ACTIVITY_NOT_FOUND = "Users activity not found. Id: {}";

    public static final String LOGGED_IN = "{} has signed-in as {}";
    public static final String LOGGED_OUT = "{} has signed-out";
    public static final String ACCESS_DENIED = "{} - access denied";

    public static final String LANGUAGE_SET = "Language set: {}";
}
