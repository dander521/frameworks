//package com.gomcarter.frameworks.xmlexcel.download;
//
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author gomcarter 2017年12月2日 08:10:35
// */
//public interface Downloadable {
//
//    /**
//     * 实现类去完成设置下载文件名
//     *
//     * @return file name
//     */
//    String getFileName();
//
//    /**
//     * 为每一个downloadable设置一个分页者；
//     *
//     * @return （null : 表示不使用分页）
//     */
//    Pageable getPager();
//
//    /**
//     * 检测是否下载任务正在进行
//     * 进行中返回state
//     *
//     * @param params           生成文件需要的参数
//     * @param downloaderTitles 下载的titles
//     * @param extraData        额外数据
//     * @return 返回生成数据
//     * @throws IOException IOException
//     */
//    List<Map<String, Object>> generate(Map<String, Object> params, List<DownloaderTitles> downloaderTitles, Object... extraData) throws IOException;
//}
