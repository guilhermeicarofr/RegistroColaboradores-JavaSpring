CREATE TABLE "colaborador" (
	"id" serial NOT NULL,
	"cpf" TEXT NOT NULL UNIQUE,
	"nome" TEXT NOT NULL,
	"admissao" DATE NOT NULL DEFAULT NOW(),
	"funcao" TEXT NOT NULL,
	"remuneracao" bigint NOT NULL,
	CONSTRAINT "colaborador_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "subordinacao" (
	"id" serial NOT NULL,
	"gerente" integer NOT NULL,
	"subordinado" integer NOT NULL UNIQUE,
	CONSTRAINT "subordinacao_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

ALTER TABLE "subordinacao" ADD CONSTRAINT "subordinacao_fk0" FOREIGN KEY ("gerente") REFERENCES "colaborador"("id");
ALTER TABLE "subordinacao" ADD CONSTRAINT "subordinacao_fk1" FOREIGN KEY ("subordinado") REFERENCES "colaborador"("id");
