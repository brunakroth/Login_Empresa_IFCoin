CREATE database login_ifcoin;

USE login_ifcoin;

CREATE TABLE Usuario (
    pk_codUsuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    nomeUsuario VARCHAR(50) NOT NULL,
    emailUsuario VARCHAR(50) NOT NULL UNIQUE KEY,
    senhaUsuario VARCHAR(255) NOT NULL,
    dataCadastroSenha DATE NOT NULL
);

CREATE TABLE HistoricoSenha (
    pk_codHistorico INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fk_codUsuario INT NOT NULL,
    senhaAnterior VARCHAR(255) NOT NULL,
    dataTroca DATE NOT NULL,
    FOREIGN KEY (fk_codUsuario) REFERENCES Usuario(pk_codUsuario)
);
