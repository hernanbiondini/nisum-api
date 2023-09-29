DROP TABLE IF EXISTS USER;

CREATE TABLE USERS
(
    ID           VARCHAR(36) NOT NULL,
    NAME         VARCHAR(100) NOT NULL,
    EMAIL        VARCHAR(120) NOT NULL,
    PASSWORD     VARCHAR(100) NOT NULL,
    IS_ACTIVE    TINYINT NOT NULL,
    TOKEN        VARCHAR(250) NOT NULL,
    LAST_LOGIN   DATETIME DEFAULT CURRENT_TIMESTAMP(),
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    PRIMARY KEY (ID),
    UNIQUE KEY EMAIL_UNIQUE (EMAIL)
);

DROP TABLE IF EXISTS PHONE;

CREATE TABLE PHONES
(
    ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID      VARCHAR(36) NOT NULL,
    NUMBER       VARCHAR(15) NOT NULL,
    CITY_CODE    VARCHAR(6) NOT NULL,
    COUNTRY_CODE VARCHAR(6) NOT NULL,
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);

INSERT INTO USER(ID, NAME, EMAIL, PASSWORD, IS_ACTIVE, TOKEN, LAST_LOGIN)
VALUES ('0c0af05a-17bf-4759-9cdc-1b42df53a8c5', 'Hernan Biondini', 'hernan@biondini.com', 'Hernan09',
        1, 'eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2OTU5NTI2NTEsImlzcyI6Imh0dHBzOi8vd3d3Lm5pc3VtbGF0YW0uY29tIiwic3ViIjoiaGVybmFuQGJpb25kaW5pLmNvbSIsImV4cCI6MTY5NTk1OTg1MX0.Hyho7sowp49B_YDEIRnkiqPHN3MZWt2VGv7slOysVETNNdeuE91gOT_RrH1D4_jwryvE2s2_jJnlBFxl2hF2NA',
        CURRENT_TIMESTAMP());

INSERT INTO PHONE(ID, USER_ID, NUMBER, CITY_CODE, COUNTRY_CODE)
VALUES (1, '0c0af05a-17bf-4759-9cdc-1b42df53a8c5', '333', '3', '33');