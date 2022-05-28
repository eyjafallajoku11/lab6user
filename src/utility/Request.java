package utility;

import gorod.Human;

import java.io.Serializable;

public class Request implements Serializable {
    public static final long serialVersionUID = 69L;

    private String commandName;
    private Human humanArgument;
    private String[] creatorArgument;
    private String dataArgument;

    public Request(String commandname){
        this.commandName =commandname;
    }

    public Request(String commandname, String data){
        this.commandName =commandname;
        this.dataArgument = data;
    }

    public Request(String commandname, String[] creator){
        this.commandName =commandname;
        this.creatorArgument = creator;
    }

    public Request(String commandname, Human governor){
        this.commandName =commandname;
        this.humanArgument = governor;
    }
    public Request(String commandname, String data, String[] creator){
        this.commandName =commandname;
        this.creatorArgument = creator;
        this.dataArgument = data;
    }

    public void setCreatorArgument(String[] creatorArgument) {
        this.creatorArgument = creatorArgument;
    }

    public void setDataArgument(String dataArgument) {
        this.dataArgument = dataArgument;
    }

    public void setHumanArgument(Human humanArgument) {
        this.humanArgument = humanArgument;
    }

    public String[] getCreatorArgument() {
        return creatorArgument;
    }

    public Human getHumanArgument() {
        return humanArgument;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getDataArgument() {
        return dataArgument;
    }
    @Override
    public String toString(){
        return commandName;
    }
}
