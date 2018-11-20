package com.stone.generator.control;

import com.stone.generator.pojo.request.GeneratorProjectRequestDTO;
import com.stone.generator.pojo.request.GeneratorRequestDTO;
import com.stone.generator.service.SysGeneratorService;
import com.stone.generator.pojo.Query;
import com.stone.generator.pojo.ResultBean;
import com.stone.generator.pojo.request.ListTableRequestDTO;
import com.stone.generator.pojo.vo.PageTableVO;
import com.stone.generator.pojo.vo.TableVO;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

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

    @SneakyThrows
    @GetMapping(value = "/{tableName}")
    public void download(@PathVariable("tableName") String tableName, GeneratorRequestDTO requestDTO, HttpServletResponse response) {
        Query query = new Query();
        query.put("tableName", tableName);
        query.put("groupId",requestDTO.getPackageName());
        query.put("artifactId",requestDTO.getModuleName());
        byte[] data = sysGeneratorService.download(query);
        @Cleanup OutputStream outputStream = response.getOutputStream();
        response.reset();
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s.zip", tableName));
        response.addHeader("Content-Length", String.format("%s", data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, outputStream);
    }

    @SneakyThrows
    @GetMapping(value = "/project")
    public void downloadProject(GeneratorProjectRequestDTO requestDTO,HttpServletResponse response) {
        byte[] data = sysGeneratorService.downloadProject(requestDTO);
        @Cleanup OutputStream outputStream = response.getOutputStream();
        response.reset();
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s.zip", requestDTO.getModuleName()));
        response.addHeader("Content-Length", String.format("%s", data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, outputStream);
    }
}
