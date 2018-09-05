package threadpool;

import java.util.concurrent.*;

/**
 * Created by shallowdream on 2018/8/3.
 */
public class RpcThreadPool {

    private static final ExecutorService EXECUTOR_SERVICE  = new ThreadPoolExecutor(0, 1000,
                                      0, TimeUnit.SECONDS,
                                      new LinkedBlockingDeque<>());

    public static ExecutorService executorService(){
        return EXECUTOR_SERVICE;
    }
}
