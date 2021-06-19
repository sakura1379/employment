package com.employ.employment.controller;

import com.employ.employment.entity.AjaxJson;
import com.employ.employment.util.UploadUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器 (基于应用服务器的文件上传)
 * @author Zenglr
 *
 */
@RestController
@RequestMapping("/upload/")
@Api
@Slf4j
@CrossOrigin
public class UploadController {

	/** 上传图片 */
	@PostMapping("image")
	public AjaxJson image(MultipartFile file){
		log.info("Start uploadImage========");
		log.info("Receive image:{}", file.toString());
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
		UploadUtil.checkFileSize(file);
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.imageSuffix);
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.imageFolder);
		return AjaxJson.getSuccessData(httpUrl);
	}

	/** 上传视频  */
	@PostMapping("video")
	public AjaxJson video(MultipartFile file){
		log.info("Start uploadVideo========");
		log.info("Receive video:{}", file.toString());
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
		UploadUtil.checkFileSize(file);
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.videoSuffix);
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.videoFolder);
		return AjaxJson.getSuccessData(httpUrl);
	}

	/** 上传音频   */
	@PostMapping("audio")
	public AjaxJson audio(MultipartFile file){
		log.info("Start uploadAudio========");
		log.info("Receive audio:{}", file.toString());
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
		UploadUtil.checkFileSize(file);
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.audioSuffix);
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.audioFolder);
		return AjaxJson.getSuccessData(httpUrl);
	}

	/** 上传apk   */
	@PostMapping("apk")
	public AjaxJson apk(MultipartFile file){
		log.info("Start uploadApk========");
		log.info("Receive apk:{}", file.toString());
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
		UploadUtil.checkFileSize(file);
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.apkSuffix);
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.apkFolder);
		return AjaxJson.getSuccessData(httpUrl);
	}

	/** 上传任意文件   */
	@PostMapping("file")
	public AjaxJson file(MultipartFile file){
		log.info("Start uploadFile========");
		log.info("Receive file:{}", file.toString());
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
		UploadUtil.checkFileSize(file);
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.fileSuffix);
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.fileFolder);
		return AjaxJson.getSuccessData(httpUrl);
	}

}
