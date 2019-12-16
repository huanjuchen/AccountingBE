package huanju.chen.app.test.service;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.model.entity.Proof;
import huanju.chen.app.model.entity.ProofItem;
import huanju.chen.app.service.ProofService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ProofTest {


    @Resource(name = "proofServiceImpl")
    ProofService proofService;

    @Test
    public void saveTest(){
//        Proof proof=new Proof();
//
//        proof.setDate(new Date());
//        proof.setInvoiceCount(2);
//        proof.setManager("Manager1");
//        proof.setCollection("hhhh");
//
//        proof.setRecorderId(2);
//
//        proof.setCashier("CashierA");
//
//        proof.setPayer("PayerA");
//
//        ProofItem item1=new ProofItem();
//        item1.setAbstraction("ProofItem1");
//        item1.setCreditLedgerSubjectId(1001);
//        item1.setDebitSubSubjectId(1002);
//        item1.setCreditSubSubjectId(2001);
//        item1.setDebitLedgerSubjectId(5001);
//        item1.setMoney(new BigDecimal(1999.99));
//
//
//        ProofItem item2=new ProofItem();
//        item2.setAbstraction("ProofItem2");
//        item2.setCreditLedgerSubjectId(1002);
//        item2.setDebitSubSubjectId(1001);
//        item2.setCreditSubSubjectId(5001);
//        item2.setDebitLedgerSubjectId(2001);
//        item2.setMoney(new BigDecimal(2999));
//
//        List<ProofItem> items=new ArrayList<>(2);
//        items.add(item1);
//        items.add(item2);
//
//        proof.setItems(items);
//        JSON.toJSONString(proof);
//
//
//        proofService.save(proof);



    }

}
