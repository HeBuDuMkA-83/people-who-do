package ru.zapoebad.pwd.objects;

import com.dart.webadmin.utils.StrUtil;
import ru.zapoebad.pwd.utils.Constants;

/**
 * Created by DuMkA on 19.02.2015.
 */
public class Person extends Origin {

    private String lastName;
    private String middleName;
    private String nick;

    //TODO реализовать различные соцсети
    private String vkAccount;

    public Person() {

    }

    public Person(int id, String name, String lastName, String desc, String text, String vk) {
        setId(id);
        setName(name);
        setLastName(lastName);
        setDesc(desc);
        setText(text);
        setVkAccount(vk);
        setAvatarPath(Constants.AVATAR_DUMMY);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getVkAccount() {
        return vkAccount;
    }

    public void setVkAccount(String vkAccount) {
        this.vkAccount = vkAccount;
    }

    public String getAvatarPath() {
        if (StrUtil.isEmpty(getAvatarPath())) {
            return Constants.AVATAR_DUMMY;
        }
        return getAvatarPath();
    }
}
