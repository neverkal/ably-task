package com.ably.task.member.api.utils;

import lombok.Getter;

@Getter
public class RegexPattern {
    public final static String PHONE_NUMBER = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}";
    public final static String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
    public final static String NICK_NAME = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{1,20}";
    public final static String NAME = "^[가-힣]{2,30}$";
    public final static String OTP = "[0-9]{4}";
}
