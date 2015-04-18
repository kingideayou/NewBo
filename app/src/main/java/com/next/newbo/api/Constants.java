package com.next.newbo.api;

/**
 * Created by NeXT on 15-4-8.
 */
public class Constants {

    public static final String SINA_BASE_URL = "https://api.weibo.com/2/";

    // Login
    public static final String OAUTH2_ACCESS_AUTHORIZE = "https://open.weibo.cn/oauth2/authorize";

    // User / Account
    public static final String GET_UID = SINA_BASE_URL + "account/get_uid.json";
    public static final String USER_SHOW = SINA_BASE_URL + "users/show.json";

    // Statuses
    public static final String HOME_TIMELINE = SINA_BASE_URL + "statuses/home_timeline.json";
    public static final String USER_TIMELINE = SINA_BASE_URL + "statuses/user_timeline.json";
    public static final String BILATERAL_TIMELINE = SINA_BASE_URL + "statuses/bilateral_timeline.json";
    public static final String MENTIONS = SINA_BASE_URL + "statuses/mentions.json";
    public static final String REPOST_TIMELINE = SINA_BASE_URL + "statuses/repost_timeline.json";
    public static final String UPDATE = SINA_BASE_URL + "statuses/update.json";
    public static final String UPLOAD = SINA_BASE_URL + "statuses/upload.json";
    public static final String REPOST = SINA_BASE_URL + "statuses/repost.json";
    public static final String DESTROY = SINA_BASE_URL + "statuses/destroy.json";
    public static final String UPLOAD_PIC = SINA_BASE_URL + "statuses/upload_pic.json";
    public static final String UPLOAD_URL_TEXT = SINA_BASE_URL + "statuses/upload_url_text.json";

    // Comments
    public static final String COMMENTS_TIMELINE = SINA_BASE_URL + "comments/timeline.json";
    public static final String COMMENTS_MENTIONS = SINA_BASE_URL + "comments/mentions.json";
    public static final String COMMENTS_SHOW = SINA_BASE_URL + "comments/show.json";
    public static final String COMMENTS_CREATE = SINA_BASE_URL + "comments/create.json";
    public static final String COMMENTS_REPLY = SINA_BASE_URL + "comments/reply.json";
    public static final String COMMENTS_DESTROY = SINA_BASE_URL + "comments/destroy.json";

    // Friendships
    public static final String FRIENDSHIPS_FRIENDS = SINA_BASE_URL + "friendships/friends.json";
    public static final String FRIENDSHIPS_FOLLOWERS = SINA_BASE_URL + "friendships/followers.json";
    public static final String FRIENDSHIPS_CREATE = SINA_BASE_URL + "friendships/create.json";
    public static final String FRIENDSHIPS_DESTROY = SINA_BASE_URL + "friendships/destroy.json";
    public static final String FRIENDSHIPS_GROUPS = SINA_BASE_URL + "friendships/groups.json";
    public static final String FRIENDSHIPS_GROUPS_IS_MEMBER = SINA_BASE_URL + "friendships/groups/is_member.json";
    public static final String FRIENDSHIPS_GROUPS_TIMELINE = SINA_BASE_URL + "friendships/groups/timeline.json";
    public static final String FRIENDSHIPS_GROUPS_CREATE = SINA_BASE_URL + "friendships/groups/create.json";
    public static final String FRIENDSHIPS_GROUPS_DESTROY = SINA_BASE_URL + "friendships/groups/destroy.json";
    public static final String FRIENDSHIPS_GROUPS_MEMBERS_ADD = SINA_BASE_URL + "friendships/groups/members/add.json";
    public static final String FRIENDSHIPS_GROUPS_MEMBERS_DESTROY = SINA_BASE_URL + "friendships/groups/members/destroy.json";

    // Remind
    public static final String REMIND_UNREAD_COUNT = "https://rm.api.weibo.com/2/remind/unread_count.json";


}
