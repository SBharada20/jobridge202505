-- サンプルデータの挿入

-- ユーザーデータ（5件）
INSERT INTO USERS (USERNAME, PASSWORD, DISPLAY_NAME) VALUES
('user001', '1234', '田中太郎'),
('alice_dev', '1234', 'Alice Developer'),
('bob_manager', '1234', 'Bob Manager'),
('charlie_ui', '1234', 'Charlie UI'),
('diana_qa', '1234', 'Diana QA');

-- チャットルームデータ（5件）
INSERT INTO CHAT_ROOMS (NAME) VALUES
('一般雑談'),
('開発チーム'),
('プロジェクトA'),
('技術相談'),
('お知らせ');

-- メッセージデータ（5件）
INSERT INTO MESSAGES (USER_ID, ROOM_ID, CONTENT, TIMESTAMP) VALUES
(1, 1, 'こんにちは！新しく参加しました。よろしくお願いします。', '2024-05-27 09:00:00'),
(2, 2, 'Today we need to review the API documentation.', '2024-05-27 10:15:00'),
(3, 3, 'プロジェクトAのデッドラインについて相談があります。', '2024-05-27 11:30:00'),
(4, 4, 'CSSのレスポンシブデザインで困っています。助けてください。', '2024-05-27 13:45:00'),
(5, 5, '来週の定期メンテナンスの件でお知らせです。', '2024-05-27 15:20:00');