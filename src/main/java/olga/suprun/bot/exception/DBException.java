package olga.suprun.bot.exception;

public class DBException extends Exception{
    public DBException(){};
    public DBException(String message){
        super(message);
    };
}
