package com.zzspace.pojo;

import org.hibernate.validator.constraints.Length;

public class User {
    @Length(min = 2,max = 4, message = "{error.name.length}")
    String name="{\"instSerialNo\":\"20210405ZAOL000000000696923741\",\"syncNode\":\"claim_report_info_update\",\"reportNo\":\"202104051100300600610687689843\",\"prodNo\":\"20156\",\"policyNo\":\"20121873749859410919\",\"outPolicyNo\":\"825090106708103860\",\"reportChannel\":\"1\",\"claimApplyTime\":\"20210405205907\",\"claimerCertName\":\"\",\"claimerCertType\":\"100\",\"claimerCertNo\":\"\",\"claimerPhone\":\"\",\"claimApplyAmount\":\"\",\"accidentPersonAccountType\":\"1\",\"accidentPersonCertName\":\"\",\"accidentPersonCertType\":\"100\",\"accidentPersonCertNo\":\"\",\"accidentPersonPhone\":\"\",\"accidentAddress\":\"\",\"accidentDesc\":\"\",\"accidentTime\":\"20210405205907\",\"bizData\":{\"orderTime\":\"2021-04-05 21:08:05\",\"screenMaintainer\":\"{\\\"addressCode\\\":\\\"440112\\\",\\\"addressDetail\\\":\\\"广州市黄埔区东乐路1号（京东华南物流中心博展A园区众安维修中心）\\\",\\\"city\\\":\\\"广州市\\\",\\\"name\\\":\\\"李钊雄（京东维修）\\\",\\\"phone\\\":\\\"17675777194\\\",\\\"province\\\":\\\"广东省\\\",\\\"region\\\":\\\"黄埔区\\\"}\",\"screenUser\":\"{\\\"addressCode\\\":\\\"421202\\\",\\\"addressDetail\\\":\\\"湖北省 咸宁市 咸安区 湖北省-咸宁市-咸安区-温泉街道温泉塘角路星辰花园2-1-1402\\\",\\\"city\\\":\\\"咸宁市\\\",\\\"name\\\":\\\"马莹\\\",\\\"phone\\\":\\\"18871535550\\\",\\\"province\\\":\\\"湖北省\\\",\\\"region\\\":\\\"咸安区\\\"}\"},\"rejectTime\":\"\",\"rejectReason\":\"\",\"cancelTime\":\"\",\"cancelReason\":\"\",\"claimRecordTime\":\"\",\"claimNo\":\"202104051100300309190687343892\",\"claimReportAcceptType\":\"\",\"claimFee\":\"\",\"claimAssessTime\":\"\",\"billAccountType\":\"\",\"billAccountNo\":\"\",\"idCardName\":\"\",\"idCardType\":\"\",\"idCardNo\":\"\",\"recoverTime\":\"\",\"recoverReason\":\"\",\"resolveTime\":\"\",\"resolveReason\":\"\",\"claimerUid\":\"2088902675900183\",\"accidentPersonUid\":\"2088902675900183\",\"claimerAccountType\":\"1\",\"billList\":[],\"attachmentList\":[],\"accidentResultInfo\":{\"accidentLiabilityCode\":\"L20200507001\",\"uploadFlowId\":\"2021040500098101000123301722812\"},\"claimBenefitList\":[]}";
    String pswd;

    public User() {
    }

    public User(String name, String pswd) {
        this.name = name;
        this.pswd = pswd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
