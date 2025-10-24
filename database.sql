CREATE DATABASE `stock-chef`
--.

--.
CREATE TABLE `produit` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(255) NOT NULL DEFAULT 'unknown',
  quantite FLOAT(12,3) NOT NULL DEFAULT 0, -- max 999 999,999
  unite VARCHAR(100) NOT NULL DEFAULT 'unknown',
  prixUnitaire FLOAT(5,2) NOT NULL DEFAULT 0, -- max 999,99
  dateEntree DATETIME NOT NULL DEFAULT now(),
  datePeremption DATETIME NULL DEFAULT NULL,
  sys_datesup DATETIME NULL DEFAULT NULL
);
-- pour avoir des valeurs dans les tables
INSERT INTO produit (nom, quantite, unite, prixUnitaire) VALUES ("test 1", 1.001, 1, 1.01);
INSERT INTO produit (nom, quantite, unite, prixUnitaire, dateEntree, datePeremption) VALUES ("test 2", 999.001, 1, 1.02, '2025-08-05 12:34:56', now());
--.

--.
CREATE TABLE menu (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(255) NOT NULL DEFAULT 'unknown',
  dateMenu DATETIME NULL DEFAULT NULL,
  ingredients VARCHAR(512) NULL DEFAULT NULL COMMENT "à gérer par une fonction particulière: la virgule sépart les ID de la liste",
  coutTotal FLOAT(8,2) NOT NULL DEFAULT 0, -- max 999 999,99
  sys_datesup DATETIME NULL DEFAULT NULL
);
-- pour avoir des valeurs dans les tables
INSERT INTO Menu (nom, coutTotal) VALUES ("test 1", 0);
INSERT INTO Menu (nom, dateMenu, ingredients, coutTotal) VALUES ("test 2", '2025-08-31 12:00:00', '1,2,7,34,120', 0);
--.

--.
CREATE TABLE ingredientmenu (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  Menu BIGINT NOT NULL COMMENT 'ID du menu associé',
  produit BIGINT NOT NULL COMMENT 'ID du produit associé',
  quantiteUtilisee FLOAT(12,3) NOT NULL DEFAULT 0, -- max 999 999,999
  unite VARCHAR(100) NOT NULL DEFAULT 'unknown',
  sys_datesup DATETIME NULL DEFAULT NULL
);
-- pour avoir des valeurs dans les tables
INSERT INTO IngredientMenu (Menu, produit) VALUES (1, 1);
INSERT INTO IngredientMenu (Menu, produit, quantiteUtilisee, unite) VALUES (1, 2, 12.3, "kilogrammes");
--.

--.
CREATE TABLE rapport (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  dateDebut DATETIME NOT NULL DEFAULT now(),
  dateFin DATETIME NOT NULL DEFAULT now(),
  coutMoyenRepas FLOAT(5,2) NOT NULL DEFAULT 0, -- max 999,99;
  menusInclus VARCHAR(512) NULL DEFAULT NULL COMMENT "à gérer par une fonction particulière: la virgule sépart les ID de la liste",
  utilisateur BIGINT NULL DEFAULT NULL,
  sys_datesup DATETIME NULL DEFAULT NULL
);
-- pour avoir des valeurs dans les tables
INSERT INTO rapport (dateDebut, dateFin, coutMoyenRepas, menusInclus) VALUES ('2025-08-29 10:34:07', '2025-08-29 10:35:12', 5.18, "1,2,3");
--.

--.
CREATE TABLE rapportmenu(
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  rapport BIGINT NOT NULL COMMENT 'ID du rapport associé',
  Menu BIGINT NOT NULL COMMENT 'ID du menu associé',
  commentaire VARCHAR(255) NULL DEFAULT NULL COMMENT 'colonne supplémentaire si besoin',
  sys_datesup DATETIME NULL DEFAULT NULL
);
-- pour avoir des valeurs dans les tables
INSERT INTO rapportmenu (Menu, rapport) VALUES (1, 1);
INSERT INTO rapportmenu (Menu, rapport, commentaire) VALUES (2, 1, "test 2");
--.

--.
CREATE TABLE utilisateur (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(64) NOT NULL DEFAULT 'unknown' UNIQUE,
  email VARCHAR(128) NOT NULL DEFAULT 'unknown' UNIQUE,
  motDePasse VARCHAR(255) NOT NULL DEFAULT 'unknown',
  `role` ENUM('CUISINIER', 'GESTIONNAIRE', 'MANAGER', 'ADMINISTRATEUR'), -- en faire une liste pas un varchar
  sys_datesup DATETIME NULL DEFAULT NULL
)
-- pour avoir des valeurs dans les tables
INSERT INTO utilisateur (`role`) VALUES ('CUISINIER');
INSERT INTO utilisateur (nom, email, motDePasse, `role`) VALUES ('dev1', 'bastien5967@gmail.com', 'test59300', 'GESTIONNAIRE');
--.

--.
-- ajouter les contraintes de clé étrangères
ALTER TABLE ingredientmenu ADD FOREIGN KEY (Menu) REFERENCES menu(id)
ALTER TABLE ingredientmenu ADD FOREIGN KEY (produit) REFERENCES produit(id)
-- tester si les contraintes ne font pas tout planter
INSERT INTO ingredientmenu (`Menu`, `produit`, `quantiteUtilisee`, `unite`) VALUES (2, 1, 4, "Kg");
-- ajouter les contraintes de clé étrangères
ALTER TABLE rapportmenu ADD FOREIGN KEY (Menu) REFERENCES menu(id)
ALTER TABLE rapportmenu ADD FOREIGN KEY (rapport) REFERENCES rapport(id)
-- tester si les contraintes ne font pas tout planter
INSERT INTO rapport (dateDebut, dateFin, coutMoyenRepas, menusInclus) VALUES (now(), now(), 4.99, "1,2");
INSERT INTO rapportmenu (`Menu`, `rapport`, `commentaire`) VALUES (2, 2, "commentaire 2");
-- ajouter les contraintes de clé étrangères
ALTER TABLE rapport ADD FOREIGN KEY (utilisateur) REFERENCES utilisateur(id);
UPDATE rapport SET utilisateur = 1 WHERE rapport.id = 2;