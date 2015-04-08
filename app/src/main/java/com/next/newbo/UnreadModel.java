package com.next.newbo;

/**
 * Created by NeXT on 15/4/8.
 */
public class UnreadModel {

    public int  status              = 0,
                follower            = 0,
                cmt                 = 0,
                dm                  = 0,
                mention_status      = 0,
                mention_cmt         = 0,
                group               = 0,
                private_group       = 0,
                notice              = 0,
                invite              = 0,
                badge               = 0,
                photo               = 0,
                msgbox              = 0;

    @Override
    public String toString() {
        return "UnreadModel{" +
                "status=" + status +
                ", follower=" + follower +
                ", cmt=" + cmt +
                ", dm=" + dm +
                ", mention_status=" + mention_status +
                ", mention_cmt=" + mention_cmt +
                ", group=" + group +
                ", private_group=" + private_group +
                ", notice=" + notice +
                ", invite=" + invite +
                ", badge=" + badge +
                ", photo=" + photo +
                ", msgbox=" + msgbox +
                '}';
    }

}
