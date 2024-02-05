package tasklist;

import tasklist.commands.*;

public class Parser {
    public enum Commands {
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        LIST,
        BYE,
        HELPG,
        ADD,
        UNKNOWN, FIND
    }

    private static Commands getCommand(String commandString) {
        try {
            return Commands.valueOf(commandString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Commands.UNKNOWN;
        }
    }

    public static Command parseCommand(String userInput) {
        String[] inputList = userInput.split(" ", 2);
        if (inputList.length != 1) { // indicates one of the other commands
            switch (getCommand(inputList[0])) {
			case FIND: 
				return new FindCommand(inputList[1]);
				
            case MARK:
                try {
                    return new MarkCommand(Integer.parseInt(inputList[1]), true);
                } catch (NumberFormatException e) {
                    return new InvalidCommand("Error: Input is not a valid numeric value.");
                }

            case UNMARK:
                try {
                    return new MarkCommand(Integer.parseInt(inputList[1]), false);
                } catch (NumberFormatException e) {
                    return new InvalidCommand("Error: Input is not a valid numeric value.");
                }

            case TODO:
                return new AddCommand(inputList[1], 1);

            case DEADLINE:
                return new AddCommand(inputList[1], 2);

            case EVENT:
                return new AddCommand(inputList[1], 3);

            case DELETE:
                try {
                    return new DeleteCommand(Integer.parseInt(inputList[1]));
                } catch (NumberFormatException e) {
                    return new InvalidCommand("Error: Input is not a valid numeric value.");
                }

            case ADD:
                return new AddCommand(inputList[1], 0);

            case BYE:
            case LIST:
            case HELPG:
                return new InvalidCommand(
                        "The command '"
                                + userInput
                                + "' is not recognized. \nDid you mean: "
                                + inputList[0]
                                + "?");

            default:
                return new InvalidCommand("Invalid command: " + inputList[0] + "\nPlease try again.");
            }
        } else {
            switch (getCommand(userInput)) {
            case BYE:
                return new ExitCommand();
                
            case LIST:
                return new ListCommand();

            case HELPG:
                return new HelpCommand();

            default:
                return new InvalidCommand("Invalid command: " + userInput + "\nPlease try again.");
            }
        }
    }
}
