INSERT INTO users (gender, email, password, role, phone_number, address, created_date_time)
VALUES
    ('MALE', 'b@m', '$2a$10$3QO9vkOVIr5LwrOtxZzoseliHkMcQVvbGnAgyPDqNY/tAn/PAkyBO', 'USER', '010-1234-5678', '강원도', NOW()), -- Password1!
    ('FEMALE', 'a@m', '$2a$10$3QO9vkOVIr5LwrOtxZzoseliHkMcQVvbGnAgyPDqNY/tAn/PAkyBO', 'USER', '010-1234-5678', '인천', NOW()),
    ('MALE', 'c@m', '$2a$10$3QO9vkOVIr5LwrOtxZzoseliHkMcQVvbGnAgyPDqNY/tAn/PAkyBO', 'USER', '010-1234-5678', '대구', NOW()),
    ('FEMALE', 'd@m', '$2a$10$3QO9vkOVIr5LwrOtxZzoseliHkMcQVvbGnAgyPDqNY/tAn/PAkyBO', 'ADMIN', '010-1234-5678', '부산', NOW()),
    ('MALE', 'e@m', '$2a$10$3QO9vkOVIr5LwrOtxZzoseliHkMcQVvbGnAgyPDqNY/tAn/PAkyBO', 'ADMIN', '010-1234-5678', '서울', NOW());

INSERT INTO teachers (name, career_years, company, phone_number, introduction, created_date_time)
VALUES
    ('John Doe', 5, 'TEAM_SPARTA', '010-1234-5678', 'Experienced software engineer specializing in backend development.', NOW()),
    ('Jane Smith', 8, 'TEAM_SPARTA', '010-8765-4321', 'Expert in frontend development with over 8 years of experience.', NOW()),
    ('Alice Johnson', 3, 'KAKAO', '010-1111-2222', 'Passionate about mobile app development and design.', NOW()),
    ('Bob Brown', 10, 'KAKAO', '010-3333-4444', 'Veteran developer with a decade of experience in full-stack development.', NOW()),
    ('Charlie Davis', 2, 'NAVER', '010-5555-6666', 'Enthusiastic about AI and machine learning.', NOW());

-- Assuming the teacher_id values are as follows:
-- John Doe = 1
-- Jane Smith = 2
-- Alice Johnson = 3
-- Bob Brown = 4
-- Charlie Davis = 5

INSERT INTO lectures (title, price, introduction, category, teacher_id, created_date_time)
VALUES
    ('Spring Boot Fundamentals', 100, 'Learn the basics of Spring Boot.', 'SPRING', 1, NOW()),
    ('Advanced React Techniques', 150, 'Master advanced techniques in React.', 'REACT', 2, NOW()),
    ('Node.js for Beginners', 120, 'Introduction to Node.js development.', 'NODE', 3, NOW()),
    ('Full-Stack Development with Spring and React', 200, 'Become a full-stack developer using Spring and React.', 'SPRING', 1, NOW()),
    ('Ai and Machine Learning Basics', 180, 'Get started with AI and machine learning.', 'REACT', 2, NOW());

-- Lecture 1 (Spring Boot Fundamentals) 댓글
INSERT INTO comments (parent_comment_id, author_id, lecture_id, content, created_date_time)
VALUES
    (NULL, 1, 1, '정말 좋은 강의입니다!', '2023-01-01 10:00:00'),
    (NULL, 2, 1, '아주 유익합니다.', '2023-01-01 11:00:00'),
    (NULL, 3, 1, '많이 배웠어요.', '2023-01-01 12:00:00'),
    (NULL, 4, 1, '강력 추천합니다!', '2023-01-01 13:00:00'),
    (NULL, 5, 1, '훌륭한 자료입니다.', '2023-01-01 14:00:00'),
    (NULL, 1, 1, 'Spring Boot에 대한 좋은 소개입니다.', '2023-01-01 15:00:00'),
    (NULL, 2, 1, '초보자에게 딱 맞는 강의입니다.', '2023-01-01 16:00:00'),
    (NULL, 3, 1, '잘 정리된 강의입니다.', '2023-01-01 17:00:00'),
    (NULL, 4, 1, '명확하고 간결합니다.', '2023-01-01 18:00:00'),
    (NULL, 5, 1, '강의를 즐겼습니다.', '2023-01-01 19:00:00');


-- Lecture 1 (Spring Boot Fundamentals) 대댓글
INSERT INTO comments (parent_comment_id, author_id, lecture_id, content, created_date_time)
VALUES
    (1, 2, 1, '저도 동의합니다!', NOW()),
    (1, 3, 1, '저도요.', NOW()),
    (1, 4, 1, '절대적으로 동의합니다.', NOW()),
    (4, 5, 1, '저도 추천합니다.', NOW()),
    (4, 1, 1, '정말 훌륭한 자료입니다.', NOW()),
    (6, 2, 1, '소개 부분이 좋았어요.', NOW()),
    (7, 3, 1, '초보자에게 정말 좋습니다.', NOW()),
    (8, 4, 1, '아주 잘 설명되었어요.', NOW()),
    (8, 5, 1, '간결하고 명확합니다.', NOW()),
    (8, 1, 1, '저도 즐겼습니다!', NOW());

INSERT INTO lecture_likes (user_id, lecture_id, created_date_time)
VALUES
    (1, 1, NOW()),  -- user1가 Spring Boot Fundamentals 강의를 좋아함
    (2, 2, NOW()),  -- user2가 Advanced React Techniques 강의를 좋아함
    (1, 3, NOW()),  -- user1가 Node.js for Beginners 강의를 좋아함
    (3, 4, NOW()),  -- user3가 Full-Stack Development with Spring and React 강의를 좋아함
    (4, 4, NOW()),  -- user4가 Full-Stack Development with Spring and React 강의를 좋아함
    (2, 5, NOW());  -- user2가 Ai and Machine Learning Basics 강의를 좋아함