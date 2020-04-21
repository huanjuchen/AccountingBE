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

    private Logger logger = LoggerFactory.getLogger(HandleCenter.class);

    private ProofSyncQueue queue = ProofSyncQueue.getInstance();

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private ExecutorService executorService= new ThreadPoolExecutor(5,5,
            60L, TimeUnit.SECONDS
            ,new ArrayBlockingQueue<>(5),new ThreadPoolExecutor.AbortPolicy());

    private AccountBookService service;


    @Resource
    public void setService(AccountBookService service) {
        this.service = service;
    }

    {
        this.handle();
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
              FutureTask<Boolean> bookTask=new FutureTask<>(task);
              try {
                  executorService.execute(bookTask);
                  queue.remove();
              }catch (RejectedExecutionException e){
                  condition.await();
              }
          }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
