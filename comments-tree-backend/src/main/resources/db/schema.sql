-- 用户表
CREATE TABLE m_user
(
    id       BIGINT NOT NULL auto_increment unique ,
    username VARCHAR(25)    NOT NULL,
    email    VARCHAR(128)   NOT NULL,
    password VARCHAR(64)   NOT NULL,
    gmt_create   datetime         NOT NULL,--创建时间
    gmt_modified datetime         NOT NULL,--最后更新时间
    CONSTRAINT pk_m_user PRIMARY KEY (id)
);

ALTER TABLE m_user
    ADD CONSTRAINT uc_m_user_id UNIQUE (id);

-- 留言表（左右值预排序算法结构）
CREATE TABLE comment
(
    id           BIGINT       NOT NULL auto_increment unique ,
    user_id      BIGINT       NOT NULL,--提交留言的用户id
    content      VARCHAR(310) NULL,--留言内容
    lft          BIGINT       NULL,--左值
    rgt          BIGINT       NULL,--右值
    parent_id          BIGINT       NULL,--父节点id
    level        BIGINT       NULL,--当前节点层级
    gmt_create   datetime         NOT NULL,--创建时间
    gmt_modified datetime         NOT NULL,--最后更新时间
    CONSTRAINT pk_comment PRIMARY KEY (id)
);
CREATE INDEX IDX_ID on comment(id);
ALTER TABLE comment
    ADD CONSTRAINT uc_comment_id UNIQUE (id);
