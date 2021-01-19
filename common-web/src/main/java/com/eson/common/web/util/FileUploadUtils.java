package com.eson.common.web.util;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eson.common.core.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dengxiaolin
 * @since 2021/01/18
 */
@Slf4j
public class FileUploadUtils {
    /**
     * 获取单个上传的文件,left is file, right is fileName
     */
    public static Pair<MultipartFile, String> getUploadSingleFilePair(MultipartHttpServletRequest request,
                                                                      Long sizeLimit,
                                                                      String sizeErrMsg,
                                                                      Pattern formatPattern,
                                                                      String patternErrMsg) {
        Map<String, MultipartFile> uploadFileMap = request.getFileMap();
        Assert.throwIfEmpty(uploadFileMap, "上传文件不能为空");
        Assert.throwIfTrue(uploadFileMap.size() > 1, "只能上传一个文件");

        Map.Entry<String, MultipartFile> uploadEntry = uploadFileMap.entrySet().iterator().next();
        MultipartFile uploadFile = uploadEntry.getValue();

        Assert.throwIfNull(uploadFile, "上传文件不能为空");
        log.info("文件大小:" + uploadFile.getSize());

        if (sizeLimit != null) {
            sizeErrMsg = StringUtils.isBlank(sizeErrMsg) ? "上传文件过大!" : sizeErrMsg;
            Assert.throwIfTrue(uploadFile.getSize() > sizeLimit, sizeErrMsg);
        }

        String originalFileName = request.getParameter("originalFileName");
        originalFileName = StringUtils.isBlank(originalFileName) ? uploadFile.getOriginalFilename() : originalFileName;
        Assert.throwIfBlank(originalFileName, "获取不到文件名");

        if (formatPattern != null) {
            patternErrMsg = StringUtils.isBlank(patternErrMsg) ? "文件格式错误" : patternErrMsg;
            Assert.throwIfTrue(!formatPattern.matcher(originalFileName.toLowerCase()).matches(), patternErrMsg);
        }

        return Pair.of(uploadFile, originalFileName);
    }
}
