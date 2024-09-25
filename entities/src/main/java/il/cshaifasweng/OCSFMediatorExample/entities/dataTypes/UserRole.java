package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

public enum UserRole {
    NOT_LOGGED_IN,
    GUEST,
    USER,
    CUSTOMER_SERVICE,
    BRANCH_MANAGER,
    CONTENT_MANAGER,
    MANAGER_OF_ALL_BRANCHES,
    SYSTEM_MANAGER,  // This is only for internal use and should not be used by the client
}
