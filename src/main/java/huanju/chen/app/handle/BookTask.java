package huanju.chen.app.handle;

import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.service.AccountBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author HuanJu
 */
public class BookTask implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(BookTask.class);

    private AccountBookService service;

    private Proof proof;

    private ProofSyncQueue queue;

    public BookTask(AccountBookService service, Proof proof) {
        this.service = service;
        this.proof = proof;
    }

    @Override
    public void run() {
        try {
            logger.info("处理" + proof.getId() + "号凭证");
            service.accountBookHandle(proof);
            logger.info(proof.getId() + "处理成功");
        }catch (RuntimeException e){
            rollback(proof.getId());
        }
    }


    private void rollback(Integer proofId){
        Proof proof=new Proof();
        proof.setId(proofId).setVerify(0).setVerifyUserId(0);
        try {
            service.handleRollBack(proof);
            logger.error(proofId+"凭证处理失败，已回滚");
        }catch (RuntimeException e){
            logger.error(proofId+"凭证处理失败，且回滚失败");
        }

    }
}
