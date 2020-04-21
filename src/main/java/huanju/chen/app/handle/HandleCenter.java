package huanju.chen.app.handle;

import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.service.AccountBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author HuanJu
 */
@Component
public class HandleCenter {

    private final static Logger logger = LoggerFactory.getLogger(HandleCenter.class);

    private final ProofSyncQueue queue = ProofSyncQueue.getInstance();

    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    ExecutorService executorService= new ThreadPoolExecutor(5,5,
            60L, TimeUnit.SECONDS
            ,new ArrayBlockingQueue<>(5),new ThreadPoolExecutor.AbortPolicy());


    private AccountBookService service;


    @Resource
    public void setService(AccountBookService service) {
        this.service = service;
    }

    {
        new Thread(()->{
            logger.info("处理线程已经启动");
            this.handle();
        }, "lookUp" ).start();
    }

    public void wakeHandle() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }

    }


    public void handle() {
        lock.lock();
        try {
          while (true){
              while (queue.isEmpty()){
                  condition.await();
              }
              Proof proof=queue.get();
              BookTask task=new BookTask(service,proof);
              try {
                  executorService.execute(task);
                  queue.remove();
              }catch (RejectedExecutionException e){
                  condition.await();
              }
          }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            lock.unlock();
        }
    }
}
