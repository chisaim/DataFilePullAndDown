DROP TABLE test.tranmessioninfo;

CREATE TABLE test.tranmessioninfo(
  filename VARCHAR(50) DEFAULT '' comment '文件名称',
  filesize varchar(50) DEFAULT '' comment '文件大小（bytes）',
  fileModifiedDate VARCHAR (20) DEFAULT '' comment'文件最后修改时间',
  exceTaskTimeAndDate VARCHAR(20) DEFAULT '999912312359' comment '任务执行时间',
  upOrDownloadFlag VARCHAR(10) DEFAULT '' comment '传输标志'
)
