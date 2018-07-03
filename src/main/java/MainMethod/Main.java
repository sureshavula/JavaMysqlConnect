package MainMethod;
import MysqlResult.ExecuteQueries;
import StartService.Beginner;
public class Main {
    public static void main(String args[]){
        Beginner bg = new Beginner();
        bg.startService();
        ExecuteQueries eq = new ExecuteQueries();
        eq.executeQueries(args[0]);

    }
}
