package com.informatorio.finalproject.dto;

public class UserLoginDto {
    private DetailsUser user = new DetailsUser();
    private String token;


    public void addUser(String fullName, String email) {
        this.user.setFullName(fullName);
        this.user.setEmail(email);
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DetailsUser getUser() {
        return user;
    }

    public void setUser(DetailsUser user) {
        this.user = user;
    }

    private class DetailsUser {
        private String fullName;
        private String email;

        public DetailsUser() {
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }
}
