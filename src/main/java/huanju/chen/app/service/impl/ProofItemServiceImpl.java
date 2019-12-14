package huanju.chen.app.service.impl;


import huanju.chen.app.dao.ProofItemMapper;
import huanju.chen.app.exception.BadCreateException;
import huanju.chen.app.model.entity.ProofItem;
import huanju.chen.app.model.entity.Subject;
import huanju.chen.app.service.ProofItemService;
import huanju.chen.app.service.SubjectService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DependsOn(value = "subjectServiceImpl")
public class ProofItemServiceImpl implements ProofItemService {

    @Resource(name = "subjectServiceImpl")
    private SubjectService subjectService;

    @Resource
    private ProofItemMapper proofItemMapper;

    @Override
    public int save(ProofItem proofItem) {
        if (proofItem.getCreditLedgerSubjectId() != null) {
            checkSubject(proofItem.getCreditLedgerSubjectId());
        }

        if (proofItem.getCreditSubSubjectId() != null) {
            checkSubject(proofItem.getCreditSubSubjectId());
        }

        if (proofItem.getDebitLedgerSubjectId() != null) {
            checkSubject(proofItem.getDebitLedgerSubjectId());
        }

        if (proofItem.getDebitSubSubjectId() != null) {
            checkSubject(proofItem.getDebitSubSubjectId());
        }

        return proofItemMapper.save(proofItem);
    }

    private void checkSubject(Integer subjectId) {
        Subject subject = subjectService.find(subjectId);
        if (subject == null || subject.getValid().equals(false)) {
            throw new BadCreateException("未选择科目/科目不可用", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<ProofItem> listByProofId(int proofId) {
        List<ProofItem> proofItems = proofItemMapper.listByProofId(proofId);
        for (int i = 0; i < proofItems.size(); i++) {
            ProofItem item = proofItems.get(i);
            Integer debitSubId = item.getDebitSubSubjectId();
            Subject debitSub = subjectService.find(debitSubId);
            item.setDebitSubSubject(debitSub);

            Integer creditSubId = item.getCreditSubSubjectId();
            Subject creditSub = subjectService.find(creditSubId);
            item.setCreditSubSubject(creditSub);

            Integer debitLedId = item.getDebitLedgerSubjectId();
            Subject debitLed = subjectService.find(debitLedId);
            item.setDebitLedgerSubject(debitLed);

            Integer creditLedId = item.getCreditLedgerSubjectId();
            Subject creditLed = subjectService.find(creditLedId);
            item.setCreditLedgerSubject(creditLed);
            proofItems.set(i, item);
        }

        return proofItems;
    }

    @Override
    public ProofItem find(Integer itemId) {
        ProofItem item = proofItemMapper.find(itemId);
        Integer debitSubId = item.getDebitSubSubjectId();
        Subject debitSub = subjectService.find(debitSubId);
        item.setDebitSubSubject(debitSub);

        Integer creditSubId = item.getCreditSubSubjectId();
        Subject creditSub = subjectService.find(creditSubId);
        item.setCreditSubSubject(creditSub);

        Integer debitLedId = item.getDebitLedgerSubjectId();
        Subject debitLed = subjectService.find(debitLedId);
        item.setDebitLedgerSubject(debitLed);

        Integer creditLedId = item.getCreditLedgerSubjectId();
        Subject creditLed = subjectService.find(creditLedId);
        item.setCreditLedgerSubject(creditLed);

        return item;
    }
}
