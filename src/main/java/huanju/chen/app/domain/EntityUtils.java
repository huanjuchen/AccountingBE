package huanju.chen.app.domain;

import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.domain.dto.ProofItem;
import huanju.chen.app.domain.dto.Subject;
import huanju.chen.app.domain.dto.User;
import huanju.chen.app.domain.vo.ProofItemVO;
import huanju.chen.app.domain.vo.ProofVO;
import huanju.chen.app.domain.vo.SubjectVO;
import huanju.chen.app.domain.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {


    public static List<UserVO> covertToUserVoList(List<User> users) {
        List<UserVO> userVos = new ArrayList<>(users.size());
        for (int i = 0; i < users.size(); i++) {
            userVos.add(i, users.get(i).covert());
        }
        return userVos;
    }

    public static List<SubjectVO> covertToSubjectVoList(List<Subject> subjects) {
        List<SubjectVO> subjectVos = new ArrayList<>(subjects.size());
        for (int i = 0; i < subjects.size(); i++) {
            subjectVos.add(i, subjects.get(i).covert());
        }

        return subjectVos;
    }

    public static List<ProofVO> covertToProofVoList(List<Proof> proofs){
        List<ProofVO> proofVos=new ArrayList<>(proofs.size());
        for (int i = 0; i < proofs.size(); i++) {
            proofVos.add(i,proofs.get(i).covert());
        }
        return proofVos;
    }


    public static List<ProofItemVO> covertToProofItemVOList(List<ProofItem> proofItems){
        List<ProofItemVO> proofVos=new ArrayList<>(proofItems.size());
        for (int i = 0; i < proofItems.size(); i++) {
            proofVos.add(i,proofItems.get(i).covert());
        }
        return proofVos;
    }

}
