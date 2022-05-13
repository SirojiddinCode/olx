package com.company.controller.adminController;

import com.company.dto.RegionDTO;
import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/region")
@Api(tags = "Region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("/by_admin/create_region")
    @ApiOperation(value = "create region method for Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200,message = "Successfull",response =RegionDTO.class)
    })
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO dto, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.ADMIN);
        RegionDTO result = regionService.createRegion(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/by_admin/update_region")
    @ApiOperation(value = "update region method for admin")
    @ApiResponse(code = 200,message = "Successful",response = RegionDTO.class)
    public ResponseEntity<RegionDTO> updateRegion(@RequestBody RegionDTO dto,HttpServletRequest request){
        JwtUtil.getProfile(request,ProfileRole.ADMIN);
        RegionDTO result=regionService.updateRegion(dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/by_admin/delete_region/{id}")
    @ApiOperation("region delete method")
    @ApiResponse(code = 200,message = "Successful",response = String.class)
    public ResponseEntity<String> deleteRegion(@ApiParam(value = "Id of Region",readOnly = true)
                                                   @PathVariable("id")Integer id, HttpServletRequest request){
       JwtUtil.getProfile(request,ProfileRole.ADMIN);
       regionService.deleteRegion(id);
       return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/get_byId")
    public ResponseEntity<RegionDTO> getById(@RequestParam("id") Integer regionId,
                                             @RequestParam("language")Language language){
       RegionDTO dto=regionService.getById(regionId,language);
       return ResponseEntity.ok(dto);
    }

    @GetMapping("/get_all_region")
    public ResponseEntity<List<RegionDTO>> getAllRegion(@RequestParam("language")Language language){
        List<RegionDTO> dtoList=regionService.getRegionAll(language);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/get_by_id/regionlist_or_region")
    public  ResponseEntity<List<RegionDTO>> getAllRegionById(@RequestParam("id") Integer regionId,
                                                                @RequestParam("id")Language language){
        List<RegionDTO> regionDTOList=regionService.getRegionListById(regionId,language);
        return ResponseEntity.ok(regionDTOList);
    }

    @GetMapping("/get_region/like")
    public ResponseEntity<List<RegionDTO>> getRegionList(@RequestParam("name")String name,
                                                         @RequestParam("language") Language language){
        List<RegionDTO> dtoList=regionService.likeName(name,language);
        return ResponseEntity.ok(dtoList);
    }

}
