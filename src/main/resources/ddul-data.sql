INSERT INTO _AUTHORITY (ID, PID, ROLE, DSC) SELECT * FROM (
SELECT 0, NULL, 'ADMIN', '시스템 관리자' FROM DUAL UNION ALL
SELECT -1, 0, 'BOSS', '상급 관리자' FROM DUAL UNION ALL
SELECT -2, -1, 'OFFICER', '관리 지휘관' FROM DUAL UNION ALL
SELECT -3, -2, 'MINION', '일반 처리자' FROM DUAL UNION ALL
SELECT -4, -3, 'ROOKIE', '정규 신입 사원' FROM DUAL UNION ALL
SELECT -5, -3, 'INTERN', '인턴 사원' FROM DUAL UNION ALL
SELECT -6, -4, 'NOVICE', '허드렛일 전담' FROM DUAL UNION ALL
SELECT -7, 0, 'BRAHMINS', '브라만(귀족/수도사)' FROM DUAL UNION ALL
SELECT -8, -7, 'KSHATRIYAS', '카샤트리야스(무사)' FROM DUAL UNION ALL
SELECT -9, -7, 'VAISYAS', '바이샤(농민/상인)' FROM DUAL UNION ALL
SELECT -10, -9, 'SUDRA', '수드라(노예)' FROM DUAL UNION ALL
SELECT -11, -10, 'UNTOUCHABLES', '불가촉천민' FROM DUAL UNION ALL
SELECT -12, NULL, 'READ', '열람' FROM DUAL UNION ALL
SELECT -13, -12, 'READ_SECRETS', '비밀 열람' FROM DUAL UNION ALL
SELECT -14, NULL, 'CREATE', '생성' FROM DUAL UNION ALL
SELECT -15, -14, 'CREATE_USER', '사용자 생성' FROM DUAL UNION ALL
SELECT -16, -14, 'CREATE_ARTICLE', '글 생성' FROM DUAL UNION ALL
SELECT -17, -14, 'CREATE_TEAM', '부서(팀) 생성' FROM DUAL UNION ALL
SELECT -18, -14, 'CREATE_AUTHORITY', '권한 생성' FROM DUAL UNION ALL
SELECT -19, NULL, 'UPDATE', '수정' FROM DUAL UNION ALL
SELECT -20, -19, 'UPDATE_USER', '사용자 수정' FROM DUAL UNION ALL
SELECT -21, -19, 'UPDATE_ARTICLE', '글 수정' FROM DUAL UNION ALL
SELECT -22, -19, 'UPDATE_TEAM', '부서(팀) 수정' FROM DUAL UNION ALL
SELECT -23, -19, 'UPDATE_AUTHORITY', '권한 수정' FROM DUAL UNION ALL
SELECT -24, NULL, 'DELETE', '삭제' FROM DUAL UNION ALL
SELECT -25, -24, 'DELETE_USER', '사용자 삭제' FROM DUAL UNION ALL
SELECT -26, -24, 'DELETE_ARTICLE', '글 삭제' FROM DUAL UNION ALL
SELECT -27, -24, 'DELETE_TEAM', '부서(팀) 삭제' FROM DUAL UNION ALL
SELECT -28, -24, 'DELETE_AUTHORITY', '권한 삭제' FROM DUAL UNION ALL
SELECT -99, NULL, 'NONE', '권한없음' FROM DUAL
) WHERE NOT EXISTS(SELECT * FROM _AUTHORITY);

INSERT INTO _TEAM (ID, PID, NAME, ORD) SELECT * FROM (
SELECT -900, NULL, '회장실', 0 FROM DUAL UNION ALL
SELECT -910, -900, '미래전략실', 0 FROM DUAL UNION ALL
SELECT -911, -910, '실장실', 0 FROM DUAL UNION ALL
SELECT -912, -910, '차장실', 1 FROM DUAL UNION ALL
SELECT -913, -912, '준법경영실', 1 FROM DUAL UNION ALL
SELECT -914, -912, '전략1팀', 0 FROM DUAL UNION ALL
SELECT -915, -912, '전략2팀', 1 FROM DUAL UNION ALL
SELECT -916, -912, '경영지원실', 2 FROM DUAL UNION ALL
SELECT -920, -900, 'DS', 1 FROM DUAL UNION ALL
SELECT -921, -920, '메모리 사업부', 0 FROM DUAL UNION ALL
SELECT -922, -920, '시스템 SI 사업부', 1 FROM DUAL UNION ALL
SELECT -923, -920, '파운드리 사업부', 2 FROM DUAL UNION ALL
SELECT -930, -900, 'CE', 3 FROM DUAL UNION ALL
SELECT -931, -930, '디스플레이 사업부', 1 FROM DUAL UNION ALL
SELECT -932, -930, '생활가전 사업부', 0 FROM DUAL UNION ALL
SELECT -933, -930, '의료기기 사업부', 2 FROM DUAL UNION ALL
SELECT -940, -900, 'IM', 2 FROM DUAL UNION ALL
SELECT -941, -940, '무선 사업부', 0 FROM DUAL UNION ALL
SELECT -942, -940, '네트워크 사업부', 1 FROM DUAL
) WHERE NOT EXISTS(SELECT * FROM _TEAM);

INSERT INTO _USER (ID, USERNAME, PASSWORD, TEAM_ID, EMAIL, NAME, PASSWORD_EXPIRED) SELECT * FROM (
SELECT -1 AS A, 'tester' AS B, '{noop}test123$' AS C, -911 AS D, 'tester@test.com' AS E, 'foo' AS F, DATEADD('MONTH', 3, CURRENT_TIMESTAMP) AS G FROM DUAL UNION ALL
SELECT -2 AS A, 'leader' AS B, '{noop}lead123$' AS C, -912 AS D, 'leader@test.com' AS E, 'bar' AS F, DATEADD('MONTH', 3, CURRENT_TIMESTAMP) AS G FROM DUAL
) WHERE NOT EXISTS( SELECT * FROM _USER);

INSERT INTO _MAP_USER_TEAM (ID, USER_ID, TEAM_ID) SELECT * FROM (
SELECT -1 AS A, -1 AS B, -911 AS C FROM DUAL UNION ALL
SELECT -2 AS A, -1 AS B, -920 AS C FROM DUAL UNION ALL
SELECT -3 AS A, -1 AS B, -940 AS C FROM DUAL
) WHERE NOT EXISTS(SELECT * FROM _MAP_USER_TEAM);

INSERT INTO _MAP_USER_AUTHORITY (ID, USER_ID, AUTHORITY_ID) SELECT * FROM (
SELECT -1 AS A, -1 AS B, -2 AS C FROM DUAL UNION ALL
SELECT -2 AS A, -1 AS B, -6 AS C FROM DUAL UNION ALL
SELECT -3 AS A, -2 AS B, -4 AS C FROM DUAL
) WHERE NOT EXISTS (SELECT * FROM _MAP_USER_AUTHORITY);

INSERT INTO _MAP_TEAM_AUTHORITY (ID, AUTHORITY_ID, TEAM_ID) SELECT * FROM (
SELECT -1 AS A, -1 AS B, -911 AS C FROM DUAL UNION ALL
SELECT -2 AS A, -2 AS B, -911 AS C FROM DUAL UNION ALL
SELECT -3 AS A, -3 AS B, -911 AS C FROM DUAL UNION ALL
SELECT -4 AS A, -1 AS B, -912 AS C FROM DUAL UNION ALL
SELECT -5 AS A, -2 AS B, -912 AS C FROM DUAL
) WHERE NOT EXISTS( SELECT * FROM _MAP_TEAM_AUTHORITY );

INSERT INTO _MENU (ID, NAME, NAME_AID, URI, ORD, DSC, PID) SELECT * FROM (
SELECT -999, 'Documentation', 'Documentations', '', 999, 'Usage guides for everything you need to know about it', null AS X FROM DUAL UNION ALL
SELECT -992, 'Core Features', 'Core Features', '/documentation/features', 2, '', -999 FROM DUAL UNION ALL
SELECT -991, 'Guides', 'Guides', '/documentation/guides', 1, '', -999 FROM DUAL UNION ALL
SELECT -990, 'Changelog', 'Changelog', '/documentation/changelog', 0, '', -999 FROM DUAL UNION ALL
SELECT -906, 'Inactive Item', 'Inactive Item', '', 3, '', -900 FROM DUAL UNION ALL
SELECT -905, 'Inactive Item', 'Inactive Item', '', 3, '', -900 FROM DUAL UNION ALL
SELECT -904, 'Round badge', 'Round badge', '', 2, '', -901 FROM DUAL UNION ALL
SELECT -903, 'Rectangle badge', 'Rectangle badge', '', 1, '', -901 FROM DUAL UNION ALL
SELECT -902, 'Circle badge', 'Circle badge', '', 0, '', -901 FROM DUAL UNION ALL
SELECT -901, 'badges', 'badges', '', 0, '', -900 FROM DUAL UNION ALL
SELECT -900, 'Navigation Features', 'Navigation Features', '', 900,'Collapsable levels and badge styles at menu item', null AS X FROM DUAL UNION ALL
SELECT -33, 'Colors', 'Colors', '/user-interface/colors', 4, '', -25 FROM DUAL UNION ALL
SELECT -32, 'Cards', 'Cards', '/user-interface/cards', 3, '', -25 FROM DUAL UNION ALL
SELECT -31, 'Icons', 'Icons', '/user-interface/icons', 2, '', -25 FROM DUAL UNION ALL
SELECT -30, 'Wizards', 'Wizards', '/user-interface/form/wizards', 2, '', -27 FROM DUAL UNION ALL
SELECT -29, 'Layouts', 'Layouts', '/user-interface/form/layouts', 1, '', -27 FROM DUAL UNION ALL
SELECT -28, 'Fields', 'Fields', '/user-interface/form/fields', 0, '', -27 FROM DUAL UNION ALL
SELECT -27, 'Form', 'Form', '', 1, '', -25 FROM DUAL UNION ALL
SELECT -26, 'Animation', 'Animation', '/user-interface/animation', 0, '', -25 FROM DUAL UNION ALL
SELECT -25, 'User Interface', 'User Interface', '', 4, 'Building elements of UI/UX', 0 FROM DUAL UNION ALL
SELECT -24, 'Split screen', 'Sign up', '/pages/authentication/login/split-screen', 1, '', -22 FROM DUAL UNION ALL
SELECT -23, 'Fullscreen', 'Sign up', '/pages/authentication/login/fullscreen', 0, '', -22 FROM DUAL UNION ALL
SELECT -22, 'Screen Play', 'Sign up', '', 1, '', -20 FROM DUAL UNION ALL
SELECT -21, 'Classic', 'Sign up', '/pages/authentication/login/classic', 0, '', -20 FROM DUAL UNION ALL
SELECT -20, 'Login', 'Login', '', 1, '', -10 FROM DUAL UNION ALL
SELECT -19, 'Modern', 'Modern', '/pages/authentication/sign-up/modern', 1, '', -17 FROM DUAL UNION ALL
SELECT -18, 'Classic', 'Classic', '/pages/authentication/sign-up/classic', 0, '', -17 FROM DUAL UNION ALL
SELECT -17, 'Sign up', 'Sign up', '', 0, '', -10 FROM DUAL UNION ALL
SELECT -16, 'Internal server error', 'Internal server error', '', 1, '', -14 FROM DUAL UNION ALL
SELECT -15, 'Page not found', 'Page not found', '', 0, '', -14 FROM DUAL UNION ALL
SELECT -14, 'Errors', 'Errors', '', 4, '', -9 FROM DUAL UNION ALL
SELECT -13, 'Support', 'Support', '/pages/support', 3, '', -9 FROM DUAL UNION ALL
SELECT -12, 'Home', 'Home', '/pages/home', 2, '', -9 FROM DUAL UNION ALL
SELECT -11, 'Help center', 'Help center', '/pages/help-center', 1, '', -9 FROM DUAL UNION ALL
SELECT -10, 'Authentication', 'Authentication', '', 0, '', -9 FROM DUAL UNION ALL
SELECT -9, 'Pages', 'Pages', '', 3, '', 0 FROM DUAL UNION ALL
SELECT -8, 'Analytics', 'Analytics', '/dashboard/analytics', 0, '', -7 FROM DUAL UNION ALL
SELECT -7, 'Dashboard', 'Dashboard', '', 2, 'Analytics for system performances!', 0 FROM DUAL UNION ALL
SELECT -6, 'Menu', 'Menu', '/system/menu', 1, '', -4 FROM DUAL UNION ALL
SELECT -5, 'User', 'User', '/system/user', 0, 'approve new user, denied, etc.', -4 FROM DUAL UNION ALL
SELECT -4, 'System', 'System', '', 1, 'Operating environment of system', 0 FROM DUAL UNION ALL
SELECT -3, 'Approve Process', 'Approve Process', '/work/approve-process', 1, '', -1 FROM DUAL UNION ALL
SELECT -2, 'Receipt', 'Receipt', '/work/receipt', 0, '', -1 FROM DUAL UNION ALL
SELECT -1, 'Main Works', 'Main Works', '', 0, 'Remember an earning yours meal!', 0 FROM DUAL UNION ALL
SELECT  0, 'Back-office Menu', '', '', 0, '', null AS X FROM DUAL
) WHERE NOT EXISTS (SELECT * FROM _MENU);

INSERT INTO _MAP_MENU_AUTHORITY (ID, AUTHORITY_ID, MENU_ID) SELECT * FROM (
SELECT -1 AS A, -1 AS B, -2 AS C FROM DUAL UNION ALL
SELECT -2 AS A, -2 AS B, -2 AS C FROM DUAL UNION ALL
SELECT -3 AS A, -3 AS B, -2 AS C FROM DUAL
) WHERE NOT EXISTS( SELECT * FROM _MAP_MENU_AUTHORITY );

INSERT INTO _CODE (ID, GRP, CODE, NAME, ORD, DSC) SELECT * FROM (
SELECT 0, 'SYS', 'SYSTEMS', '시스템 그룹', 9999, '' FROM DUAL UNION ALL
SELECT -1, 'SYSTEMS', 'SITE_A', '사이트 A', 0, '' FROM DUAL UNION ALL
SELECT -2, 'SYSTEMS', 'SITE_B', '사이트 B', 1, '' FROM DUAL UNION ALL
SELECT -3, 'SYSTEMS', 'SITE_C', '사이트 C', 2, '' FROM DUAL UNION ALL
SELECT -4, 'SYSTEMS', 'SITE_D', '사이트 D', 3, '' FROM DUAL UNION ALL
SELECT -5, 'SITE_A', 'SAMPLE', '직급', 0, '' FROM DUAL UNION ALL
SELECT -6, 'SAMPLE', '004', 'executive director', 0, '' FROM DUAL UNION ALL
SELECT -7, 'SAMPLE', '005', 'director', 0, '' FROM DUAL UNION ALL
SELECT -8, 'SAMPLE', '006', 'department manager', 0, '' FROM DUAL UNION ALL
SELECT -9, 'SAMPLE', '008', 'Deputy General Manager', 0, '' FROM DUAL UNION ALL
SELECT -10, 'SAMPLE', '003', 'vice-president', 0, '' FROM DUAL UNION ALL
SELECT -11, 'SAMPLE', '002', 'representative', 0, '' FROM DUAL UNION ALL
SELECT -12, 'SAMPLE', '001', 'CEO', 0, '' FROM DUAL UNION ALL
SELECT -13, 'SAMPLE', '010', 'Assistant Manager', 0, '' FROM DUAL UNION ALL
SELECT -14, 'SAMPLE', '007', 'Head of team', 0, '' FROM DUAL UNION ALL
SELECT -15, 'SAMPLE', '012', 'Staff', 0, '' FROM DUAL UNION ALL
SELECT -16, 'SAMPLE', '009', 'Manager', 0, '' FROM DUAL UNION ALL
SELECT -17, 'SAMPLE', '011', 'Senior staff', 0, '' FROM DUAL UNION ALL
SELECT -18, 'SITE_A', 'FRUIT', '과일', 0, '' FROM DUAL UNION ALL
SELECT -19, 'FRUIT', '001', '수박', 0, '' FROM DUAL UNION ALL
SELECT -20, 'FRUIT', '002', '참외', 1, '' FROM DUAL UNION ALL
SELECT -21, 'FRUIT', '003', '복숭아', 2, '' FROM DUAL
) WHERE NOT EXISTS (SELECT * FROM _CODE);

INSERT INTO _ARTICLE_CATEGORY (CATEGORY, TYPE, DSC, PROPS) SELECT * FROM (
SELECT 'notice' AS A, '' AS B, '' AS C, CAST('{"page": {"size": 12, "indices": 5}, "usage": {"reply": true, "comment": true, "defaultSecret": false}}' AS CLOB) FROM DUAL UNION ALL
SELECT 'qna' AS A, '' AS B, '' AS C, CAST('{"page": {"size": 12, "indices": 5}, "usage": {"reply": true, "comment": true, "defaultSecret": false}}' AS CLOB) FROM DUAL UNION ALL
SELECT 'shared' AS A, '' AS B, '' AS C, CAST('{"page": {"size": 12, "indices": 5}, "usage": {"reply": true, "comment": true, "defaultSecret": false}}' AS CLOB) FROM DUAL
) WHERE NOT EXISTS ( SELECT * FROM _ARTICLE_CATEGORY );

INSERT INTO _ARTICLE (ID, BUNCH, SEQUEL, DEPTH, CATEGORY, USER_ID, AUTHOR, EMAIL, TITLE, CONTENT) SELECT * FROM (
SELECT -1 AS A, -1 AS B, 0 AS C, 0 AS D, 'notice' AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'data 1', null FROM DUAL UNION ALL
SELECT -2 AS A, -2 AS B, 0 AS C, 0 AS D, 'notice' AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'Annual result data #2', null FROM DUAL UNION ALL
SELECT -3 AS A, -3 AS B, 0 AS C, 0 AS D, 'notice' AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'Daily archive of transaction performance', null FROM DUAL UNION ALL
SELECT -4 AS A, -4 AS B, 0 AS C, 0 AS D, 'notice' AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'Analysis management data cleansing', null FROM DUAL UNION ALL
SELECT -5 AS A, -1 AS B, 1 AS C, 1 AS D, 'notice' AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'data 1-1', null FROM DUAL UNION ALL
SELECT -6 AS A, -1 AS B, 3 AS C, 1 AS D, 'notice' AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'data 1-2', null FROM DUAL UNION ALL
SELECT -7 AS A, -1 AS B, 2 AS C, 2 AS D, 'notice' AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'data 1-1-1', null
) WHERE NOT EXISTS( SELECT * FROM _ARTICLE );

INSERT INTO _ARTICLE_COMMENT (ID, BUNCH, SEQUEL, DEPTH, ARTICLE_ID, USER_ID, AUTHOR, EMAIL, COMMENT) SELECT * FROM (
SELECT -1 AS A, -1 AS B, 0 AS C, 0 AS D, -1 AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'comment 1' FROM DUAL UNION ALL
SELECT -2 AS A, -2 AS B, 0 AS C, 0 AS D, -1 AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'Annual result comment #2' FROM DUAL UNION ALL
SELECT -3 AS A, -3 AS B, 0 AS C, 0 AS D, -1 AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'Daily archive of transaction performance' FROM DUAL UNION ALL
SELECT -4 AS A, -4 AS B, 0 AS C, 0 AS D, -1 AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'Analysis management comment cleansing' FROM DUAL UNION ALL
SELECT -5 AS A, -1 AS B, 1 AS C, 1 AS D, -1 AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'comment 1-1' FROM DUAL UNION ALL
SELECT -6 AS A, -1 AS B, 3 AS C, 1 AS D, -1 AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'comment 1-2' FROM DUAL UNION ALL
SELECT -7 AS A, -1 AS B, 2 AS C, 2 AS D, -1 AS E, -1 AS F, 'anonymity' AS G, 'anonymous@xxx.xyz' AS H, 'comment 1-1-1'
) WHERE NOT EXISTS ( SELECT * FROM _ARTICLE_COMMENT );


INSERT INTO _FILE (ID, NAME, PATH) SELECT * FROM (
SELECT -1, 'A.png', '/root/image1' FROM DUAL UNION ALL
SELECT -2, 'B.png', '/root/image2' FROM DUAL UNION ALL
SELECT -3, 'C.png', '/root/image3' FROM DUAL UNION ALL
SELECT -4, 'D.png', '/root/image4' FROM DUAL UNION ALL
SELECT -5, 'D.png', '/root/image4' FROM DUAL
) WHERE NOT EXISTS ( SELECT * FROM _FILE );

INSERT INTO _ARTICLE_FILE (ARTICLE_ID, FILE_ID, MAPPED) SELECT * FROM (
SELECT -1 AS A, -1 AS B, CURRENT_TIMESTAMP() + 1 FROM DUAL UNION ALL
SELECT -1 AS A, -2 AS B, CURRENT_TIMESTAMP() + 3 FROM DUAL UNION ALL
SELECT -1 AS A, -3 AS B, CURRENT_TIMESTAMP() + 2 FROM DUAL
) WHERE NOT EXISTS ( SELECT * FROM _ARTICLE_FILE );
