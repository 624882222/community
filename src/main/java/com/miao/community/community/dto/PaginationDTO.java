package com.miao.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionDTO> questionDTOList;

    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private Integer page;

    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;


    public void setPagination(Integer page, Integer size, Integer totalCount) {


        // 计算一共多少页
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1){
            page = 1;
        }

        if (page > totalPage){
            page = totalPage;
        }

        this.page = page;

        pages.add(page);

        for (int i = 1; i < 3; i++){
            if (page - i > 0){
                pages.add(0,page - i);
            }
            if (page +i <= totalPage){
                pages.add(page + i);
            }
        }

        // 是否显示上一页按钮
        if (page != 1) {
            showPrevious = true;
        } else {
            showPrevious = false;
        }

        // 是否显示下一页按钮
        if (page != totalPage) {
            showNext = true;
        } else {
            showNext = false;
        }

        // 是否显示第一页按钮
        if (pages.contains(1)){
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        // 是否显示最后一页按钮
        if (pages.contains(totalPage)){
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
