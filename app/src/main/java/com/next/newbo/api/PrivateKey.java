package com.next.newbo.api;

import com.next.newbo.BaseApi;

/**
 * Created by NeXT on 15-4-8.
 */
public class PrivateKey extends BaseApi{

    private static final String APP_ID = "211160679";
    private static final String APP_KEY_HASH = "1e6e33db08f9192306c4afa0a61ad56c";
    private static final String REDIRECT_URI = "http://oauth.weico.cc";
    private static final String PACKAGE_NAME = "com.eico.weico";
    private static final String SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";

    // Smooth KEY : E89B158E4BCF988EBD09EB83F5378E87
    // Smooth PACKAGE_NAME : com.mzba.happy.laugh

    public static String getOauthLoginPage() {
        return Constants.OAUTH2_ACCESS_AUTHORIZE + "?" + "client_id=" + APP_ID
                + "&response_type=token&redirect_uri=" + REDIRECT_URI
                + "&key_hash=" + APP_KEY_HASH + "&packagename=" + PACKAGE_NAME
                + "&display=mobile" + "&scope=" + SCOPE;
    }

    public static boolean isUrlRedirected(String url) {
        return url.startsWith(REDIRECT_URI);
    }

}
