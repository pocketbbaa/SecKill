package exception;

/**
 * 重复秒杀异常
 * Created by admin on 2016/6/6.
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
