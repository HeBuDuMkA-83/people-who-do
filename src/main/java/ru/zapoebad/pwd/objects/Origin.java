package ru.zapoebad.pwd.objects;

import com.dart.webadmin.utils.StrUtil;
import ru.zapoebad.pwd.utils.Constants;

/**
 * Created by DuMkA on 19.02.2015.
 */
public class Origin {

    private int id;

    private String name;
    private String desc;
    private String text;

    protected String avatarPath = Constants.AVATAR_DUMMY;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAvatarPath() {
        if (StrUtil.isEmpty(avatarPath)) {
            return Constants.ORIGIN_DUMMY;
        }
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
