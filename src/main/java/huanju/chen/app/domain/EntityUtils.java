package huanju.chen.app.domain;

import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.domain.dto.Subject;
import huanju.chen.app.domain.dto.User;
import huanju.chen.app.domain.vo.ProofVo;
import huanju.chen.app.domain.vo.SubjectVo;
import huanju.chen.app.domain.vo.UserVo;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {


    public static List<UserVo> covertToUserVoList(List<User> users) {
        List<UserVo> userVos = new ArrayList<>(users.size());
        for (int i = 0; i < users.size(); i++) {
            userVos.add(i, users.get(i).covert());
        }
        return userVos;
    }

    public static List<SubjectVo> covertToSubjectVoList(List<Subject> subjects) {
        List<SubjectVo> subjectVos = new ArrayList<>(subjects.size());
        for (int i = 0; i < subjects.size(); i++) {
            subjectVos.add(i, subjects.get(i).covert());
        }

        return subjectVos;
    }



    public static List<ProofVo> covertToProofVoList(List<Proof> proofs){
        List<ProofVo> proofVos=new ArrayList<>(proofs.size());
        for (int i = 0; i < proofs.size(); i++) {
            proofVos.add(i,proofs.get(i).covert());
        }

        return proofVos;
    }

}