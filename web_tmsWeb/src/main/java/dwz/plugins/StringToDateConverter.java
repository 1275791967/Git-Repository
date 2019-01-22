package dwz.plugins;


import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import dwz.common.util.time.DateUtils;

/**
 * 提交controller控制器的日期与字符串格式转换模式
 * @author lvlei
 *
 */
public class StringToDateConverter implements Converter<String,Date>{
    @Override
    public Date convert(String source) {
        Date date = null;
        try {
            date = DateUtils.parseDate(source, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" });
            System.out.println("datePlugins: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
