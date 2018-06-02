package com.ww.javase;

/**
 * Created by weiwei on 2018/5/28.
 */
public class TestEnum {
    public enum BizTypeEnum {
        BIZ_TYPE_PAPER("习题考试场景", 0),
        BIZ_TYPE_QUESTIONNAIRE("问卷场景", 1);

        private String desc;
        private int value;
        BizTypeEnum(String desc, int value) {
            this.desc = desc;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static BizTypeEnum getByValue(int value) {
            for(BizTypeEnum item : values()) {
                if(item.getValue() == value) {
                    return item;
                }
            }
            return null;
        }

        public static boolean isTargetBizType(int value, BizTypeEnum bizTypeEnum){
//            return this == BIZ_TYPE_PAPER;

            BizTypeEnum query = BizTypeEnum.getByValue(value);
            if(query == null) {
                return false;
            }

            return query == bizTypeEnum;
        }

    }


    public static void main(String[] args) {

        System.out.println(BizTypeEnum.isTargetBizType(0, BizTypeEnum.BIZ_TYPE_PAPER));
        System.out.println(BizTypeEnum.isTargetBizType(1, BizTypeEnum.BIZ_TYPE_PAPER));
        System.out.println(BizTypeEnum.isTargetBizType(0, BizTypeEnum.BIZ_TYPE_QUESTIONNAIRE));
        System.out.println(BizTypeEnum.isTargetBizType(1, BizTypeEnum.BIZ_TYPE_QUESTIONNAIRE));
        System.out.println(BizTypeEnum.isTargetBizType(2, BizTypeEnum.BIZ_TYPE_PAPER));
        System.out.println(BizTypeEnum.isTargetBizType(2, BizTypeEnum.BIZ_TYPE_QUESTIONNAIRE));


//        System.out.println(BizTypeEnum.getByValue(paper).isPaper());
//        System.out.println(BizTypeEnum.getByValue(paper).isQuestionnaire());
//        System.out.println(BizTypeEnum.getByValue(questionnaire).isPaper());
//        System.out.println(BizTypeEnum.getByValue(questionnaire).isQuestionnaire());
    }


}
