CREATE TABLE Person (
  id UUID NOT NULL,
  DTYPE VARCHAR(31),
  first_name VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255),
  last_name VARCHAR(255) NOT NULL,
  version INT,
  CONSTRAINT pk_person PRIMARY KEY (id)
);
CREATE INDEX idx_person_id ON Person(id);

CREATE TABLE Card (
  id UUID NOT NULL,
  client_id UUID NOT NULL,
  balance DECIMAL(19, 2) NOT NULL,
  has_expiration_date_limit BOOLEAN NOT NULL,
  expiration_date_limit date,
  activated BOOLEAN NOT NULL,
  version INT,
  CONSTRAINT pk_card PRIMARY KEY (id)
);

ALTER TABLE Card ADD CONSTRAINT FK_CARD_ON_CLIENT FOREIGN KEY (client_id) REFERENCES Person (id);
CREATE INDEX idx_card_id ON Card(id);

CREATE TABLE TransactionInfo (
  id UUID NOT NULL,
  sender_card_id UUID NOT NULL,
  recipient_card_id UUID NOT NULL,
  "DATE" date NOT NULL,
  amount DECIMAL(19, 2) NOT NULL,
  version INT,
  CONSTRAINT pk_transactioninfo PRIMARY KEY (id)
);

ALTER TABLE TransactionInfo ADD CONSTRAINT FK_TRANSACTIONINFO_ON_RECIPIENT_CARD FOREIGN KEY (recipient_card_id) REFERENCES Card (id);

ALTER TABLE TransactionInfo ADD CONSTRAINT FK_TRANSACTIONINFO_ON_SENDER_CARD FOREIGN KEY (sender_card_id) REFERENCES Card (id);
