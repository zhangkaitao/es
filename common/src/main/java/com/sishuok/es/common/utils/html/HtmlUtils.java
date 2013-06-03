package com.sishuok.es.common.utils.html;

import com.sishuok.es.common.utils.html.jsoup.SishuokCleaner;
import com.sishuok.es.common.utils.html.jsoup.SishuokWhitelist;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * html工具类
 * 主要作用：标签转义、提取摘要、删除不安全的tag（比如<script></script>）
 */
public class HtmlUtils {

    /**
     * 获取html文档中的文本
     *
     * @return
     */
    public static String text(String html) {
        if (StringUtils.isEmpty(html)) {
            return html;
        }
        return Jsoup.parse(removeUnSafeTag(html).replace("&lt;", "<").replace("&gt;", ">")).text();
    }

    /**
     * 获取html文档中的文本 并仅提取文本中的前maxLength个 超出部分使用……补充
     *
     * @param html
     * @param maxLength
     * @return
     */
    public static String text(String html, int maxLength) {
        String text = text(html);
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "……";
    }

    /**
     * 从html中移除不安全tag
     *
     * @param html
     * @return
     */
    public static String removeUnSafeTag(String html) {
        SishuokWhitelist whitelist = new SishuokWhitelist();
        whitelist.addTags("embed", "object", "param", "span", "div", "img", "font", "del");
        whitelist.addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup");
        whitelist.addTags("dd", "dl", "dt", "em", "hr", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img");
        whitelist.addTags("li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub", "sup", "table");
        whitelist.addTags("tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul");

        //删除以on开头的（事件）
        whitelist.addAttributes(":all", "on");
        Document dirty = Jsoup.parseBodyFragment(html, "");
        SishuokCleaner cleaner = new SishuokCleaner(whitelist);
        Document clean = cleaner.clean(dirty);

        return clean.body().html();
    }

    /**
     * 删除指定标签
     *
     * @param html
     * @param tagName
     * @return
     */
    public static String removeTag(String html, String tagName) {
        Element bodyElement = Jsoup.parse(html).body();
        bodyElement.getElementsByTag(tagName).remove();
        return bodyElement.html();
    }

}
