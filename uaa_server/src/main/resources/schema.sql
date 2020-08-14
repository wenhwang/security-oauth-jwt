CREATE DATABASE `SYS_AUTH` CHARACTER SET utf8 COLLATE utf8_general_ci;

create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY comment '客户端ID',
  resource_ids VARCHAR(256) comment '授权资源标识集合',
  client_secret VARCHAR(256) comment '客户端密钥',
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256) comment '客户端支持的授权类型',
  web_server_redirect_uri VARCHAR(256) comment '授权成功跳转地址',
  authorities VARCHAR(256) comment '客户端允许的权限',
  access_token_validity INTEGER comment 'token有效期',
  refresh_token_validity INTEGER comment '刷新token有效期',
  additional_information VARCHAR(4096) ,
  autoapprove VARCHAR(256) comment '授权自动完成'
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONGVARBINARY,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication LONGVARBINARY
);

create table oauth_code (
  code VARCHAR(256), authentication LONGVARBINARY
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
  appId VARCHAR(256) PRIMARY KEY,
  resourceIds VARCHAR(256),
  appSecret VARCHAR(256),
  scope VARCHAR(256),
  grantTypes VARCHAR(256),
  redirectUrl VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(256)
);