// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.dto;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
public class SearchDTO {

    private int pageIndex;
    private String searchTerm;

    /**
     * DOC crazyLau Comment method "setPageIndex".
     * 
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;

    }

    /**
     * DOC crazyLau Comment method "setSearchTerm".
     * 
     * @param searchTerm
     */
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;

    }

    /**
     * DOC crazyLau Comment method "getSearchTerm".
     * @return
     */
    public String getSearchTerm() {
        return this.searchTerm;
    }

}
