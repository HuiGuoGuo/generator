package com.stone.generator;

import com.stone.generator.pojo.request.GeneratorProjectRequestDTO;
import com.stone.generator.service.SysGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorApplicationTests {

    @Autowired
    private SysGeneratorService sysGeneratorService;

    @Test
    public void test()  throws Exception{
        String format = String.format("attachment; filename=%s%s", System.currentTimeMillis(), ".xlsx");
        System.out.println(format);
//        System.out.println(anInt);
    }

    @Test
    public void testDownloadProjct(){
        GeneratorProjectRequestDTO requestDTO = new GeneratorProjectRequestDTO();
        requestDTO.setGroupId("com.stone").setArtifactId("project").setVersion("1.0").setModuleName("project");
        byte[] bytes = sysGeneratorService.downloadProject(requestDTO);
    }
}
