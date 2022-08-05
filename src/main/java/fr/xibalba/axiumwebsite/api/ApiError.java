package fr.xibalba.axiumwebsite.api;

import java.util.Arrays;

public interface ApiError {

    String getMessage();

    default String getType() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    int getCode();

    enum Global implements ApiError {

        MISSING_PARAMETER("Missing parameter"),
        INVALID_PARAMETER("Invalid parameter"),
        REQUEST_ALREADY_DONE_OR_IN_PROGRESS("Request already done or in progress");
        private final String message;

        Global(String message) {

            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public int getCode() {

            return Arrays.asList(Global.values()).indexOf(this) + 1;
        }

        @Override
        public String toString() {
            return this.getType() + ": " + this.getMessage();
        }
    }

    enum Account implements ApiError {

        ACCOUNT_NOT_FOUND("Account not found"),
        BAD_CREDENTIALS("Bad credentials"),
        INVALID_TOKEN("Invalid token"),
        ACCOUNT_IS_BANNED_OR_DISABLED("Account is banned or disabled"),
        ACCOUNT_ALREADY_EXISTS("Account already exists"),
        ACCOUNT_ALREADY_CONNECTED("Account already connected"),
        EMAIL_ALREADY_EXISTS("Email already exists"),
        EMAIL_IS_BANNED("Email is banned"),
        USERNAME_ALREADY_TAKEN("Username already taken");

        private final String message;

        Account(String message) {
            this.message = message;

        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return this.getType() + ": " + this.getMessage();
        }

        @Override
        public int getCode() {

            return Arrays.asList(Account.values()).indexOf(this) + 1;
        }
    }

    enum Role implements ApiError {

        ROLE_NOT_FOUND("Role not found"),
        ROLE_ALREADY_EXISTS("Role already exists"),
        ROLE_ALREADY_ASSIGNED("Role already assigned"),
        USER_DOES_NOT_HAVE_THIS_ROLE("User does not have this role");

        private final String message;

        Role(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return this.getType() + ": " + this.getMessage();
        }

        @Override
        public int getCode() {

            return Arrays.asList(Role.values()).indexOf(this) + 1;
        }
    }

    enum Permission implements ApiError {

        PERMISSION_NOT_FOUND("Permission not found"),
        PERMISSION_ALREADY_EXISTS("Permission already exists"),
        PERMISSION_ALREADY_ASSIGNED("Permission already assigned"),
        USER_DOES_NOT_HAVE_THIS_PERMISSION("User does not have this permission"),
        DONT_HAVE_PERMISSION_TO_DO_THIS("You don't have permission to do this");

        private final String message;

        Permission(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return this.getType() + ": " + this.getMessage();
        }

        @Override
        public int getCode() {

            return Arrays.asList(Permission.values()).indexOf(this) + 1;
        }
    }
}
