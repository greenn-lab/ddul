DROP TABLE IF EXISTS MENU CASCADE;
CREATE TABLE IF NOT EXISTS MENU
(
    ID       BIGINT PRIMARY KEY,
    UPPER_ID BIGINT,
    NAME     VARCHAR(256) NOT NULL,
    NAME_AID VARCHAR(256),
    URI      VARCHAR(256),
    DSC      VARCHAR(1024),
    ORD      NUMBER DEFAULT 0,
    BADGE    VARCHAR(16),
    ICON     VARCHAR(64),
    ATTR     VARCHAR(512),
    OPENED   TIMESTAMP,
    -- auditors
    DELETED  TIMESTAMP COMMENT '삭제일시',
    CREATED  TIMESTAMP COMMENT '생성일시',
    CREATOR  VARCHAR(32) COMMENT '생성자',
    UPDATED  TIMESTAMP COMMENT '수정일시',
    UPDATER  VARCHAR(32) COMMENT '수정자'
);
COMMENT ON TABLE MENU IS '메뉴';
COMMENT ON COLUMN MENU.NAME_AID IS '보조 이름';
COMMENT ON COLUMN MENU.DSC IS '설명';
COMMENT ON COLUMN MENU.ORD IS '순서';
COMMENT ON COLUMN MENU.ATTR IS '속성';
COMMENT ON COLUMN MENU.OPENED IS '개방일시';

--
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (0, null, '관리자메뉴', null, null, null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (1, 0, '커뮤니티', null, null, null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (2, 0, '시스템관리', null, null, null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (3, 1, '공지및자료실', null, null, null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (4, 2, '기본정보', null, null, null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (5, 2, '메뉴및권한', null, null, null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (6, 5, '그룹별권한관리', null, '/system/system/menu/authGrp/authGrpList.do', null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (7, 5, '메뉴관리', null, '/system/system/menu/menuMng/menuMngList.do', null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (8, 5, '사용자그룹관리', null, '/system/system/menu/userAuthGrp/userAuthGrpList.do', null, 2);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (9, 4, '사용자관리', null, '/system/system/comp/compUserMng/compUserMngList.do', null, 2);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (10, 4, '조직관리', null, '/system/system/comp/compDeptMng/compDeptMngList.do', null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (11, 4, '코드관리', null, '/system/system/comp/compCodeGrp/compCodeGrpList.do', null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (12, 3, '공지사항', null, '/bsc/system/system/notice/noticeList.do', null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (13, 3, '게시판', null, '/system/system/board/boardList.do?boardId=BBS', null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (14, 3, 'Q & A', null, '/system/system/board/boardList.do?boardId=QNA', null, 2);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (15, 3, '자료실', null, '/system/system/board/boardList.do?boardId=PDS', null, 3);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (982, 996, 'FileUpload_test', null, '/example/file/multiGroup.do', null, 3);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (983, 996, '검색조건 설정 & 유지', null, '/example/common/findValue.do', null, 2);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (984, 996, '파일 업/다운로드', null, '/example/board/boardList.do', null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (985, 996, 'FancyBox (레이어팝업)', null, '/example/common/fancybox.do', null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (986, 995, 'Excel Upload/Download', null, '/example/excel/excel.do', null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (987, 998, 'Add Row & Save All', null, '/example/grid/gridSave.do', null, 5);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (988, 998, 'Multiple Delete', null, '/example/grid/gridDelete.do', null, 4);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (989, 998, 'Cell Edit', null, '/example/grid/gridCellEdit.do', null, 3);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (990, 998, '페이징 - DB', null, '/example/grid/gridPaging2.do', null, 2);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (991, 998, '페이징 - grid 자체', null, '/example/grid/gridPaging.do', null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (992, 998, '조회 & 링크', null, '/example/grid/grid.do', null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (993, 997, 'Form &  grid', null, '/example/validation/validationGrid.do', null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (994, 997, 'Form', null, '/example/validation/validationForm.do', null, 0);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (995, 999, 'Excel', null, null, null, 3);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (996, 999, 'Common Module', null, null, null, 4);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (997, 999, 'Validation', null, null, null, 2);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (998, 999, 'jqGrid', null, null, null, 1);
insert into MENU (ID, UPPER_ID, NAME, NAME_AID, URI, DSC, ORD)
values (999, 0, '예제', null, null, null, 2);
