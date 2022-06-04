package utility;

import java.util.Arrays;
import java.util.HashSet;

public class CommandManager {
    private static final HashSet<String> dataCommand = new HashSet<>(Arrays.asList(new String[]{"open", "remove_key", "remove_all_by_government"}));
    private static final HashSet<String> creatorCommand = new HashSet<>(Arrays.asList( new String[]{"remove_lower", "insert", "update", "replace_if_grater"}));
    private static final HashSet<String> humanCommand = new HashSet<>(Arrays.asList( new String[]{"filter_less_than_governor"}));
    private static final HashSet<String> bareCommand = new HashSet<>(Arrays.asList( new String[]{"help", "exit", "show", "clear", "print_decending", "history", "info"}));

    public static Request execute(String commandname, String data) {
        Request request = null;
        if (!commandname.equals("")){
            if (dataCommand.contains(commandname)) {
                request = new Request(commandname);
                request.setDataArgument(data);
            } else if (creatorCommand.contains(commandname)) {
                request = new Request(commandname);
                try {
                    Long.parseLong(data);
                    request.setCreatorArgument(CollectingDataForCityCreator.execute(data));
                } catch (NumberFormatException e) {
                    request.setCreatorArgument(CollectingDataForCityCreator.execute());
                }
            } else if (humanCommand.contains(commandname)) {
                request = new Request(commandname);
                request.setHumanArgument(CollectingDataForGovernor.execute());
            } else if (bareCommand.contains(commandname)) {
                request = new Request(commandname);
            } else System.out.println("Такой команды нет, для списка команд напишите help");
        }
        return request;
    }
}
