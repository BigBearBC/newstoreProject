package com.cy.store.commom.queryUtils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author
 */
@Slf4j
public class QueryHelpPlus {

    /**
     * 构造巨型查询条件，同时支持多字段模糊查询
     *
     * @param obj
     * @param query
     * @param <R>
     * @param <Q>
     * @return
     */
    public static <R, Q> QueryWrapper getPredicate(R obj, Q query) {
        QueryWrapper<R> queryWrapper = new QueryWrapper<R>();
        if (query == null) {
            return queryWrapper;
        }
        try {
            QueryToUnderLine clsu = query.getClass().getAnnotation(QueryToUnderLine.class);
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Query q = field.getAnnotation(Query.class);
                QueryToUnderLine fdsu = field.getAnnotation(QueryToUnderLine.class);
                if (q != null) {
                    String propName = q.propName();
                    String blurry = q.blurry();
                    String attributeName = isBlank(propName) ? field.getName() : propName;

                    //转驼峰命名，注解在属性上优先于注解在类上
                    if (toUnderLine(clsu, fdsu)) {
                        attributeName = humpToUnderline(attributeName);
                    }
                    //处理排序
                    QueryOrder queryOrder = field.getAnnotation(QueryOrder.class);
                    if (ObjectUtil.isNotNull(queryOrder)) {
                        switch (queryOrder.order()) {
                            case ASC:
                                queryWrapper.orderByAsc(attributeName);
                                break;
                            case DESC:
                                queryWrapper.orderByDesc(attributeName);
                                break;
                            default:
                                break;
                        }
                    }

                    Object val = field.get(query);
                    if (ObjectUtil.isNull(val) || "".equals(val)) {
                        continue;
                    }
                    // 模糊多字段
                    if (ObjectUtil.isNotEmpty(blurry)) {
                        String[] blurrys = blurry.split(",");
                        //queryWrapper.or();
                        queryWrapper.and(wrapper -> {
                            for (int i = 0; i < blurrys.length; i++) {
                                String column = blurrys[i];
                                //转驼峰命名，注解在属性上优先于注解在类上
                                if (toUnderLine(clsu, fdsu)) {
                                    column = humpToUnderline(blurrys[i]);
                                }
                                //if(i!=0){
                                wrapper.or();
                                //}
                                wrapper.like(column, val.toString());
                            }
                        });
                        continue;
                    }
                    String finalAttributeName = attributeName;
                    switch (q.type()) {
                        case EQUAL:
                            //queryWrapper.and(wrapper -> wrapper.eq(finalAttributeName, val));
                            queryWrapper.eq(attributeName, val);
                            break;
                        case GREATER_THAN:
                            queryWrapper.ge(finalAttributeName, val);
                            break;
                        case LESS_THAN:
                            queryWrapper.le(finalAttributeName, val);
                            break;
                        case LESS_THAN_NQ:
                            queryWrapper.lt(finalAttributeName, val);
                            break;
                        case INNER_LIKE:
                            queryWrapper.like(finalAttributeName, val);
                            break;
                        case LEFT_LIKE:
                            queryWrapper.likeLeft(finalAttributeName, val);
                            break;
                        case RIGHT_LIKE:
                            queryWrapper.likeRight(finalAttributeName, val);
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Long>) val)) {
                                queryWrapper.in(finalAttributeName, (Collection<Long>) val);
                            }
                            break;
                        case NOT_EQUAL:
                            queryWrapper.ne(finalAttributeName, val);
                            break;
                        case NOT_NULL:
                            queryWrapper.isNotNull(finalAttributeName);
                            break;
                        case BETWEEN:
                            List<Object> between = new ArrayList<>((List<Object>) val);
                            queryWrapper.between(finalAttributeName, between.get(0), between.get(1));
                            break;
                        case UNIX_TIMESTAMP:
                            List<Object> UNIX_TIMESTAMP = new ArrayList<>((List<Object>) val);
                            if (!UNIX_TIMESTAMP.isEmpty()) {
                                SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                long time1 = fm.parse(UNIX_TIMESTAMP.get(0).toString()).getTime() / 1000;
                                long time2 = fm.parse(UNIX_TIMESTAMP.get(1).toString()).getTime() / 1000;
                                queryWrapper.between(finalAttributeName, time1, time2);
                            }
                            break;
                        default:
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return queryWrapper;
    }

    private static boolean toUnderLine(QueryToUnderLine clsu, QueryToUnderLine fdsu) {
        //默认需要转驼峰
        boolean flag = true;
        if (clsu != null && !clsu.toUnderLine()) {
            flag = false;
        }
        if (fdsu != null) {
            if (!fdsu.toUnderLine()) {
                flag = false;
            } else {
                flag = true;
            }
        }
        return flag;
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        if (!para.contains("_")) {
            for (int i = 0; i < para.length(); i++) {
                if (Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString();
    }
}
