package com.webank.cmdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webank.cmdb.dto.IdNamePairDto;
import com.webank.cmdb.dto.IntQueryResponseHeader;
import com.webank.cmdb.dto.IntegrationQueryDto;
import com.webank.cmdb.dto.QueryRequest;
import com.webank.cmdb.dto.QueryResponse;
import com.webank.cmdb.service.CiService;
import com.webank.cmdb.service.IntegrationQueryService;

@RestController
public class IntQueryController {
    @Autowired
    private IntegrationQueryService intQueryService;
    @Autowired
    private CiService ciService;

    @GetMapping("/intQuery/ciType/{ciTypeId}/{queryId}")
    public IntegrationQueryDto getIntQueryByName(@PathVariable("ciTypeId") Integer ciTypeId, @PathVariable("queryId") int queryId) {
        return intQueryService.getIntegrationQuery(queryId);
    }

    @PostMapping("/intQuery/ciType/{ciTypeId}/{queryName}/save")
    public int saveIntQuery(@PathVariable("ciTypeId") Integer ciTypeId, @PathVariable("queryName") String queryName, @RequestBody IntegrationQueryDto intQueryDto) {
        return intQueryService.createIntegrationQuery(ciTypeId, queryName, intQueryDto);
    }

    @PostMapping("/intQuery/{queryId}/update")
    public void updateIntQuery(@PathVariable("queryId") int queryId, @RequestBody IntegrationQueryDto intQueryDto) {
        intQueryService.updateIntegrationQuery(queryId, intQueryDto);
    }

    @PostMapping("/intQuery/ciType/{ciTypeId}/{queryId}/delete")
    public void deleteQuery(@PathVariable("ciTypeId") Integer ciTypeId, @PathVariable("queryId") int queryId) {
        intQueryService.deleteIntegrationQuery(queryId);
    }

    @GetMapping("/intQuery/ciType/{ciTypeId}/search")
    public List<IdNamePairDto> searchIntQuery(@PathVariable("ciTypeId") Integer ciTypeId, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "tailAttrId", required = false) Integer tailAttrId) {
        return intQueryService.findAll(ciTypeId, name, tailAttrId);
    }

    @PostMapping("/intQuery/{queryId}/execute")
    public QueryResponse queryInt(@PathVariable("queryId") int queryId, @RequestBody QueryRequest queryRequest) {
        return ciService.integrateQuery(queryId, queryRequest);
    }

    @GetMapping("/intQuery/{queryId}/header")
    public List<IntQueryResponseHeader> queryIntHeader(@PathVariable("queryId") int queryId) {
        return ciService.integrateQueryHeader(queryId);
    }
}
