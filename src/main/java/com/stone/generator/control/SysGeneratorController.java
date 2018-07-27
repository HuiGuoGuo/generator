package com.stone.generator.control;

import com.stone.generator.SysGeneratorService;
import com.stone.generator.pojo.PageResultBean;
import com.stone.generator.pojo.Query;
import com.stone.generator.pojo.request.BaseRequestDTO;
import com.stone.generator.pojo.ResultBean;
import com.stone.generator.pojo.request.ListTableRequestDTO;
import com.stone.generator.pojo.vo.PageTableVO;
import com.stone.generator.pojo.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Stone on 2018/7/27.
 */

@RestController
@RequestMapping("/sys/generator")
public class SysGeneratorController {

    @Autowired
    private SysGeneratorService sysGeneratorService;

    @GetMapping(value = "/listTable")
    public ResultBean<PageTableVO> listTable(ListTableRequestDTO requestDTO) {
        Query query = new Query(requestDTO);
        ResultBean<List<TableVO>> listResultBean = sysGeneratorService.listTable(query);
        ResultBean<Integer> countTalbe = sysGeneratorService.countTalbe(query);
        PageTableVO pageTableVO = new PageTableVO(query.getPage(), query.getPageSize(), countTalbe.getData(),
                listResultBean.getData());
        return ResultBean.create().setData(pageTableVO);
    }
}
