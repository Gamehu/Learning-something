package com.elane.common.utils.sql;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.jsqlparser.JSQLParserException;

public class SqlParserUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetParsedSql() throws JSQLParserException {
        String testSql = "select c1,c2,c3     from    t1,t2 where condi3=3 " + "\n" + "    or condi4=5 order by o1,o2";
        testSql = "SELECT                                    "
                +"  su.login_id AS 登陆帐号,                "
                +"  su.name AS 用户昵称,                    "
                +"  su.fact_name AS 中文名称,               "
                +"  su.company AS 公司名称,                 "
                +"  su.company_short_name AS 公司简称,      "
                +"  su.audit_time AS 审核时间,              "
                +"  CASE                                    "
                +"    WHEN su.user_type = 0                 "
                +"    THEN '公司'                           "
                +"    WHEN su.user_type = 1                 "
                +"    THEN '车主'                           "
                +"    WHEN su.user_type = 2                 "
                +"    THEN '挂靠车队'                       "
                +"    WHEN su.user_type = 3                 "
                +"    THEN '货代'                           "
                +"    ELSE '管理员'                         "
                +"  END AS 用户类别,                        "
                +"  CASE                                    "
                +"    WHEN sue.level = 1                    "
                +"    THEN '游客'                           "
                +"    WHEN sue.level = 10                   "
                +"    THEN '试用客户'                       "
                +"    WHEN sue.level = 20                   "
                +"    THEN '普通会员'                       "
                +"    WHEN sue.level = 30                   "
                +"    THEN 'VIP会员'                        "
                +"    WHEN sue.level = 100                  "
                +"    THEN '维修中...'                      "
                +"    ELSE sue.level                        "
                +"  END AS 用户级别,                        "
                +"  CASE                                    "
                +"    WHEN su.credit_level = 1              "
                +"    THEN 'QQ'                             "
                +"    WHEN su.credit_level = 2              "
                +"    THEN '普通'                           "
                +"    WHEN su.credit_level = 3              "
                +"    THEN '良好'                           "
                +"    WHEN su.credit_level = 4              "
                +"    THEN '优质'                           "
                +"    ELSE su.credit_level                  "
                +"  END AS 诚信级别,                        "
                +"  su.integral AS 当前积分,                "
                +"  su.integral_history AS 历史积分,        "
                +"  su.telephone AS 联系电话,               "
                +"  su.mobile_telephone AS 手机号码,        "
                +"  su.qq AS QQ,                            "
                +"  su.address AS 公司地址,                 "
                +"  su.create_time AS 注册时间,             "
                +"  su.login_time AS 登陆时间               "
                +"FROM                                      "
                +"  sys_user su                             "
                +"  LEFT JOIN sys_company sc                "
                +"    ON sc.id = su.company_id              "
                +"  LEFT JOIN sys_user_extend sue           "
                +"    ON sue.user_id = su.id                "
                +"WHERE su.login_time >= @start_year        "
                +"  ORDER BY su.integral_history DESC       ";
        SqlParserUtil test = new SqlParserUtil();
        List<String> result = null;
        result = test.getTableNameBySql(testSql);
        System.out.println(result.toString());
    }

    @Test
    public void testGetParsedSqlList() throws JSQLParserException {
        String str = "";
        String testSql = "ALTER TABLE table_name DROP COLUMN column_name";
        str = SqlParserUtil.getSQLTypeBySql(testSql);
        System.out.println(str);
    }

}
