package huanju.chen.app.service;

import huanju.chen.app.model.entity.Examination;

public interface ExaminationService {


    int save(Examination examination);

    Examination find(Integer id);
}
