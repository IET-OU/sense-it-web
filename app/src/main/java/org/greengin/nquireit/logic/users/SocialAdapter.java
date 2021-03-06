package org.greengin.nquireit.logic.users;

import org.greengin.nquireit.entities.users.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SocialAdapter implements SignInAdapter, ConnectionSignUp {

    @Autowired
    UserServiceBean userServiceBean;

    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest) request.getNativeRequest();
        HttpServletResponse servletResponse = (HttpServletResponse) request.getNativeResponse();

        org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
        ConnectionData data = connection.createData();
        UserProfile user = userServiceBean.providerSignIn(profile.getUsername(), data.getProviderId(), data.getProviderUserId());
        if (user != null) {
            userServiceBean.login(user, servletRequest, servletResponse);
        }

        return null;
    }

    public String execute(Connection<?> connection) {
        UserProfile current = userServiceBean.currentUser();
        if (current == null) {
            org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
            ConnectionData data = connection.createData();
            UserProfile user = userServiceBean.providerSignIn(profile.getUsername(), data.getProviderId(), data.getProviderUserId());
            return user.getUsername();
        } else {
            return current.getUsername();
        }
    }
}