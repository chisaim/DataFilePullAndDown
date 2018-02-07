DROP TABLE site.mession;

CREATE TABLE site.mession(
  filename VARCHAR(30) DEFAULT '' comment '文件名称',
  filesize varchar(50) DEFAULT '' comment '文件大小',
  exceTaskTimeAndDate VARCHAR(12) DEFAULT '999912312359' comment '任务执行时间',
  upOrDownloadFlag VARCHAR(10) DEFAULT '' comment '传输标志'
)
