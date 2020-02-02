package huanju.chen.app.controller;

import huanju.chen.app.domain.RespResult;
import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.ProofService;
import huanju.chen.app.domain.EntityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProofController {

    @Resource(name = "proofServiceImpl")
    private ProofService proofService;



    @PostMapping("/proof")
    public ApiResult createProof(@Validated @RequestBody Proof proof, HttpServletRequest request) {
        String tokenId=request.getHeader("token_id");
        proofService.save(proof,tokenId);
        return ApiResult.success();
    }





}
