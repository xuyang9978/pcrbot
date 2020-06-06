package run.xuyang.pcrbot.util;

/**
 * @author XuYang
 * @date 2020/6/4 13:52
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * 将参数前后的空白字符删除
     *
     * @param msg 源字符串数组
     * @return 经过处理后的字符串数组
     */
    public static String[] trimStrArr(String[] msg) {
        String[] rst = new String[msg.length];
        for (int i = 0, j = 0; i < msg.length; i++) {
            if (msg[i].trim().length() != 0) {
                rst[j] = msg[i].trim();
                j++;
            }
        }
        return rst;
    }

    /**
     * 解析cq艾特群成员的消息得出QQ号
     *
     * @param cqMsg cq艾特群成员的消息
     * @return 被艾特的人的QQ号
     */
    public static Long parseCQGetQQNumber(String cqMsg) {
        String beginStr = "[CQ:at,qq=";
        int beginIndex = beginStr.length();
        int endIndex = cqMsg.length() - 1;
        return Long.parseLong(cqMsg.substring(beginIndex, endIndex));
    }
}
