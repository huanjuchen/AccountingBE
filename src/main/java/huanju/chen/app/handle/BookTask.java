package huanju.chen.app.handle;

import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.service.AccountBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author HuanJu
 */
public class BookTask implements Callable<Boolean> {

    private final static Logger logger= LoggerFactory.getLogger(BookTask.class);

    private AccountBookService service;

    private Proof proof;

    public BookTask(AccountBookService service, Proof proof) {
        this.service = service;
        this.proof = proof;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            service.AccountBookHandle(proof);
        }catch (Exception e){
            logger.error("凭证"+proof.getId()+"处理出现了异常");
            return false;
        }
        return true;
    }
}
