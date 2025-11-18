# 구독형 SaaS 과금·결제 플랫폼 PRD (Product Requirements Document)

## 1. 개요
본 문서는 구독 기반 SaaS 서비스의 **구독 플랜 관리, 과금, 결제, 갱신, 취소** 등 핵심 기능을 제공하는 백엔드 시스템의 요구사항을 정의한다. 본 프로젝트는 Kotlin + Spring 기반으로 구현되며, DDD, 헥사고날 아키텍처, CQRS, Event Sourcing을 적절히 활용한다.

---

## 2. 제품 목적 (Product Goals)
- SaaS 서비스에서 일반적으로 필요한 **구독 관리·과금·결제** 기능을 제공하는 백엔드 구축
- 시간 기반 이벤트(매월 갱신, 트라이얼 만료 등)를 도메인 이벤트로 다루는 구조 설계
- 이벤트 소싱 기반의 구독 상태 이력 재구성 가능하도록 구현
- 읽기 모델과 쓰기 모델을 분리한 CQRS 기반 API 제공
- 외부 결제 게이트웨이(PG) 연동 가능한 확장성 있는 구조

---

## 3. 주요 사용자 유형 (User Types)
### 3.1 End-User (고객)
- 구독 시작, 취소, 결제수단 등록, 플랜 변경을 수행
- 과거 결제 내역 및 구독 상태를 조회

### 3.2 Admin (운영자)
- Plan 생성 및 수정
- 전체 고객의 구독 상태, 매출 지표 조회(MRR, Active Subscriptions 등)

### 3.3 Batch/Domain Scheduler
- 매일 또는 매월 실행되어 갱신·만료 처리 및 Invoice 생성 처리

---

## 4. 기능 요구사항 (Functional Requirements)

## 4.1 고객 관리 (Customer Context)
### FR-C1. 고객 등록
- 고객은 이메일 및 기본 정보를 통해 가입할 수 있어야 한다.
- 시스템은 고객의 ID를 발급하고 이벤트 `CustomerRegistered`를 발생시킨다.

### FR-C2. 결제수단 등록
- 고객은 Stripe/Toss 등 PG를 통해 결제수단을 등록할 수 있어야 한다.
- 등록 성공 시 `PaymentMethodAdded` 이벤트 발생.

---

## 4.2 구독 관리 (Subscription Context)
### FR-S1. 구독 시작
- 고객은 특정 플랜을 선택하여 구독을 시작할 수 있다.
- 트라이얼이 있는 플랜은 trial 기간 동안 결제되지 않는다.
- 이벤트: `SubscriptionStarted`

### FR-S2. 구독 플랜 변경
- active 상태의 구독은 상위/하위 플랜으로 변경 가능하다.
- 변경 시 다음 결제 주기부터 반영하거나 즉시 반영 여부 선택 가능.
- 이벤트: `SubscriptionPlanChanged`

### FR-S3. 구독 취소
- 고객은 언제든지 구독을 취소할 수 있으며 두 방식 존재:
  - 즉시 취소
  - 다음 결제 주기 종료 후 취소(cancel_at_period_end)
- 이벤트: `SubscriptionCanceled`

### FR-S4. 구독 상태 전이
구독은 아래 상태 전이를 가진다.
- trial → active → past_due → canceled
- Invoice/Payment 이벤트 기반으로 상태 변화.

---

## 4.3 청구/인보이스 관리 (Billing Context)
### FR-B1. 인보이스 생성
- 갱신일에 맞춰 시스템은 자동으로 인보이스를 생성해야 한다.
- 이벤트: `InvoiceGenerated`

### FR-B2. 인보이스 결제 요청
- 인보이스 생성 즉시 Payment Context로 결제 요청 이벤트 전달
- 이벤트: `InvoicePaymentRequested`

### FR-B3. 결제 실패 처리
- 결제 실패 시 인보이스는 `FAILED` 상태가 되고, 구독은 grace period 이후 `past_due`로 변경됨

---

## 4.4 결제 처리 (Payment Context)
### FR-P1. 결제 요청
- 외부 PG에 결제 요청 전송
- 성공 시 `PaymentSucceeded`
- 실패 시 `PaymentFailed`

### FR-P2. 웹훅 처리
- PG가 비동기로 성공/실패를 알려오는 경우 웹훅 처리 가능해야 함

---

## 4.5 조회/리포팅 (Query Context, CQRS Read Model)
### FR-Q1. 고객 대시보드
- 현재 구독 상태
- 다음 결제일
- 결제 내역

### FR-Q2. 운영자 대시보드
- 전체 활성 구독 수
- 월 반복 매출(MRR)
- 최근 가입자 및 churn 기록

---

## 5. 비기능 요구사항 (NFR)
### NFR-1. 확장성
- PG 연동은 Port/Adapter 패턴으로 추상화되어야 한다.

### NFR-2. 안정성
- 이벤트 저장(Event Store)은 트랜잭션 일관성을 보장해야 한다.

### NFR-3. 관찰성
- 주요 도메인 이벤트는 로그 및 Kafka 등 메시지 브로커 지원

### NFR-4. 데이터 무결성
- Aggregate 단위로 강한 일관성을 유지

---

## 6. 도메인 모델 개요 (Domain Model Overview)
### 주요 Aggregate
- **Customer**
- **Subscription**
- **Plan**
- **Invoice**
- **Payment**

### 주요 이벤트(Event Sourcing 용)
- `SubscriptionStarted`
- `SubscriptionPlanChanged`
- `SubscriptionCanceled`
- `InvoiceGenerated`
- `PaymentSucceeded`
- `PaymentFailed`

---

## 7. Bounded Context 정의
```
+---------------------+
|  Customer Context   |
+---------------------+
         ↓ events
+---------------------+
| Subscription Context|
+---------------------+
         ↓ events
+---------------------+
|   Billing Context   |
+---------------------+
         ↓ events
+---------------------+
|   Payment Context   |
+---------------------+
         ↓ events
+---------------------+
| Query/Reporting BC  |
+---------------------+
```

---

## 8. API 요구사항 (요약)
### POST /customers
### POST /customers/{id}/payment-method
### POST /subscriptions
### POST /subscriptions/{id}/change-plan
### POST /subscriptions/{id}/cancel
### GET /subscriptions/{id}
### GET /customers/{id}/dashboard
### GET /admin/metrics

---

## 9. Event Sourcing 요구사항
- Subscription과 Invoice는 이벤트 소싱 기반으로 설계
- Aggregate 상태는 이벤트 스트림을 리플레이해 재구성 가능해야 함
- Snapshot 기능(optional)

---

## 10. 성공 지표 (Success Metrics)
- 주요 시나리오의 domain event 흐름이 명확히 설명되고 테스트 가능
- CQRS 기반으로 조회 성능 최적화
- 복잡한 구독 시나리오(플랜 변경, 기간 종료, 결제 실패)를 안정적으로 처리

---

## 11. 향후 확장 포인트
- 프로모션/쿠폰 기능
- 팀(Organization) 단위 구독 관리
- 멀티 플랜 번들 구독

---

## 12. 부록: 상태 전이(State Diagram) 예시
```
trial → active → past_due → canceled
                 ↑
             payment_failed
```

---

본 PRD는 개발 초기 버전이며, 상세 도메인 모델 및 이벤트 스키마는 추후 설계 문서(DDD 문서, Context Map 등)에서 보완된다.

