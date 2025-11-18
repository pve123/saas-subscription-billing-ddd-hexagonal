-- ==========================================
-- Customers: 고객 정보 저장 테이블
-- ULID(26 chars) 기반 Primary Key 사용
-- ==========================================

CREATE TABLE customers (
    id CHAR(26) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ---------- Column Comments ----------
COMMENT ON COLUMN customers.id 
    IS 'ULID 기반 고객 고유 식별자 (예: 01FZ8Z3W7X1Y3V7D8S4A7N3RZ7)';

COMMENT ON COLUMN customers.email 
    IS '고객 이메일. 로그인/식별 용도로 사용되며 시스템에서 유일해야 함';

COMMENT ON COLUMN customers.name 
    IS '고객 이름 (선택 입력)';

COMMENT ON COLUMN customers.created_at 
    IS '고객 계정 생성 시간 (서버 기준 )';

-- ---------- Table Comment ----------
COMMENT ON TABLE customers 
    IS '고객(Customer) 도메인 엔티티를 저장하는 테이블';
