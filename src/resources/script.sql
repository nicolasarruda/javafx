create schema financas;
use `financas`;
CREATE TABLE `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `categoria` VALUES (1,'Alimentação'),(2,'Educação'),(3,'Esportes'),(4,'Lazer'),(5,'Moradia'),(6,'Outros'),(7,'Presentes'),(8,'Roupas'),(9,'Salário'),(10,'Saúde'),(11,'Transporte'),(12,'Viagem');



CREATE TABLE `tipos_movimentacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO `tipos_movimentacao` VALUES (1,'Receita'),(2,'Despesa');

CREATE TABLE `movimentacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tiposMovimentacao_id` int(11) NOT NULL,
  `categoria_id` int(11) NOT NULL,
  `data` date NOT NULL,
  `valor` decimal(7,2) NOT NULL,
  `descricao` varchar(50) DEFAULT NULL,
  `pago` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_movimentacao_1_idx` (`tiposMovimentacao_id`),
  KEY `fk_categoria_idx` (`categoria_id`),
  CONSTRAINT `fk_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tipo` FOREIGN KEY (`tiposMovimentacao_id`) REFERENCES `tipos_movimentacao` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

insert into `movimentacao` (tiposMovimentacao_id, categoria_id, data, valor, descricao, pago) values (1, 9, now(), 5000, null, 'S');

CREATE TABLE `financas`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL UNIQUE,
  `senha` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`id`));
  
  INSERT INTO `usuario` (nome, senha) VALUES ('teste','1234');