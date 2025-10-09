-- CREATE USER 'repl_user'@'%' IDENTIFIED BY 'repl_password';
--
-- GRANT ALL PRIVILEGES ON community_local.* TO 'root'@'%' IDENTIFIED BY 'community';
-- FLUSH PRIVILEGES;

-- 복제용 사용자 생성 (이미 존재하면 무시)
CREATE USER IF NOT EXISTS 'repl_user'@'%' IDENTIFIED BY 'repl_password';

-- 복제용 사용자에 복제 권한 부여
GRANT REPLICATION SLAVE ON *.* TO 'repl_user'@'%';

-- root 계정에 community_local DB 전체 권한 부여 (외부 접속 허용)
GRANT ALL PRIVILEGES ON community_local.* TO 'root'@'%' IDENTIFIED BY 'community';
FLUSH PRIVILEGES;
