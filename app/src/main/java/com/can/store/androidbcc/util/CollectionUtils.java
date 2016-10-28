
package com.can.store.androidbcc.util;

import com.can.store.androidbcc.Const.BaseEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * Provides utility methods and decorators for {@link Collection} instances.
 *
 * @since Commons Collections 1.0
 * @version $Revision: 646777 $ $Date: 2008-04-10 13:33:15 +0100 (Thu, 10 Apr 2008) $
 *
 * @author Rodney Waldhoff
 * @author Paul Jack
 * @author Stephen Colebourne
 * @author Steve Downey
 * @author Herve Quiroz
 * @author Peter KoBek
 * @author Matthew Hawthorne
 * @author Janek Bogucki
 * @author Phil Steitz
 * @author Steven Melzer
 * @author Jon Schewe
 * @author Neil O'Toole
 * @author Stephen Smith
 */
public class CollectionUtils {

    // -----------------------------------------------------------------------
    /**
     * Null-safe check if the specified collection is empty.
     * <p>
     * Null returns true.
     *
     * @param coll
     *            the collection to check, may be null
     * @return true if empty or null
     * @since Commons Collections 3.2
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * Null-safe check if the specified collection is not empty.
     * <p>
     * Null returns false.
     *
     * @param coll
     *            the collection to check, may be null
     * @return true if non-null and non-empty
     * @since Commons Collections 3.2
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Collection coll) {
        return !CollectionUtils.isEmpty(coll);
    }

    /**
     * 配列をリストに変換します。 未検証
     *
     * @param arrays
     * @return
     */
    public static <T> List<T> array4List(T[] arrays) {
        return Arrays.asList(arrays);
    }

    /**
     * 件数を取得します。
     * @param list
     * @return
     */
    public static <T> int size(List<T> list){
        if(list == null){
            return 0;
        }
        return list.size();
    }


    /**
     * 文字列を区切り文字で分割してリストで返却します。
     *
     * @param arrayStr
     * @param divideOption
     * @return
     */
    public static List<String> string4List(String arrayStr, DivideOption divideOption) {
        List<String> retList = new ArrayList<String>();

        arrayStr = arrayStr.replaceAll("\r\n", "\n");

        String[] array = arrayStr.split(divideOption.separator);
        for(String str : array) {
            str = StringUtil.trim(str);
            if(StringUtil.isNotEmpty(str)) {
                retList.add(str);
            }
        }
        return retList;
    }

    /**
     * Listを指定したサイズ毎に分割します。
     *
     * @param origin
     *            分割元のList
     * @param size
     *            Listの分割単位
     * @return サイズ毎に分割されたList。但し、Listがnullまたは空の場合、もしくはsizeが0以下の場合は空のListを返す。
     */
    public static <T> List<List<T>> divide(List<T> origin, int size) {
        if(origin == null || origin.isEmpty() || size <= 0) {
            return Collections.emptyList();
        }

        int block = origin.size() / size + (origin.size() % size > 0 ? 1 : 0);

        List<List<T>> devidedList = new ArrayList<List<T>>(block);
        for(int i = 0; i < block; i++) {
            int start = i * size;
            int end = Math.min(start + size, origin.size());
            devidedList.add(new ArrayList<T>(origin.subList(start, end)));
        }
        return devidedList;
    }


    public static <T, Y> List<Map<T, Y>> divide(Map<T, Y> origin, int size) {
        if(origin == null || origin.isEmpty() || size <= 0) {
            return Collections.emptyList();
        }

        int block = origin.size() / size + (origin.size() % size > 0 ? 1 : 0);

        List<Map<T, Y>> devidedList = new ArrayList<Map<T, Y>>(block);

        Map<T, Y> tmp = new TreeMap<>();
        int y = 0;
        for(Map.Entry<T, Y> e : origin.entrySet()) {
            tmp.put(e.getKey(), e.getValue());
            if(y % size == 0){
                devidedList.add(tmp);
                tmp = new TreeMap<>();
            }
            y++;
        }
        if(tmp.size() > 0){
            devidedList.add(tmp);
        }
        return devidedList;
    }

    public static <T> List<List<T>> divide(Set<T> origin, int size) {
        List<T> list = new ArrayList<T>(origin);
        return divide(list, size);
    }

    public static enum DivideOption implements BaseEnum {
        LINE_BREAK("改行", "\n"), COMMA("カンマ", ","), TAB("タブ", "\t"),;
        private String name;
        private String separator;

        private DivideOption(String name, String separator) {
            this.setName(name);
            this.setSeparator(separator);
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String getJsonDispName() {
            return this.getName();
        }

        @Override
        public String getJsonDescription() {
            return null;
        }

        @Override
        public String getJsonLabelClass() {
            return null;
        }

        @Override
        public String getJsonButtonClass() {
            return null;
        }

        public String getSeparator() {
            return separator;
        }

        public void setSeparator(String separator) {
            this.separator = separator;
        }

    }

}
