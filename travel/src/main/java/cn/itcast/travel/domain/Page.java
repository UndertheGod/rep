package cn.itcast.travel.domain;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: zhangjiale
 * @CreateDate: 2019/3/3 13:41
 * @Version: 1.0
 */
public class Page<T> {
    private int totalpage;
    private int currentpage;
    private int rows;
    private int totalcount;
    private int beginpage;
    private int endpage;
    private List<T> list;

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getBeginpage() {
        return beginpage;
    }

    public void setBeginpage(int beginpage) {
        this.beginpage = beginpage;
    }

    public int getEndpage() {
        return endpage;
    }

    public void setEndpage(int endpage) {
        this.endpage = endpage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
