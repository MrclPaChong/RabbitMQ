package com.weirdo.example.email;


import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/14 23:01
 */
public class E {

    public static void main(String[] args) {
        /**
         *报错：Exception in thread "main" cn.hutool.extra.mail.MailException: AuthenticationFailedException: 535 Error: authentication failed
         * https://www.cnblogs.com/mikemhm/p/11811647.html
         * 申请授权码：https://jingyan.baidu.com/article/4853e1e551c0441908f7264b.html
         */

        MailAccount account = new MailAccount();
        account.setHost("smtp.yeah.net");
        account.setPort(465);
        account.setSslEnable(true);
        account.setFrom("dchenlei@yeah.net");
        account.setUser("dchenlei");
        //授权码
        account.setPass("MHUORUOCEPCTDMIK1");
        MailUtil.send(account, "961898003@qq.com", "测试", "<h1>邮件来自Hutool测试</h1>", true);
    }
}
