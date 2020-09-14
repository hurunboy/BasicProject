package com.pdb.web.service.login.impl;

import com.pdb.common.StringUtils;
import com.pdb.common.VerifyCodeUtils;
import com.pdb.common.platform.Conv;
import com.pdb.common.platform.exception.ResultErrException;
import com.pdb.common.platform.response.ResponseCode;
import com.pdb.common.platform.response.ResponseModel;
import com.pdb.web.config.login.IdentityValidator;
import com.pdb.web.config.login.Principal;
import com.pdb.web.config.login.Verifier;
import com.pdb.web.config.redis.RedisFactory;
import com.pdb.web.service.login.LoginService;
import com.pdb.web.service.login.verifier.AppLoginVerifier;
import com.pdb.web.service.login.verifier.CodeVerifier;
import com.pdb.web.service.sms.SmsService;
import com.pdb.web.service.user.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/5/26   10:50
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private Environment env;

    @Autowired
    private IdentityValidator identityValidator;

    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisFactory redisFactory;

    @Override
    public ResponseModel login(String loginType, String loginName, String code) {

        Verifier verifier = null;
        if ("1".equals(loginType)) { // 密码
            verifier = new AppLoginVerifier(loginName, code);
        } else if ("2".equals(loginType)) { // 验证码
            verifier = new CodeVerifier(loginName, code);
        } else {
            throw new ResultErrException("暂不支持此登录类型！");
        }

        Principal principal = identityValidator.login(verifier);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", principal.getUserId());

        return ResponseModel.getModel(ResponseCode.SUCCESS, "登录成功！", map);
    }

    @Override
    public ResponseModel loginOut() {
        identityValidator.logout();
        return ResponseModel.getModel(ResponseCode.SUCCESS);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseModel sendLoginMsg(String phone) {
        try {
            int count = Conv.NI(redisFactory.get("login_" + phone + "_count"));
            if (count > 10) {
                throw new ResultErrException("发送短信超过最大次数限制");
            } else {
                count++;
            }
            String verifyCode = redisFactory.get("login_" + phone);
            if (!StringUtils.isNullOrEmpty(verifyCode)) {
                throw new ResultErrException("请勿频繁发送短信，稍后再试");
            }

            verifyCode = VerifyCodeUtils.generateVerifyCode(6, "1234567890");
            String content = "您的验证码是：" + verifyCode + "，3分钟内有效，请勿转发。";
            smsService.sendSmsMessage(phone, content);

            redisFactory.setex("login_" + phone, 180, verifyCode);
            redisFactory.setex("login_" + phone + "_count", 60 * 60 * 24, String.valueOf(count));
        } catch (Exception e) {
            throw new ResultErrException("发送失败:" + e.getMessage());
        }
        return ResponseModel.getModel(ResponseCode.SUCCESS, "发送成功！");
    }

}
