package huanju.chen.app.domain;

import huanju.chen.app.domain.dto.*;
import huanju.chen.app.domain.vo.*;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {


    public static List<UserVO> covertToUserVoList(List<User> users) {
        List<UserVO> userVos = new ArrayList<>(users.size());
        for (User user : users) {
            userVos.add(user.covert());
        }
        return userVos;
    }

    public static List<SubjectVO> covertToSubjectVoList(List<Subject> subjects) {
        List<SubjectVO> subjectVos = new ArrayList<>(subjects.size());
        for (Subject subject : subjects) {
            subjectVos.add(subject.covert());
        }

        return subjectVos;
    }

    public static List<ProofVO> covertToProofVoList(List<Proof> proofs){
        List<ProofVO> proofVos=new ArrayList<>(proofs.size());
        for (Proof proof : proofs) {
            proofVos.add(proof.covert());
        }
        return proofVos;
    }

    public static List<ProofItemVO> covertToProofItemVOList(List<ProofItem> proofItems){
        List<ProofItemVO> proofVos=new ArrayList<>(proofItems.size());
        for (ProofItem proofItem : proofItems) {
            proofVos.add(proofItem.covert());
        }
        return proofVos;
    }

    public static List<InformationVO> covertToInformationVOList(List<Information> sourcesList){
        List<InformationVO> vos = new ArrayList<>(sourcesList.size());
        for (Information information : sourcesList) {
            vos.add(information.covert());
        }
        return vos;
    }

}
