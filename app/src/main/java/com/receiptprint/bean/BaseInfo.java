package com.receiptprint.bean;

import java.util.List;

public class BaseInfo {


    /**
     * result_code : 00
     * result_code_msg : 成功
     * success : true
     * model : {"ip":"192.168.1.103","memo":["http://192.168.1.19:9003/nsreceipt/applicationmanag/image.htm?fileName=2.jpg","http://192.168.1.19:9003/nsreceipt/applicationmanag/image.htm?fileName=3.jpg","http://192.168.1.19:9003/nsreceipt/applicationmanag/image.htm?fileName=1.jpg"],"organization_en_Name":"831010","organization_name":"福明信用社","state":"1","term_id":"fca015ae63","term_model":{"box":"0","type":"0"}}
     * sign : b10d4aa3232324f93bb8237ed99d3fc06860ad064d66326de124d73274f9e2e60607d4b8a21d14f60aa29de5925c6341235f9fe4943a53be25b41974d94a5bbc0abd1f15c919010dfc0a657131ffd0b92391257d30ed44af9350696f05899803e1c93098518c9afc6a1f2ac416248976175d67701c9d8558229e4ed9d6b1220b
     */

    private String result_code;
    private String result_code_msg;
    private String success;
    private ModelBean model;
    private String sign;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_code_msg() {
        return result_code_msg;
    }

    public void setResult_code_msg(String result_code_msg) {
        this.result_code_msg = result_code_msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class ModelBean {
        /**
         * ip : 192.168.1.103
         * memo : ["http://192.168.1.19:9003/nsreceipt/applicationmanag/image.htm?fileName=2.jpg","http://192.168.1.19:9003/nsreceipt/applicationmanag/image.htm?fileName=3.jpg","http://192.168.1.19:9003/nsreceipt/applicationmanag/image.htm?fileName=1.jpg"]
         * organization_en_Name : 831010
         * organization_name : 福明信用社
         * state : 1
         * term_id : fca015ae63
         * term_model : {"box":"0","type":"0"}
         */

        private String ip;
        private String organization_en_Name;
        private String organization_name;
        private String state;
        private String term_id;
        private TermModelBean term_model;
        private List<String> memo;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getOrganization_en_Name() {
            return organization_en_Name;
        }

        public void setOrganization_en_Name(String organization_en_Name) {
            this.organization_en_Name = organization_en_Name;
        }

        public String getOrganization_name() {
            return organization_name;
        }

        public void setOrganization_name(String organization_name) {
            this.organization_name = organization_name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTerm_id() {
            return term_id;
        }

        public void setTerm_id(String term_id) {
            this.term_id = term_id;
        }

        public TermModelBean getTerm_model() {
            return term_model;
        }

        public void setTerm_model(TermModelBean term_model) {
            this.term_model = term_model;
        }

        public List<String> getMemo() {
            return memo;
        }

        public void setMemo(List<String> memo) {
            this.memo = memo;
        }

        public static class TermModelBean {
            /**
             * box : 0
             * type : 0
             */

            private String box;
            private String type;

            public String getBox() {
                return box;
            }

            public void setBox(String box) {
                this.box = box;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        @Override
        public String toString() {
            return "ModelBean{" +
                    "ip='" + ip + '\'' +
                    ", organization_en_Name='" + organization_en_Name + '\'' +
                    ", organization_name='" + organization_name + '\'' +
                    ", state='" + state + '\'' +
                    ", term_id='" + term_id + '\'' +
                    ", term_model=" + term_model +
                    ", memo=" + memo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "result_code='" + result_code + '\'' +
                ", result_code_msg='" + result_code_msg + '\'' +
                ", success='" + success + '\'' +
                ", model=" + model +
                ", sign='" + sign + '\'' +
                '}';
    }
}
