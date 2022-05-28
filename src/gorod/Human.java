package gorod;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Human implements Comparable<Human>, Serializable {

    public static final long serialVersionUID = 420L;

    private String name; //Поле не может быть null, Строка не может быть пустой
    private ZonedDateTime birthday;

    public Human(String Name){
        this.name=Name;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        if (Objects.isNull(birthday)) return name;
        else return name+"\n"+
                "   birthday: "+ birthday;
    }

    @Override
    public int compareTo(Human other) {

        if (Objects.isNull(other)) return 1;
        else if (Objects.isNull(other.getBirthday()) || Objects.isNull(this.getBirthday())) return this.name.compareTo(other.getName());
        else return this.getBirthday().compareTo(other.getBirthday())+this.name.compareTo(other.getName())/10;
    }
}
