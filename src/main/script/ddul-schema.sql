DROP SEQUENCE IF EXISTS ID_GENERATOR_SQ;
CREATE SEQUENCE IF NOT EXISTS ID_GENERATOR_SQ START WITH 1;

--
-- User entity
--
DROP TABLE IF EXISTS USER CASCADE;
CREATE TABLE IF NOT EXISTS USER
(
    ID           BIGINT PRIMARY KEY,
    USERNAME     VARCHAR(32)  NOT NULL UNIQUE,
    PASSWORD     VARCHAR(256) NOT NULL,
    PASSWORD_EXP TIMESTAMP,
    EMAIL        VARCHAR(128),
    NAME         VARCHAR(64),
    LOCK         CHAR(1)   DEFAULT 'N' CHECK (LOCK = 'Y' OR LOCK = 'N'),
    -- auditors
    REMOVAL      CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR      VARCHAR(32) COMMENT '생성자',
    UPDATED      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER      VARCHAR(32) COMMENT '수정자'
);
COMMENT ON TABLE USER IS '사용자';
COMMENT ON COLUMN USER.USERNAME IS '사용자명(로그인 ID)';
COMMENT ON COLUMN USER.PASSWORD_EXP IS '비밀번호 만료일시';
COMMENT ON COLUMN USER.NAME IS '성명';
COMMENT ON COLUMN USER.LOCK IS '잠김';

INSERT INTO USER (ID, USERNAME, PASSWORD, EMAIL, NAME, PASSWORD_EXP)
VALUES (-1, 'tester', '{noop}test123$', 'tester@test.com', 'foo',
        DATEADD('MONTH', 3, CURRENT_DATE));
INSERT INTO USER (ID, USERNAME, PASSWORD, EMAIL, NAME, PASSWORD_EXP)
VALUES (-2, 'leader', '{noop}lead123$', 'leader@test.com', 'bar',
        DATEADD('MONTH', 3, CURRENT_DATE));

--
-- Authority Entity
--
DROP TABLE IF EXISTS AUTHORITY CASCADE;
CREATE TABLE IF NOT EXISTS AUTHORITY
(
    ID      BIGINT PRIMARY KEY,
    PID     INT,
    ROLE    VARCHAR(32) NOT NULL UNIQUE,
    -- auditors
    REMOVAL CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR VARCHAR(32) COMMENT '생성자',
    UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER VARCHAR(32) COMMENT '수정자'
);
COMMENT ON TABLE AUTHORITY IS '권한';
COMMENT ON COLUMN AUTHORITY.ROLE IS '권한 이름';

INSERT INTO AUTHORITY (ID, PID, ROLE)
VALUES (0, NULL, 'ADMIN');
INSERT INTO AUTHORITY (ID, PID, ROLE)
VALUES (-1, 0, 'BOSS');
INSERT INTO AUTHORITY (ID, PID, ROLE)
VALUES (-2, -1, 'OFFICER');
INSERT INTO AUTHORITY (ID, PID, ROLE)
VALUES (-3, -2, 'MINION');
INSERT INTO AUTHORITY (ID, PID, ROLE)
VALUES (-4, -3, 'ROOKIE');
INSERT INTO AUTHORITY (ID, PID, ROLE)
VALUES (-5, -3, 'INTERN');
INSERT INTO AUTHORITY (ID, PID, ROLE)
VALUES (-6, -4, 'NOVICE');


DROP TABLE IF EXISTS USER_AUTHORITY CASCADE;
CREATE TABLE IF NOT EXISTS USER_AUTHORITY
(
    ID           BIGINT PRIMARY KEY,
    USER_ID      BIGINT NOT NULL,
    AUTHORITY_ID BIGINT NOT NULL,
    -- auditors
    REMOVAL      CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR      VARCHAR(32) COMMENT '생성자',
    UPDATED      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER      VARCHAR(32) COMMENT '수정자'
);
ALTER TABLE USER_AUTHORITY
    ADD CONSTRAINT USER_AUTHORITY_UQ1 UNIQUE (USER_ID, AUTHORITY_ID);

COMMENT ON TABLE USER_AUTHORITY IS '사용자-권한 매핑';

INSERT INTO USER_AUTHORITY (ID, USER_ID, AUTHORITY_ID)
VALUES (-1, -1, -2);
INSERT INTO USER_AUTHORITY (ID, USER_ID, AUTHORITY_ID)
VALUES (-2, -1, -6);
INSERT INTO USER_AUTHORITY (ID, USER_ID, AUTHORITY_ID)
VALUES (-3, -2, -4);


--
-- Menu Entity
--
DROP TABLE IF EXISTS MENU CASCADE;
CREATE TABLE IF NOT EXISTS MENU
(
    ID       BIGINT PRIMARY KEY,
    PID      BIGINT,
    NAME     VARCHAR(256) NOT NULL,
    NAME_AID VARCHAR(256),
    URI      VARCHAR(256),
    DSC      VARCHAR(1024),
    ORD      NUMBER    DEFAULT 0,
    BADGE    VARCHAR(16),
    ICON     VARCHAR(64),
    ATTR     VARCHAR(512),
    OPENED   TIMESTAMP,
    -- auditors
    REMOVAL  CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR  VARCHAR(32) COMMENT '생성자',
    UPDATED  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER  VARCHAR(32) COMMENT '수정자'
);
COMMENT ON TABLE MENU IS '메뉴';
COMMENT ON COLUMN MENU.PID IS '상위(부모) ID';
COMMENT ON COLUMN MENU.NAME_AID IS '보조 이름';
COMMENT ON COLUMN MENU.DSC IS '설명';
COMMENT ON COLUMN MENU.ORD IS '순서';
COMMENT ON COLUMN MENU.ATTR IS '속성';
COMMENT ON COLUMN MENU.OPENED IS '개방일시';
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-999, 'Documentation', 'Documentation', '', 999,
        'Usage guides for everything you need to know about it', null);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-992, 'Core Features', 'Core Features', '/documentation/features', 2, '', -999);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-991, 'Guides', 'Guides', '/documentation/guides', 1, '', -999);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-990, 'Changelog', 'Changelog', '/documentation/changelog', 0, '', -999);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-906, 'Inactive Item', 'Inactive Item', '', 3, '', -900);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-905, 'Inactive Item', 'Inactive Item', '', 3, '', -900);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-904, 'Round badge', 'Round badge', '', 2, '', -901);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-903, 'Rectangle badge', 'Rectangle badge', '', 1, '', -901);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-902, 'Circle badge', 'Circle badge', '', 0, '', -901);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-901, 'badges', 'badges', '', 0, '', -900);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-900, 'Navigation Features', 'Navigation Features', '', 900,
        'Collapsable levels and badge styles at menu item', null);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-33, 'Colors', 'Colors', '/user-interface/colors', 4, '', -25);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-32, 'Cards', 'Cards', '/user-interface/cards', 3, '', -25);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-31, 'Icons', 'Icons', '/user-interface/icons', 2, '', -25);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-30, 'Wizards', 'Wizards', '/user-interface/form/wizards', 2, '', -27);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-29, 'Layouts', 'Layouts', '/user-interface/form/layouts', 1, '', -27);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-28, 'Fields', 'Fields', '/user-interface/form/fields', 0, '', -27);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-27, 'Form', 'Form', '', 1, '', -25);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-26, 'Animation', 'Animation', '/user-interface/animation', 0, '', -25);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-25, 'User Interface', 'User Interface', '', 4, 'Building elements of UI/UX', 0);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-24, 'Split screen', 'Sign up', '/pages/authentication/login/split-screen', 1, '', -22);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-23, 'Fullscreen', 'Sign up', '/pages/authentication/login/fullscreen', 0, '', -22);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-22, 'Screen Play', 'Sign up', '', 1, '', -20);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-21, 'Classic', 'Sign up', '/pages/authentication/login/classic', 0, '', -20);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-20, 'Login', 'Login', '', 1, '', -10);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-19, 'Modern', 'Modern', '/pages/authentication/sign-up/modern', 1, '', -17);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-18, 'Classic', 'Classic', '/pages/authentication/sign-up/classic', 0, '', -17);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-17, 'Sign up', 'Sign up', '', 0, '', -10);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-16, 'Internal server error', 'Internal server error', '', 1, '', -14);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-15, 'Page not found', 'Page not found', '', 0, '', -14);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-14, 'Errors', 'Errors', '', 4, '', -9);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-13, 'Support', 'Support', '/pages/support', 3, '', -9);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-12, 'Home', 'Home', '/pages/home', 2, '', -9);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-11, 'Help center', 'Help center', '/pages/help-center', 1, '', -9);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-10, 'Authentication', 'Authentication', '', 0, '', -9);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-9, 'Pages', 'Pages', '', 3, '', 0);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-8, 'Analytics', 'Analytics', '/dashboard/analytics', 0, '', -7);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-7, 'Dashboard', 'Dashboard', '', 2, 'Analystics for system performances!', 0);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-6, 'Menu', 'Menu', '/system/menu', 1, '', -4);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-5, 'User', 'User', '/system/user', 0, 'approve new user, denied, etc.', -4);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-4, 'System', 'System', '', 1, 'Operating environment of system', 0);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-3, 'Approve Process', 'Approve Process', '/work/approve-process', 1, '', -1);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-2, 'Receipt', 'Receipt', '/work/receipt', 0, '', -1);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (-1, 'Main Works', 'Main Works', '', 0, 'Remember an earning yours meal!', 0);
INSERT INTO MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID)
VALUES (0, '관리자메뉴', '', '', 0, '', null);


DROP TABLE IF EXISTS MENU_AUTHORITY CASCADE;
CREATE TABLE IF NOT EXISTS MENU_AUTHORITY
(
    ID           BIGINT PRIMARY KEY,
    MENU_ID      BIGINT NOT NULL,
    AUTHORITY_ID BIGINT NOT NULL,
    -- auditors
    REMOVAL      CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR      VARCHAR(32) COMMENT '생성자',
    UPDATED      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER      VARCHAR(32) COMMENT '수정자'
);
ALTER TABLE MENU_AUTHORITY
    ADD CONSTRAINT MENU_AUTHORITY_UQ1 UNIQUE (MENU_ID, AUTHORITY_ID);

COMMENT ON TABLE MENU_AUTHORITY IS '메뉴-권한 매핑';


DROP TABLE IF EXISTS CODE CASCADE;
CREATE TABLE IF NOT EXISTS CODE
(
    ID      BIGINT PRIMARY KEY,
    GRP_COD VARCHAR(256) NOT NULL,
    COD     VARCHAR(256) NOT NULL,
    COD_NM  VARCHAR(256),
    ORD     NUMBER    DEFAULT 0,
    DSC     VARCHAR(512),
    USE     CHAR(1)   DEFAULT 'Y' CHECK (USE = 'Y' OR USE = 'N'),
    -- auditors
    REMOVAL CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR VARCHAR(32) COMMENT '생성자',
    UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER VARCHAR(32) COMMENT '수정자'
);
ALTER TABLE CODE
    ADD CONSTRAINT CODE_UK1 UNIQUE (GRP_COD, COD);

COMMENT ON TABLE CODE IS '공통코드';
COMMENT ON COLUMN CODE.GRP_COD IS '그룹(상위) 코드';
COMMENT ON COLUMN CODE.COD IS '코드';
COMMENT ON COLUMN CODE.COD_NM IS '코드 이름';
COMMENT ON COLUMN CODE.ORD IS '순서';
COMMENT ON COLUMN CODE.DSC IS '설명';

INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (0, 'SYS', 'SYSTEMS', '시스템 그룹', 9999, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-1, 'SYSTEMS', 'SITE_A', '사이트 A', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-2, 'SYSTEMS', 'SITE_B', '사이트 B', 1, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-3, 'SYSTEMS', 'SITE_C', '사이트 C', 2, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-4, 'SYSTEMS', 'SITE_D', '사이트 D', 3, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-5, 'SITE_A', 'SAMPLE', '직급', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-6, 'SAMPLE', '004', 'executive director', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-7, 'SAMPLE', '005', 'director', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-8, 'SAMPLE', '006', 'department manager', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-9, 'SAMPLE', '008', 'Deputy General Manager', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-10, 'SAMPLE', '003', 'vice-president', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-11, 'SAMPLE', '002', 'representative', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-12, 'SAMPLE', '001', 'CEO', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-13, 'SAMPLE', '010', 'Assistant Manager', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-14, 'SAMPLE', '007', 'Head of team', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-15, 'SAMPLE', '012', 'Staff', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-16, 'SAMPLE', '009', 'Manager', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-17, 'SAMPLE', '011', 'Senior staff', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-18, 'SITE_A', 'FRUIT', '과일', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-19, 'FRUIT', '001', '수박', 0, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-20, 'FRUIT', '002', '참외', 1, '');
INSERT INTO CODE (ID, GRP_COD, COD, COD_NM, ORD, DSC)
VALUES (-21, 'FRUIT', '003', '복숭아', 2, '');

--
-- File entity
--
DROP TABLE IF EXISTS FILE CASCADE;
CREATE TABLE IF NOT EXISTS FILE
(
    ID        VARCHAR(128) PRIMARY KEY,
    NAME      VARCHAR(256) NOT NULL,
    PATH      VARCHAR(512) NOT NULL,
    GRP       VARCHAR(64),
    SIZE      BIGINT    DEFAULT 0,
    CONT_TYPE VARCHAR(128),
    ACS_COUNT BIGINT    DEFAULT 0,
    -- auditors
    REMOVAL   CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR   VARCHAR(32) COMMENT '생성자',
    UPDATED   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER   VARCHAR(32) COMMENT '수정자'
);
COMMENT ON TABLE FILE IS '첨부파일';
COMMENT ON COLUMN FILE.NAME IS '원본 파일명';
COMMENT ON COLUMN FILE.PATH IS '저장 경로';
COMMENT ON COLUMN FILE.SIZE IS '파일 크기';
COMMENT ON COLUMN FILE.GRP IS '파일 그룹(or <input [name]/>)';
COMMENT ON COLUMN FILE.CONT_TYPE IS 'Content-Type(or Mime-Type)';
COMMENT ON COLUMN FILE.ACS_COUNT IS '다운로드 횟수';


--
-- Board Entity
--
DROP TABLE IF EXISTS BOARD CASCADE;
CREATE TABLE IF NOT EXISTS BOARD
(
    ID        BIGINT PRIMARY KEY,
    BID       BIGINT,
    ORD       INT       DEFAULT 0,
    DEPTH     INT       DEFAULT 0,
    GRP       VARCHAR2(64) NOT NULL,
    TITLE     VARCHAR(512),
    USER_ID   BIGINT,
    AU_NAME   VARCHAR(64),
    AU_EMAIL  VARCHAR(256),
    AU_PWD    VARCHAR(256),
    SECRET    CHAR(1)   DEFAULT 'N' CHECK (SECRET = 'Y' OR SECRET = 'N'),
    ACS_COUNT BIGINT    DEFAULT 0,
    -- auditors
    REMOVAL   CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR   VARCHAR(32) COMMENT '생성자',
    UPDATED   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER   VARCHAR(32) COMMENT '수정자'
);
COMMENT ON TABLE BOARD IS '게시판';
COMMENT ON COLUMN BOARD.BID IS '댓글용 묶음 ID';
COMMENT ON COLUMN BOARD.ORD IS '답글 정렬 순서';
COMMENT ON COLUMN BOARD.DEPTH IS '답글 깊이';
COMMENT ON COLUMN BOARD.GRP IS '게시판 그룹';
COMMENT ON COLUMN BOARD.AU_NAME IS '작성자 이름';
COMMENT ON COLUMN BOARD.AU_EMAIL IS '작성자 이메일';
COMMENT ON COLUMN BOARD.AU_PWD IS '작성자 비밀번호';
COMMENT ON COLUMN BOARD.SECRET IS '비밀글';
COMMENT ON COLUMN BOARD.ACS_COUNT IS '조횟수';

DROP TABLE IF EXISTS BOARD_CONTENT CASCADE;
CREATE TABLE IF NOT EXISTS BOARD_CONTENT
(
    ID      BIGINT PRIMARY KEY,
    BODY    CLOB,
    TEXT    CLOB,
    -- auditors
    REMOVAL CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR VARCHAR(32) COMMENT '생성자',
    UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER VARCHAR(32) COMMENT '수정자'
);
COMMENT ON TABLE BOARD_CONTENT IS '게시판 내용';
COMMENT ON COLUMN BOARD_CONTENT.BODY IS '본문';
COMMENT ON COLUMN BOARD_CONTENT.TEXT IS '글내용(HTML 제거)';

DROP TABLE IF EXISTS BOARD_COMMENT CASCADE;
CREATE TABLE IF NOT EXISTS BOARD_COMMENT
(
    ID       BIGINT PRIMARY KEY,
    BID      BIGINT,
    ORD      INT       DEFAULT 0,
    DEPTH    INT       DEFAULT 0,
    BOARD_ID BIGINT NOT NULL,
    CONTENT  CLOB,
    USER_ID  BIGINT,
    AU_NAME  VARCHAR(64),
    AU_EMAIL VARCHAR(256),
    AU_PWD   VARCHAR(256),
    SECRET   CHAR(1)   DEFAULT 'N' CHECK (SECRET = 'Y' OR SECRET = 'N'),
    -- auditors
    REMOVAL  CHAR(1)   DEFAULT 'N' COMMENT '삭제여부' CHECK (REMOVAL = 'Y' OR REMOVAL = 'N'),
    CREATED  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR  VARCHAR(32) COMMENT '생성자',
    UPDATED  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER  VARCHAR(32) COMMENT '수정자'
);
COMMENT ON TABLE BOARD_COMMENT IS '게시판 댓글';
COMMENT ON COLUMN BOARD_COMMENT.BID IS '묶음 ID';
COMMENT ON COLUMN BOARD_COMMENT.ORD IS '정렬 순서';
COMMENT ON COLUMN BOARD_COMMENT.DEPTH IS '답글 깊이';
COMMENT ON COLUMN BOARD_COMMENT.BOARD_ID IS '게시물 ID';
COMMENT ON COLUMN BOARD_COMMENT.AU_NAME IS '작성자 이름';
COMMENT ON COLUMN BOARD_COMMENT.AU_EMAIL IS '작성자 이메일';
COMMENT ON COLUMN BOARD_COMMENT.AU_PWD IS '작성자 비밀번호';
COMMENT ON COLUMN BOARD_COMMENT.SECRET IS '비밀글';

DROP TABLE IF EXISTS BOARD_EXTRA CASCADE;
CREATE TABLE IF NOT EXISTS BOARD_EXTRA
(
    BOARD_ID BIGINT,
    NAME     VARCHAR(256),
    VALUE    VARCHAR(4000),
    VALUE2   VARCHAR(4000),
    -- auditors
    CREATED  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    CREATOR  VARCHAR(32) COMMENT '생성자',
    UPDATED  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    UPDATER  VARCHAR(32) COMMENT '수정자',
    PRIMARY KEY (BOARD_ID, NAME)
);
COMMENT ON TABLE BOARD_EXTRA IS '게시판 추가 데이터';
COMMENT ON COLUMN BOARD_EXTRA.NAME IS '추가 데이터 이름';
COMMENT ON COLUMN BOARD_EXTRA.VALUE IS '추가 데이터 값';
COMMENT ON COLUMN BOARD_EXTRA.VALUE2 IS '추가 데이터 값2';
