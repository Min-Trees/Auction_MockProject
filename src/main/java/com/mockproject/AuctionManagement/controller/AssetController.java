package com.mockproject.AuctionManagement.controller;

import com.mockproject.AuctionManagement.dto.request.AssetRequestDTO;
import com.mockproject.AuctionManagement.dto.response.AssetResponseDTO;
import com.mockproject.AuctionManagement.dto.response.PageResponse;
import com.mockproject.AuctionManagement.dto.response.ResponseData;
import com.mockproject.AuctionManagement.dto.response.ResponseError;
import com.mockproject.AuctionManagement.service.AssetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;

    @PostMapping()
    public ResponseData<?> addAsset(@RequestPart(value = "images", required = false) List<MultipartFile> images,
                                    @RequestPart(value = "videos", required = false) MultipartFile videos,
                                    @RequestPart(value = "files", required = false) MultipartFile files,
                                    @Valid @ModelAttribute AssetRequestDTO request) {
        try{
            AssetResponseDTO response = assetService.addAsset(request, images, files, videos);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Asset added successfully", response);
        }catch (Exception e){
            log.error("errorMessage{}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "add asset failed");
        }
    }

    @GetMapping("/{idAsset}")
    public ResponseData<?> getAssetById(@PathVariable() Long idAsset) {
        try{
            AssetResponseDTO response = assetService.getAssetById(idAsset);
            return new ResponseData<>(HttpStatus.OK.value(), "Asset get successfully", response);
        }catch (Exception e){
            log.error("errorMessage{}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get asset by id failed");
        }
    }

    @GetMapping()
    public ResponseData<?> getAllAssets(@Min(1) @RequestParam(required = false, defaultValue = "1") int pageNo,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try{
            return new ResponseData<>(HttpStatus.OK.value(), "Asset get successfully", assetService.getAllAssets(pageNo, pageSize));
        }catch (Exception e){
            log.error("errorMessage{}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get all assets failed");
        }
    }

    @GetMapping("/sellers/{idSeller}")
    public ResponseData<?> getAllAssetsByIdSeller(@PathVariable() int idSeller,
                                                  @Min(1) @RequestParam(required = false, defaultValue = "1") int pageNo,
                                                  @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try{
            return new ResponseData<>(HttpStatus.OK.value(), "Asset get successfully", assetService.getAllAssetByIdSeller(idSeller, pageNo, pageSize));
        }catch (Exception e){
            log.error("errorMessage{}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "get all assets failed");
        }
    }

    @DeleteMapping("/{idAsset}")
    public ResponseData<?> deleteAssetById(@PathVariable Long idAsset) {
        try{
            assetService.deleteAssetById(idAsset);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete successfully");
        }catch (Exception e){
            log.error("errorMessage{}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete assets failed");
        }
    }

    @PatchMapping("/{idAsset}")
    public ResponseData<?> updateAssetStatus(@PathVariable Long idAsset) {
        try{
            assetService.updateAssetStatus(idAsset);
            return new ResponseData<>(HttpStatus.OK.value(), "PENDING REVIEW successfully");
        }catch (Exception e){
            log.error("errorMessage{}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "PENDING REVIEW failed");
        }
    }

    @PutMapping("/{idAsset}")
    public ResponseData<?> updateAsset(@PathVariable Long idAsset,
                                       @Valid @ModelAttribute AssetRequestDTO request,
                                       @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                       @RequestPart(value = "videos", required = false) MultipartFile videos,
                                       @RequestPart(value = "files", required = false) MultipartFile files) {
        try{
            return new ResponseData<>(HttpStatus.OK.value(), "Update successfully", assetService.updateAsset(idAsset, request, images, videos, files));
        }catch (Exception e){
            log.error("errorMessage{}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update failed");
        }
    }

}
